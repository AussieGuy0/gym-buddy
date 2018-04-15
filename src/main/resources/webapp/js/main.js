const credentials = {
    loggedIn: false,
    id: null,
    username: null
}

const services = new Services();

services.logcheck()
    .then(json => {
        credentials.loggedIn = true;
        credentials.id = json.id;
        credentials.username =  json.username;
    })

const Workouts = {
    template: `
    <div>
        <div class="row">
            <div class="col">
                <h1>Workouts</h1> 
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                    Add Workout
                    </div>
                    <div class="card-body">
                        <label>Title: <input type="text"/></label>
                        <label>Description: <input type="text"/></label>
                        <label>Date: <input type="date"/></label>
                        <label>Duration: <input type="text"/></label>
                    </div>
                </div> 
            </div>
        </div>
        <div class="row">
            <div class="col">
                <table class="table">
                      <tr v-for="workout in workouts">
                         <td>
                            {{workout}} 
                         </td> 
                      </tr>  
                </table>
            </div>
        </div>
    </div> 
    `,
    data () {
        return {
            workouts: []
        }
    },
    methods: {
       getWorkouts() {
           services.getWorkouts(credentials.id)
               .then(workouts => this.workouts = workouts)
       }
    },
    created() {
        this.getWorkouts()
    }
};

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
<button class="btn btn-primary" v-on:click="login">
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
                    router.push('/');
                    app.credentials.loggedIn = true;
                    app.credentials.username = json.username;
                    app.credentials.id = json.id;
                })
                .catch((err) => {
                    console.warn(err);
                })
        }
    }
};

const Register = {
    template: `<div>
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
`,
    data () {
        return {
            username: "",
            password: "",
            email: "",
            registered: false
        }
    },
    methods: {
        register() {
            services.register(this.username, this.password, this.email)
                .then(json => {
                    this.registered = true;
                })
        }
    }
}


const routes = [
    { path: '*', component: Main },
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
    },
    methods: {
        logout() {
            services.logout()
                .then(res => {
                    this.credentials.loggedIn = false
                    this.credentials.username = null
                    this.credentials.id = null
                })
        }
    }
}).$mount('#app')

