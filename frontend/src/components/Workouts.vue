<template>
  <div>
    <div class='row'>
      <div class='col'>
        <h1>Workouts</h1>
      </div>
    </div>
    <div class='row'>
      <div class='col'>
        <div class='card'>
          <div class='card-header'>
            Add Workout
          </div>
          <div class='card-body'>
            <div class='row'>
              <div class='col'>
                <label>Title: <input v-model='newWorkout.title' type='text'/></label>
              </div>
              <div class='col'>
                <label>Date: <input v-model='newWorkout.date' type='date'/></label>
              </div>
            </div>
            <div class='row'>
              <div class='col'>
                <label>Description: <textarea v-model='newWorkout.description'> </textarea></label>
              </div>
            </div>
            <div class='row'>
              <div class='col'>
                <button class='btn btn-primary' v-on:click='addWorkout'> Add Workout </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class='row'>
      <h2>All Workouts</h2>
    </div>
    <div class='row'>
      <div class='col'>
        <table class='table'>
          <tr>
            <th>Date</th>
            <th>Title</th>
            <th>Description</th>
          </tr>
          <tr v-for='workout in workouts'>
            <td>
              {{workout.date}}
            </td>
            <td>
              {{workout.title}}
            </td>
            <td>
              {{workout.description}}
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>

</template>

<script>
  // import axios from 'axios'

  import Services from './services'
  import {session as credentials} from '../session'

  export default {
    name: 'bootstrap',
    data () {
      return {
        workouts: [],
        newWorkout: {}
      }
    },
    methods: {
      getWorkouts () {
        Services.getWorkouts(credentials.id)
          .then(workouts => { this.workouts = workouts })
      },
      addWorkout () {
        Services.addWorkout(credentials.id, this.newWorkout.title, this.newWorkout.description, this.newWorkout.date)
          .then(json => {
            this.newWorkout = {}
            this.workouts.push(json)
          })
      }
    },
    created () {
      Services.getWorkouts()
    }
  }

</script>

<style scoped>
</style>
