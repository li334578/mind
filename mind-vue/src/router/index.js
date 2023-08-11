import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import BookSy from '@/components/BookSy'
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
      path: '/bookSy',
      name: 'BookSy',
      component: BookSy
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
