import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import Manager from '@/components/Manager'
import Layout from '@/components/Layout'
import Login from '@/components/Login'
import Register from '@/components/Register'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Main',
      component: Main
    },
    {
      path: '/manager',
      name: 'Manager',
      component: Manager
    },
    {
      path: '/layout',
      name: 'Layout',
      component: Layout
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    }
  ]
})
