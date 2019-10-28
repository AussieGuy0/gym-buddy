package dev.anthonybruno.gymbuddy.workout

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.util.Date

data class Workout(val id: Int,
                   val date: Instant,
                   val title: String?,
                   val description: String?,
                   val exercises: List<Exercise>)

data class Exercise(val id: Int, val name: String, val description: String?, val mainMuscle: String)