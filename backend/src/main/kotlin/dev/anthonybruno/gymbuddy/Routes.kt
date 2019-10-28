package dev.anthonybruno.gymbuddy

import dev.anthonybruno.gymbuddy.auth.AuthenticationController
import dev.anthonybruno.gymbuddy.exception.HttpException
import dev.anthonybruno.gymbuddy.user.UserController
import dev.anthonybruno.gymbuddy.user.UserServiceImpl
import dev.anthonybruno.gymbuddy.workout.ExerciseController
import dev.anthonybruno.gymbuddy.workout.WorkoutController

import io.javalin.Javalin

import io.javalin.apibuilder.ApiBuilder.*


class Routes(private val app: Javalin) {

    private val authenticationController = AuthenticationController()
    private val userController = UserController()
    private val workoutController = WorkoutController()
    private val exerciseController = ExerciseController()

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
            ctx.json(exception.serialise())
        }
    }

}
