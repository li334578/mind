<template>
  <div id="app">
    <h1>这是 main {{ msg }}</h1>
  </div>
</template>
<script>
export default {
  name: 'Main',
  mounted: function () {
    this.$axios.get("/hello/world").then((res) => {
      console.log(res);
    })
  },
  data() {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>
