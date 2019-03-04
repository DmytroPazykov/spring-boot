package com.astt.strt.test;

import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.astt.strt.data.model.Role;
import com.astt.strt.data.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "clean.cron.time=1 * * * * ?")
public class ConfigApplicationTests {

    private static final String GET_USER_BY_ROLE_URL = "/user/role/{role}";
    private static final String GET_ALL_USERS_URL = "/users";
    private static final String POST_NEW_USER_URL = "/user";
    private static final String POST_USER_RESET_URL = "/user/reset";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Tag("unit")
    void getAllUsersApiTest() {
        User[] users = restTemplate
                .getForObject(GET_ALL_USERS_URL, User[].class);

        Assert.assertFalse("No users being provided",
                Arrays.asList(users).isEmpty());
    }

    @Test
    @Tag("unit")
    void postAndCheckNewUserTest() {
        User user = new User(
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                Role.ADMIN);

        restTemplate
                .postForEntity(POST_NEW_USER_URL, user, User.class);

        User[] users = restTemplate
                .getForObject(GET_ALL_USERS_URL, User[].class);

        assertTrue("Could not found brand new user",
                Arrays.asList(users).contains(user));

        assertTrue("User that is just being posted, isn't free for use",
                Arrays.stream(users)
                        .filter(el -> user.getLogin().equals(el.getLogin()))
                        .anyMatch(User::getIsNotBusy));
    }

    @Test
    @Tag("unit")
    void getUserAndCheckIfItWasMarkedAsBusyTest() {

        User user = restTemplate
                .getForObject(GET_USER_BY_ROLE_URL, User.class, Role.ADMIN);

        assertAll(
                "Either user isn't available or it is busy",
                () -> assertNotNull(user),
                () -> assertFalse(user.getIsNotBusy()));

        User[] users = restTemplate
                .getForObject("/users", User[].class);

        boolean anyMatch = Arrays.stream(users)
                .filter(el -> el.getRole().equals(Role.ADMIN))
                .filter(el -> el.getLogin().equals(user.getLogin()))
                .anyMatch(el -> el.getIsNotBusy().equals(false));

        Assert.assertTrue("User that being in use, hasn't been marked as busy one", anyMatch);
    }

    @Test
    @Tag("unit")
    void couldNotRetrieveFreeUserTest() {
        String errorMessage = String.format("Could not found any free user by role '%s'", Role.USER);

        restTemplate
                .getForObject(GET_USER_BY_ROLE_URL, User.class, Role.USER.name());

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity(GET_USER_BY_ROLE_URL, String.class, Role.USER.name());

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode()),
                () -> assertEquals(errorMessage, getResponse.getBody()));
    }

    @Test
    @Tag("unit")
    void couldNotRetrieveUserByIncorrectRoleTest() {
        String testString = RandomStringUtils.randomAlphabetic(10);
        ResponseEntity<String> response = restTemplate
                .getForEntity(GET_USER_BY_ROLE_URL, String.class, testString);

        assertAll(
                () -> assertEquals("Response code isn't 400 but - " + response.getStatusCode(),
                        HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertEquals("Incorrect response body error message",
                        String.format("Could not found any role '%s'", testString),
                        response.getBody()));
    }

    @Test
    @Tag("unit")
    void userCanBeBackToUseQueue() {
        User user = restTemplate
                .getForObject(GET_USER_BY_ROLE_URL, User.class, Role.USER.name());

        User[] users = restTemplate
                .getForObject(GET_ALL_USERS_URL, User[].class);

        boolean anyMatch = Arrays.stream(users)
                .filter(el -> el.getRole().equals(Role.USER))
                .filter(el -> el.getLogin().equals(user.getLogin()))
                .anyMatch(el -> el.getIsNotBusy().equals(false));

        Assert.assertTrue("User that being in use, hasn't been marked as busy one", anyMatch);

        restTemplate
                .put(POST_USER_RESET_URL, user, String.class);

        users = restTemplate
                .getForObject(GET_ALL_USERS_URL, User[].class);

        anyMatch = Arrays.stream(users)
                .filter(el -> el.getRole().equals(Role.USER))
                .filter(el -> el.getLogin().equals(user.getLogin()))
                .anyMatch(el -> el.getIsNotBusy().equals(true));
        Assert.assertTrue("User information has not being rollbacked", anyMatch);
    }

}
