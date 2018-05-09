import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import Login from '@/components/Login'
import Register from '@/components/Register'
import Workouts from '@/components/Workouts'

Vue.use(Router)

export default new Router({
  routes: [
    {path: '*', component: Main},
    {path: '/login', component: Login},
    {path: '/register', component: Register},
    {path: '/workouts', component: Workouts}
  ]
})
