<template>
  <div id="app">
    是否允许注册:
    <a-switch :checked="initChecked" checked-children="开" un-checked-children="关" @change="changeAllowRegister"/>
  </div>
</template>

<script>
export default {
  name: 'Manager',
  data() {
    return {
      initChecked: undefined
    };
  },
  methods: {
    changeAllowRegister(checked) {
      this.$axios.post("/manager/changeAllowRegister", {"allowRegister": checked}).then((res) => {
        if (res) {
          this.$message.success("修改成功!");
        }
      })
      this.initChecked = checked;
    }
  },
  mounted: function () {
    this.$axios.get("/manager/allowRegister").then((res) => {
      this.initChecked = res.data;
    })
  }
}
</script>

<style scoped>

</style>
