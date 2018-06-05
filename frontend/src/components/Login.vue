`
`,

<template>
  <div>
    <h1>Login</h1>
    <form v-on:submit.prevent>
      <label>
        Username
        <input v-model='username' type='text'/>
      </label>
      <label>
        Password
        <input v-model='password' type='password'/>
      </label>
      <button class='btn btn-primary' v-on:click='login'>
        Login
      </button>
    </form>
  </div>
</template>

<script>
  import Services from './services'
  import {session as credentials} from '../session'

  export default {
    data () {
      return {
        username: '',
        password: '',
        credentials: credentials
      }
    },
    methods: {
      login () {
        Services.login(this.username, this.password)
          .then((json) => {
            this.$router.push('')
            credentials.loggedIn = true
            credentials.username = json.username
            credentials.id = json.id
          })
          .catch((err) => {
            console.warn(err)
          })
      }
    }
  }

</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
</style>
