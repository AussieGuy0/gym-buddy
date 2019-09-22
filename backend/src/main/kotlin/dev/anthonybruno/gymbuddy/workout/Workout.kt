package dev.anthonybruno.gymbuddy.workout

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty

import java.time.Duration
import java.time.LocalDate
import java.util.Date

data class Workout(val id: Int, @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
val date: LocalDate,  val title: String,  val description: String)
