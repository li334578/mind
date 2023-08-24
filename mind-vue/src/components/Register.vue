<template>
  <a-form
    id="components-form-demo-normal-login"
    :form="form"
    class="login-form"
    @submit="handleSubmit"
  >
    <h1>输入信息完成注册</h1>
    <a-form-item>
      <a-input
        v-decorator="[
          'nickName',
          { rules: [{ required: true, message: '请输入昵称' }] },
        ]"
        placeholder="昵称"
      >
        <a-icon slot="prefix" type="smile" style="color: rgba(0,0,0,.25)"/>
      </a-input>
    </a-form-item>
    <a-form-item>
      <a-input
        v-decorator="[
          'username',
          { rules: [{ required: true, message: '请输入登录用户名' }] },
        ]"
        placeholder="登录用户名"
      >
        <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)"/>
      </a-input>
    </a-form-item>
    <a-form-item>
      <a-input
        v-decorator="[
          'password',
          { rules: [{ required: true, message: '请输入您的密码' }] },
        ]"
        type="password"
        placeholder="密码"
      >
        <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)"/>
      </a-input>
    </a-form-item>
    <a-form-item>
      <a-input
        v-decorator="[
          'email'
        ]"
        placeholder="邮箱"
      >
        <a-icon slot="prefix" type="mail" style="color: rgba(0,0,0,.25)"/>
      </a-input>
    </a-form-item>
    <a-form-item>
      <a-input
        v-decorator="[
          'phone'
        ]"
        placeholder="手机号"
      >
        <a-icon slot="prefix" type="phone" style="color: rgba(0,0,0,.25)"/>
      </a-input>
    </a-form-item>
    <a-form-item>
      <a-checkbox
        v-decorator="[
          'autoLogin',
          {
            valuePropName: 'checked',
            initialValue: true,
          },
        ]"
      >
        自动帮我登录
      </a-checkbox>
      <a-button type="primary" html-type="submit" class="login-form-button">
        注册
      </a-button>
      Or
      <router-link to="/login">去登录</router-link>
    </a-form-item>
  </a-form>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {}
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, {name: 'normal_login'});
  },
  methods: {
    handleSubmit(e) {
      let allowRegister = false;
      this.$axios.get("/manager/allowRegister").then((res) => {
        if (!res.data) {
          this.$message.error('管理员暂时关闭了注册功能');
        } else {
          allowRegister = true;
        }
        if (allowRegister) {
          e.preventDefault();
          this.form.validateFields((err, values) => {
            if (!err) {
              this.$axios.post("/user/doRegister", values).then((res) => {
                this.$message.success("注册成功");
              })
            }
          });
        }
      })
    },
  },
};
</script>
<style>
#components-form-demo-normal-login, .login-form {
  max-width: 300px;
  position: relative;
}

.login-form {
  margin: 0;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -0%);
}

#components-form-demo-normal-login .login-form-forgot {
  float: right;
}

#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
</style>
