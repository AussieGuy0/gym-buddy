package dev.anthonybruno.gymbuddy

import dev.anthonybruno.gymbuddy.auth.AuthenticationController
import dev.anthonybruno.gymbuddy.auth.AuthenticationService
import dev.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher
import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.exception.HttpException
import dev.anthonybruno.gymbuddy.user.DbUserRepository
import dev.anthonybruno.gymbuddy.user.UserController
import dev.anthonybruno.gymbuddy.user.UserService
import dev.anthonybruno.gymbuddy.workout.*

import io.javalin.Javalin

import io.javalin.apibuilder.ApiBuilder.*
import org.slf4j.LoggerFactory
import java.lang.Exception


class Routes(private val app: Javalin, private val database: Database) {

    private val log = LoggerFactory.getLogger(Routes::class.java)

    // When you don't have auto dependency injection, this is what you do!
    private val passwordHasher = BcryptPasswordHasher()

    private val userRepository = DbUserRepository(passwordHasher, database)

    private val authenticationController = AuthenticationController(AuthenticationService(userRepository, passwordHasher))
    private val userController = UserController(UserService(userRepository))
    private val workoutController = WorkoutController(WorkoutService(DbWorkoutRepository(database)))
    private val exerciseController = ExerciseController(ExerciseService(DbExerciseRepository(database)))

    fun setupEndpoints() {
        app.routes {
            path("/auth") {
                post("/login") { authenticationController.login(it) }
                post("/logout") { authenticationController.logout(it) }
                post("/logcheck") { authenticationController.logCheck(it) }
            }
            path("api/v1") {
                path("/users") {
                    post { userController.addUser(it) }
                    path("/:userId") {
                        get { userController.getUser(it) }
                        put { userController.editUser(it) }
                        post { userController.addUser(it) }
                        delete { userController.deleteUser(it) }
                        path("/workouts") {
                            post { workoutController.addWorkout(it) }
                            get { workoutController.getWorkouts(it) }
                            get("/stats") { workoutController.getStats(it) }
                        }
                    }
                }
                path("/exercises") {
                    get { exerciseController.getExercises(it) }
                }
            }
        }

        app.exception(HttpException::class.java) { exception, ctx ->
            ctx.status(exception.statusCode)
            ctx.json(exception.asProblemDetails())
        }

        app.exception(Exception::class.java) { exception, ctx ->
            log.error("Unhandled exception", exception)
            ctx.status(500)
        }
    }

}
