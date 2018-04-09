const Main = {
    template: '<div>foo {{lol}}</div>',
    data () {
        return {
            lol: "bla"
        }
    }
};

const Login = {
    template: `<div>foo
<form v-on:submit.prevent>
<label>
    Username
    <input v-model="username" type="text"/>
</label>
<label>
    Password
    <input v-model="password" type="text"/>
</label>
<button v-on:click="login">
    Login
</button>
</form>
</div>
`,
    data () {
        return {
            username: "",
            password: ""
        }
    },
    methods: {
        login() {
            login(this.username, this.password)
        }
    }
};

const Register = { template: '<div>bar</div>' }
const Workouts = { template: '<div>bar</div>' }

const routes = [
    { path: '/', component: Main },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/workouts', component: Workouts },
]

const router = new VueRouter({
    routes // short for `routes: routes`
})

const app = new Vue({
    router
}).$mount('#app')

