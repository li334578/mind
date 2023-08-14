<template>
  <a-layout id="components-layout-demo-fixed">
    <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }">
      <div class="logo"/>
      <a-menu
        theme="dark"
        mode="horizontal"
        :selectedKeys="[selectKey]"
        :style="{ lineHeight: '64px' }"
        @select="selectMenu"
      >
        <a-menu-item :key="1">
          <router-link to="/">mind</router-link>
        </a-menu-item>
        <a-menu-item :key="2">
          <router-link to="/manager">manager</router-link>
        </a-menu-item>
        <a-menu-item :key="3">
          <router-link to="/layout">nav3</router-link>
        </a-menu-item>
      </a-menu>
      <div class="logout" v-if="display">
        <a-button @click="logout" type="primary" ghost>退出登录</a-button>
      </div>
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
    return {
      selectKey: 1,
      display: undefined,
    };
  },
  methods: {
    selectMenu({item, key, selectedKeys}) {
      this.selectKey = key;
    },
    logout() {
      this.$axios.get("/user/doLogout").then((res) => {
        localStorage.removeItem("tokenInfo")
        this.$message.success("登出成功")
        if (this.$route.path !== '/login') {
          this.$router.push('/login')
        }
      })
    },
  },
  mounted: function () {

  },
  computed: {
    path: function () {
      return this.$route.path;
    }
  },
  watch: {
    path: function (val) {
      const tokenInfo = localStorage.getItem("tokenInfo");
      this.display = !!tokenInfo;
      if (val === '/login' || val === '/register' || tokenInfo) {
        // 不用跳转
        if (val === '/') {
          this.selectKey = 1
        } else if (val === '/manager') {
          this.selectKey = 2
        } else if (val === '/layout') {
          this.selectKey = 3
        }
      } else {
        this.selectKey = 1
        // 转到登录页面
        if (val !== '/login') {
          this.$router.push('/login')
        }
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

.logout {
  float: right;
  margin: -65px 0 0 0;
}
</style>
