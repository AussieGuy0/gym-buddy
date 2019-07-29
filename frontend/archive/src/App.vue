<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
      <div class="container">
        <a class="navbar-brand" href="#">Gym Buddy</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto">
            <li v-if="credentials.loggedIn" class="nav-item">
              <router-link class="nav-link" to="workouts">Workouts</router-link>
            </li>
            <li v-if="!credentials.loggedIn" class="nav-item">
              <router-link class="nav-link" to="login">Login</router-link>
            </li>
            <li v-if="!credentials.loggedIn" class="nav-item">
              <router-link class="nav-link" to="register">Register</router-link>
            </li>
            <li v-if="credentials.loggedIn" class="nav-item" v-on:click="logout">
              <span class="nav-link">Log Out</span>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  import services from './components/services'
  import {session as credentials} from './session'

  export default {
    name: 'app',

    data () {
      return {
        credentials: credentials
      }
    },
    methods: {
      logout () {
        services.logout()
          .then(res => {
            credentials.loggedIn = false
            credentials.username = null
            credentials.id = null
          })
      }
    },
    created () {
      services.logcheck()
        .then(json => {
          credentials.loggedIn = true
          credentials.id = json.id
          credentials.username = json.username
        })
    }
  }
</script>

<style>
</style>
