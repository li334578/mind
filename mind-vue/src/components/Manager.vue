<template>
  <div id="app">
    <h2>用户管理</h2>
    <div style="margin-bottom: 1%">
      是否允许注册:
      <a-switch :checked="initChecked" checked-children="开" un-checked-children="关" @change="changeAllowRegister"/>
    </div>
    <a-table :pagination="userPagination" @change="updateUserTable" :columns="userColumns" :data-source="userList">
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
      <a @click="showAddUserRoleDialog(record)">修改角色</a>
      <a-divider type="vertical"/>
      <a>删除用户</a>
    </span>
    </a-table>
    <hr>
    <h2>角色管理</h2>
    <a-button type="primary" style="margin-bottom: 1%" @click="showAddRoleDialog">
      添加角色
    </a-button>
    <a-table :pagination="rolePagination" @change="updateRoleTable" :columns="roleColumns" :data-source="roleList">
      <span slot="action" slot-scope="text, record">
      <a @click="deleteRole(record)">删除角色</a>
    </span>
    </a-table>

    <a-modal v-model="visibleAddUserRole" :destroyOnClose="true" :title=this.currentDialogUser.title ok-text="确认"
             cancel-text="取消"
             @ok="confirmUpdateUserRole"
             destroyOnClose:=true>
      <a-select
        mode="multiple"
        size="default"
        placeholder="请选择"
        :defaultValue="this.defaultSelectRole"
        style="width: 200px"
        @change="handleChange"
        @popupScroll="popupScroll"
      >
        <a-select-option v-for="role in this.allRoleList" :key="role.id">
          {{ role.roleName }}
        </a-select-option>
      </a-select>
    </a-modal>

    <a-modal v-model="visibleAddRole" :destroyOnClose="true" title="添加角色" ok-text="确认"
             cancel-text="取消"
             @ok="commitRoleBean"
             destroyOnClose:=true>
      <a-form-item label="角色名">
        <a-input v-model="addRoleBean.roleName" placeholder="请输入角色名"/>
      </a-form-item>
      <a-form-item label="角色编码">
        <a-input v-model="addRoleBean.roleCode" placeholder="请输入角色编码"/>
      </a-form-item>
    </a-modal>
  </div>
</template>

<script>
export default {
  name: 'Manager',
  data() {
    return {
      initChecked: undefined,
      visibleAddUserRole: false,
      visibleAddRole: false,
      currentDialogUser: {
        title: ""
      },
      userColumns: [
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
      roleColumns: [
        {
          title: '角色名称',
          dataIndex: 'roleName',
          key: 'roleName',
        },
        {
          title: '角色编码',
          dataIndex: 'roleCode',
          key: 'roleCode',
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: {customRender: 'action'},
        },
      ],
      userList: [],
      roleList: [],
      allRoleList: [],
      defaultSelectRole: [],
      newSelectRole: [],
      userPagination: {
        current: 1,
        showSizeChanger: true,
        showQuickJumper: true,
        defaultPageSize: 10,
        total: 0,
        pageSize: 10,
        showTotal: total => `共 ${total} 条数据`,
      },
      rolePagination: {
        current: 1,
        showSizeChanger: true,
        showQuickJumper: true,
        defaultPageSize: 10,
        total: 0,
        pageSize: 10,
        showTotal: total => `共 ${total} 条数据`,
      },
      addRoleBean: {
        roleName: "",
        roleCode: "",
      },
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
      let pageRequest = {
        currentPage: this.userPagination.current,
        pageSize: this.userPagination.pageSize,
      }
      this.$axios.post("/manager/pageUserInfo", pageRequest).then((res) => {
        this.userList = res.data.records
        this.userPagination.total = res.data.total
      })
    },
    getRolePage() {
      let pageRequest = {
        currentPage: this.rolePagination.current,
        pageSize: this.rolePagination.pageSize,
      }
      this.$axios.post("/role/page", pageRequest).then((res) => {
        this.roleList = res.data.records
        this.rolePagination.total = res.data.total
      })
    },
    getRoleList() {
      this.$axios.post("/role/list", {}).then((res) => {
        this.allRoleList = res.data;
      })
    },
    updateUserTable(value) {
      this.userPagination = value
      this.getUserPage();
    },
    updateRoleTable(value) {
      this.rolePagination = value
      this.getRolePage();
    },
    showAddUserRoleDialog(value) {
      this.visibleAddUserRole = true
      this.currentDialogUser = value;
      this.currentDialogUser.title = "给 [" + this.currentDialogUser.nickName + "] 分配角色"
      this.getRoleList()
      this.defaultSelectRole = this.currentDialogUser.roleList.map((item) => item.id)
    },
    handleChange(value) {
      this.newSelectRole = value;
      console.log(`Selected: ${value}`);
    },
    popupScroll() {
      console.log('popupScroll');
    },
    confirmUpdateUserRole() {
      // 更新用户的角色信息
      this.$axios.post("/manager/updateToleToUser",
        {"userId": this.currentDialogUser.id, "roleIdList": this.newSelectRole}).then((res) => {
        this.visibleAddUserRole = false
        if (res.success) {
          this.$message.success("更新成功");
        } else {
          this.$message.error("更新失败");
        }
        this.getUserPage()
      });
    },
    deleteRole(value) {
      console.log(value)
      this.$axios.delete("/role?idList=" + [value.id]).then((res) => {
        if (res.success) {
          this.$message.success("删除成功");
          this.getRolePage();
        } else {
          this.$message.error("删除失败");
        }
      })
    },
    showAddRoleDialog() {
      this.visibleAddRole = true
    },
    commitRoleBean() {
      this.$axios.post("/role", this.addRoleBean).then((res) => {
        if (res.success) {
          this.$message.success("添加成功");
          this.visibleAddRole = false
          this.addRoleBean = {}
          this.getRolePage();
        }
      })
    }
  },
  mounted: function () {
    this.$axios.get("/manager/allowRegister").then((res) => {
      this.initChecked = res.data;
    })
    this.getUserPage();
    this.getRolePage();
  }
}
</script>

<style scoped>

</style>
