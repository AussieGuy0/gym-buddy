<template>
  <div>
    <h1>Register</h1>
    <form v-on:submit.prevent v-if="!registered">
      <label>
        Username
        <input v-model="username" type="text"/>
      </label>
      <label>
        Password
        <input v-model="password" type="password"/>
      </label>
      <label>
        Email
        <input v-model="email" type="email"/>
      </label>
      <button v-on:click="register" class="btn btn-primary">
        Register
      </button>
    </form>
    <h2 v-if="registered">Registered Successfully!</h2>
  </div>
</template>

<script>
  import Services from './services'

  export default {
    data () {
      return {
        username: '',
        password: '',
        email: '',
        registered: false
      }
    },
    methods: {
      register () {
        Services.register(this.username, this.password, this.email)
          .then(json => {
            this.registered = true
          })
      }
    }
  }

</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
