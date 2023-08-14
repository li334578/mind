import axios from 'axios'
import vue from '../main.js'


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
      vue.$notification['error']({
        message: '错误提示',
        description: response.data.msg,
      });
      // 去登陆
      localStorage.removeItem("tokenInfo")
      vue.$router.push("/login")
    } else {
      vue.$notification['error']({
        message: '错误提示',
        description: response.data.msg,
      });
    }
  },
  error => {
    return Promise.reject(error)
  }
)
export default {}


