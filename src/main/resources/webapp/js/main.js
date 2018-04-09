const Main = { template: '<div>foo</div>' }
const Login = { template: '<div>foo</div>' }
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

