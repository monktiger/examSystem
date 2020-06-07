<template>
  <div class="sidebar">
    <el-menu
      default-active="/informationentry"
      class="el-menu-vertical-demo"
      :collapse="slidechange"
      background-color="#324157"
      text-color="rgb(191, 203, 217)"
      active-text-color="#409EFF"
      router
    >
      <template v-for="(item,index) in routers">
        <el-menu-item :index="item.path" :key="index">
          <i :class="item.meta.icon"></i>
          <span slot="title" class="icon-text">{{item.meta.name}}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      routers: this.$root._router.options.routes[2].children,
      isCollapse: this.$store.state.app.isCollapse,
      // managementRouter: this.$root._router.options.routes[4]
    };
  },
  methods: {},
  created() {
    // console.log(this.managementRouter.path);
  },
  computed: {
    slidechange() {
      return this.$store.state.app.isCollapse;
    }
  }
};
</script>

<style scoped>
ul {
  min-height: 80px !important;
}

.sidebar {
  background-color: rgb(50, 65, 87);
  color: #fff;
  height: calc(100vh - 70px);
}

.el-menu-item:focus {
  background-color: rgb(40, 52, 70) !important;
}

.el-menu-item {
  font-size: 14px;
}

.el-menu {
  border-right: 0px !important;
  list-style: none;
  position: relative;
  margin: 0;
  padding-left: 0;
  background-color: #ffffff;
}
.icon-text {
  margin-left: 15px;
}
</style>