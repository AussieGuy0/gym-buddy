package dev.anthonybruno.gymbuddy;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;

public class ServerIT {

    private static final Server server = new Server();
    private static final int portNum = 9000;
    private static final String url = "http://localhost:" + portNum;

    @BeforeAll
    public static void setup() {
        server.start(portNum);
    }

    @AfterAll
    public static void teardown() {
        server.stop();
    }

    @Test
    public void homepage() {
        get(url).then().statusCode(200);
    }

    @Test
    public void signIn() {
        given()
                .contentType("application/json")
                .body("{\"email\": \"bla@example.org\", \"password\": \"password\"}")
                .when()
                .post(url + "/auth/login")
                .then()
                .statusCode(200);
    }
}
