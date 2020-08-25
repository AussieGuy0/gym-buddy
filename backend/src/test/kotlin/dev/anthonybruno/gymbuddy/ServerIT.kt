package dev.anthonybruno.gymbuddy

import dev.anthonybruno.gymbuddy.db.Database
import io.restassured.RestAssured
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServerIT {

    private val postgres = PostgreSQLContainer<Nothing>("postgres:12").apply {
        start()
    }

    private val server = Server(Database(postgres.username, postgres.username, postgres.jdbcUrl))
    private val portNum = 9000
    private val url = "http://localhost:$portNum"

    @BeforeAll
    fun setup() {
        server.start(portNum)
    }

    @AfterAll
    fun teardown() {
        server.stop()
        postgres.stop()
    }

    @Test
    fun homepage() {
        RestAssured.get(url).then().statusCode(200)
    }

    @Test
    fun failLogIn() {
        RestAssured.given()
                .contentType("application/json")
                .body(mapOf("email" to "bla@example.org", "password" to "password"))
                .`when`()
                .post("$url/auth/login")
                .then()
                .statusCode(400)
    }

}