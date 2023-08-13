<template>
  <a-layout id="components-layout-demo-fixed">
    <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }">
      <div class="logo"/>
      <a-menu
        theme="dark"
        mode="horizontal"
        :default-selected-keys="['1']"
        :style="{ lineHeight: '64px' }"
      >
        <a-menu-item key="1">
          <router-link to="/">mind</router-link>
        </a-menu-item>
        <a-menu-item key="2">
          <router-link to="/bookSy">nav2</router-link>
        </a-menu-item>
        <a-menu-item key="3">
          <router-link to="/layout">nav3</router-link>
        </a-menu-item>
      </a-menu>
    </a-layout-header>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
      <div :style="{ background: '#fff', padding: '24px', minHeight: '380px' }">
        <router-view/>
      </div>
    </a-layout-content>
    <a-layout-footer :style="{ textAlign: 'center' }">
      Ant Design ©2018 Created by Ant UED
    </a-layout-footer>
  </a-layout>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {};
  },
  mounted: function () {
    let path = this.$route.path;
    // 页面加载完成之后，获取用户信息
    const tokenInfo = localStorage.getItem("tokenInfo");
    if (path === '/login' || path === '/register' || tokenInfo) {
      // 不用跳转
    } else {
      // 转到登录页面
      if (path !== '/login') {
        this.$router.push('/login')
      }
    }
  }
}
</script>

<style>
#components-layout-demo-fixed .logo {
  width: 120px;
  height: 31px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px 24px 16px 0;
  float: left;
}
</style>
