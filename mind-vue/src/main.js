// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import 'ant-design-vue/dist/antd.css'
import Antd from 'ant-design-vue'
import App from './App'
import router from './router'
import axios from 'axios'
import request from './utils/request'

Vue.config.productionTip = false
Vue.use(Antd)
Vue.prototype.$axios = axios
Vue.prototype.request = request


const vue = new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
});

export default vue
