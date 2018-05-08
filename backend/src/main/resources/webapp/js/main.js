const credentials = {
    loggedIn: false,
    id: null,
    username: null
}

const services = new Services();

services.logcheck()
    .then(json => {
        app.credentials.loggedIn = true;
        app.credentials.id = json.id;
        app.credentials.username =  json.username;
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
                        <div class="row">
                            <div class="col">
                                <label>Title: <input v-model="newWorkout.title" type="text"/></label>
                            </div>
                            <div class="col">
                                <label>Date: <input v-model="newWorkout.date" type="date"/></label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <label>Description: <textarea v-model="newWorkout.description"> </textarea></label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <button class="btn btn-primary" v-on:click="addWorkout"> Add Workout </button>
                            </div>
                        </div>
                    </div>
                </div> 
            </div>
        </div>
        <div class="row">
           <h2>All Workouts</h2>
        </div>
        <div class="row">
            <div class="col">
                <table class="table">
                      <tr>
                         <th>Date</th> 
                         <th>Title</th> 
                         <th>Description</th> 
                      </tr>
                      <tr v-for="workout in workouts">
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
    `,
    data () {
        return {
            workouts: [],
            newWorkout: {

            }
        }
    },
    methods: {
       getWorkouts() {
           services.getWorkouts(app.credentials.id)
               .then(workouts => this.workouts = workouts)
       },
        addWorkout() {
           services.addWorkout(app.credentials.id, this.newWorkout.title, this.newWorkout.description, this.newWorkout.date)
               .then(json => {
                   this.newWorkout = {};
                   this.workouts.push(json)
               })
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
                    console.log(json);
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

