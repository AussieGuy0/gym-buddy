package dev.anthonybruno.gymbuddy

import com.fasterxml.jackson.databind.node.ArrayNode
import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.exception.BadRequestException
import dev.anthonybruno.gymbuddy.user.DbUserRepository
import dev.anthonybruno.gymbuddy.user.UserDetails
import dev.anthonybruno.gymbuddy.user.UserService
import dev.anthonybruno.gymbuddy.workout.DbExerciseRepository
import io.restassured.RestAssured
import io.restassured.matcher.ResponseAwareMatcher
import io.restassured.matcher.RestAssuredMatchers
import io.restassured.response.*
import io.restassured.specification.RequestSpecification
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.*
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import java.net.URI
import java.time.ZoneId

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServerIT {

    private val postgres = PostgreSQLContainer<Nothing>("postgres:12").apply {
        start()
    }

    private val database = Database(postgres.username, postgres.username, postgres.jdbcUrl)
    private val userService = UserService(DbUserRepository(db = database))
    private val server = Server(database)
    private val portNum = 9000
    private val url: URI
        get() = server.url

    private val defaultPassword = "password1234"

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
        given()
                .contentType("application/json")
                .body(mapOf("email" to "bla@example.org", "password" to "password"))
                .`when`()
                .post("$url/auth/login")
                .andThen()
                .statusCode(400)
    }

    private fun createUser(password: String = defaultPassword): UserDetails {
        val email = TestUtils.randomEmail();
        return userService.addUser(email, password)
    }

    private fun loginThen(email: String, password: String = defaultPassword): RequestSpecification {
        val cookies = given()
                .contentType("application/json")
                .body(mapOf("email" to email, "password" to password))
                .`when`()
                .post("$url/auth/login")
                .andThen()
                .statusCode(200)
                .extract()
                .response()
                .detailedCookies!!

        return given().cookies(cookies)
    }

    private fun given(): RequestSpecification {
        return RestAssured.given()
                .log().ifValidationFails()


    }

    @Nested
    inner class UserServiceTest {


        @Test
        fun canAddUser() {
            val email = TestUtils.randomEmail();
            val password = "password234"
            val user = userService.addUser(email, password)
            assertThat(user.id).isGreaterThanOrEqualTo(0)
            assertThat(user.email).isEqualTo(email)
            assertThat(user.timezone).isEqualTo(ZoneId.of("Australia/Adelaide"))
        }

        @Test
        fun preventsInvalidEmail() {
            assertThatThrownBy { userService.addUser("not a valid email!", "password234") }
                    .isInstanceOf(BadRequestException::class.java)
        }

        @Test
        fun preventsTooShortPassword() {
            assertThatThrownBy { userService.addUser(TestUtils.randomEmail(), "short") }
                    .isInstanceOf(BadRequestException::class.java)
        }
    }

    @Nested
    inner class WorkoutIT {

        @Test
        fun canAddWorkout() {
            val user = createUser()
            loginThen(user.email!!)
                    .contentType("application/json")
                    .body(mapOf(
                            "title" to "Leg day",
                            "description" to "never skip!",
                            "exercises" to listOf<Map<String, Any>>(
                                    mapOf(
                                            "id" to 1,
                                            "sets" to 3,
                                            "reps" to 12,
                                            "weight" to 20
                                    )
                            )
                    ))
                    .`when`()
                    .post("$url/api/v1/users/${user.id}/workouts")
                    .andThen()
                    .statusCode(200)

            val workouts = loginThen(user.email!!)
                    .contentType("application/json")
                    .`when`()
                    .get("$url/api/v1/users/${user.id}/workouts")
                    .andThen()
                    .statusCode(200)
                    .extract()
                    .body()
                    .`as`(ArrayNode::class.java)
            assertThat(workouts).hasSize(1)
        }

        @Test
        fun cantAddWorkoutIfNoExercisesGiven() {
            val user = createUser()
            loginThen(user.email!!)
                    .contentType("application/json")
                    .body(mapOf(
                            "title" to "Leg day",
                            "description" to "never skip!",
                            "exercises" to listOf<Nothing>()
                    ))
                    .`when`()
                    .post("$url/api/v1/users/${user.id}/workouts")
                    .andThen()
                    .statusCode(400)
                    .body("message", equalTo("There must be at least one exercise to save a workout."))
        }

        @Test
        fun cantAddWorkoutIfNotLoggedIn() {
            val user = createUser()
            given()
                    .contentType("application/json")
                    .body(mapOf(
                            "title" to "Leg day",
                            "description" to "never skip!",
                            "exercises" to listOf<Nothing>()
                    ))
                    .`when`()
                    .post("$url/api/v1/users/${user.id}/workouts")
                    .andThen()
                    .statusCode(401)
        }

    }

    private fun <T, R> Validatable<T, R>.andThen(log: Boolean = true): T
            where T : ValidatableResponseOptions<T, R>,
                  R : ResponseBody<R>,
                  R : ResponseOptions<R> {
        return if (log) {
            this.then().log().ifValidationFails()
        } else {
            this.then()
        }
    }


}