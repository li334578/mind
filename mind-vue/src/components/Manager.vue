<template>
  <div id="app">
    是否允许注册:
    <a-switch :checked="initChecked" checked-children="开" un-checked-children="关" @change="changeAllowRegister"/>
    <p></p>
    <a-table :columns="columns" :data-source="userList">
      <a slot="nickName" slot-scope="text">{{ text }}</a>
      <span slot="customTitle"><a-icon type="smile-o"/> 昵称</span>
      <span slot="roleList" slot-scope="roleList">
      <a-tag
        v-for="role in roleList"
        :key="role.id"
        :color="role.roleCode === 'admin' ? 'volcano' : 'green'"
      >
        {{ role.roleCode }}
      </a-tag>
    </span>
      <span slot="action" slot-scope="text, record">
      <a>添加角色</a>
      <a-divider type="vertical"/>
      <a>删除用户</a>
    </span>
    </a-table>
  </div>
</template>

<script>
export default {
  name: 'Manager',
  data() {
    return {
      initChecked: undefined,
      columns: [
        {
          dataIndex: 'nickName',
          key: 'nickName',
          slots: {title: 'customTitle'},
          scopedSlots: {customRender: 'nickName'},
        },
        {
          title: '账户',
          dataIndex: 'username',
          key: 'username',
        },
        {
          title: '邮箱',
          dataIndex: 'email',
          key: 'email',
        },
        {
          title: '手机',
          dataIndex: 'phone',
          key: 'phone',
        },
        {
          title: '角色',
          key: 'roleList',
          dataIndex: 'roleList',
          scopedSlots: {customRender: 'roleList'},
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: {customRender: 'action'},
        },
      ],
      userList: []
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
    },
    getUserPage() {
      this.$axios.post("/manager/pageUserInfo", {"currentPage": 1, "pageSize": 10}).then((res) => {
        console.log(res);
        this.userList = res.data.records;
      })
    }
  },
  mounted: function () {
    this.$axios.get("/manager/allowRegister").then((res) => {
      this.initChecked = res.data;
    })
    this.getUserPage();
  }
}
</script>

<style scoped>

</style>
