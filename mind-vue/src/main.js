// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import 'ant-design-vue/dist/antd.css'
import Antd from 'ant-design-vue'
import App from './App'
import router from './router'
import axios from 'axios'

Vue.config.productionTip = false
Vue.use(Antd)
Vue.prototype.$axios = axios


// 请求拦截器
axios.interceptors.request.use(
  config => {
    let token = localStorage.getItem('tokenInfo');
    if (token) {
      let parseToken = JSON.parse(token);
      let tokenName = parseToken.tokenName;
      let tokenValue = parseToken.tokenValue;
      config.headers[tokenName] = tokenValue;
    }
    config.url = "/api" + config.url
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
axios.interceptors.response.use(
  response => {
    let code = response.data.code;
    if (code === 200) {
      return response.data;
    } else if (code === 401) {
      // 去登陆
      localStorage.removeItem("tokenInfo")
      router.push("/login")
    } else {
      alert("报错了" + response.data.msg)
    }
  },
  error => {
    return Promise.reject(error)
  }
)

new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})
