<template>
  <div class="header">
    <div class="collapse-btn" @click="collapseChage">
      <i v-if="!collapse" class="el-icon-s-fold"></i>
      <i v-else class="el-icon-s-unfold"></i>
    </div>
    <div class="logo">随测管理端</div>
    <div class="header-right">
      <div class="header-user-con">
        <!-- 用户头像 -->
        <div class="user-avator">
          <img src="../assets/img/索罗.jpg" />
          <span class="username">{{username}}</span>
          <span class="esc el-icon-switch-button" @click="quit"></span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      collapse: this.$store.state.app.isCollapse || false,
      username: this.$store.state.app.username
    };
  },
  methods: {
    collapseChage() {
      (this.collapse = !this.collapse), this.$store.commit("app/SET_COLLAPSE");
    },
    quit() {
      this.$confirm("确定要退出吗", "提示", {
        type: "warning"
      })
        .then(() => {
          this.$store.dispatch("app/exit").then(() => {
            this.$router.push({ path: "/login" });
          });
        })
        .catch(() => {}); //一定别忘了这个;
    }
  }
};
</script>
<style scoped>
.header {
  width: 100%;
  height: 70px;
  font-size: 22px;
  color: #fff;
  background-color: #242f42;
}
.collapse-btn {
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 70px;
}
.header .logo {
  float: left;
  width: 250px;
  line-height: 70px;
}
.header-right {
  float: right;
  padding-right: 50px;
}
.user-name {
  margin-left: 10px;
}
.user-avator {
  margin-left: 20px;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 15px;
  font-size: 14px !important;
}
.user-avator img {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 15px;
}
.esc {
  margin-left: 15px;
  cursor: pointer;
  font-size: 20px;
}
</style>
