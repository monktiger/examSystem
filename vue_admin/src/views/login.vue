<template>
  <div class="login">
    <div class="ms-title">随测后台管理系统</div>
    <div class="ms-login">
      <el-form
        :model="param"
        :rules="rules"
        ref="login"
        label-width="0px"
        status-icon
        class="ms-content"
      >
        <el-form-item prop="username">
          <el-input v-model="param.username" placeholder="username">
            <el-button slot="prepend" icon="el-icon-maobussiness-man" class="pwdBtn"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="password" v-model="param.password">
            <el-button slot="prepend" icon="el-icon-maopassword" class="pwdBtn"></el-button>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button
            type="success"
            @click="submitForm()"
            class="loginBtn"
            :disabled="loginstatus"
          >{{loginText}}</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    var validatorUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("username不能为空"));
      } else {
        callback();
      }
    };
    var validatorPassword = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请输入密码"));
      } else {
        callback();
      }
    };
    return {
      param: {
        username: "",
        password: ""
      },
      rules: {
        username: [{ validator: validatorUsername, trigger: "blur" }],
        password: [{ validator: validatorPassword, trigger: "blur" }]
      },
      loginstatus: false,
      loginText: "登录"
    };
  },
  methods: {
    submitForm() {
      this.$refs.login.validate(valid => {
        if (valid) {
          this.$store
            .dispatch("app/login", this.param)
            .then(() => {
              this.$message.success("登陆成功");
              this.loginstatus = false;
              this.loginText = "登陆";
              this.$router.push({ path: "/index" });           
            })
            .catch(() => {});
        } else {
          console.log("error!");
          return false;
        }
      });
    }
  },
  created: function() {}
};
</script>
<style scoped>
.login {
  height: 100vh;
  width: 100vw;
  background: #242f42;
}
.ms-title {
  position: absolute;
  top: 15%;
  width: 100%;
  line-height: 100px;
  text-align: center;
  font-size: 30px;
  color: #fff;
  letter-spacing: 10px;
  /* border-bottom: 1px solid #ddd; */
}
.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -140px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}
.ms-content {
  padding: 30px 30px;
}
.login-btn {
  text-align: center;
  /* background:#409EFF; */
}
.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}
.loginBtn {
  background: transparent;
  border: 2px solid rgb(255, 255, 255, 0.6);
}
.pwdBtn {
  padding: 10px 10px !important;
}
</style>
