const credentials = {
    loggedIn: false,
    id: null,
    username: null
}

const services = new Services();

const Main = {
    template: '<div>foo {{lol}}</div>',
    data () {
        return {
            lol: "bla"
        }
    }
};

const Login = {
    template: `<div>
<h1>Login</h1>
<form v-on:submit.prevent>
<label>
    Username
    <input v-model="username" type="text"/>
</label>
<label>
    Password
    <input v-model="password" type="password"/>
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
            services.login(this.username, this.password)
                .then((json) => {
                    app.credentials.loggedIn = true;
                    app.credentials.username = json.username;
                    app.credentials.id = json.id;
                })
        }
    }
};

const Register = {
    template: `<div>
<h1>Register</h1>
<form v-on:submit.prevent>
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
<button v-on:click="register">
    Register
</button>
</form>
</div>
`,
    data () {
        return {
            username: "",
            password: "",
            email: ""
        }
    },
    methods: {
        register() {
            services.register(this.username, this.password, this.email)
                .then(json => {
                    console.log(json);
                })
        }
    }
}

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
    router,
    data: {
        credentials: credentials
    }
}).$mount('#app')

