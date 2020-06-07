<template>
  <div id="home" :class="[slidechange ? 'close':'open']">
    <el-container>
      <el-header>
        <Header></Header>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <Slidebar></Slidebar>
        </el-aside>
        <el-main>
          <Main></Main>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import Header from "./Header";
import Main from "./Main";
import Slidebar from "./Slidebar";
export default {
  components: {
    Header,
    Main,
    Slidebar
  },
  computed:{
    slidechange(){
      return this.$store.state.app.isCollapse
    }
  },created(){

  }
};
</script>

<style lang="less">
* {
  padding: 0px;
  margin: 0px;
}
.el-header {
    padding: 0 0 !important;
    height: 70px !important;
}
.el-aside {
  // position: fixed;
  height: calc(100vh - 70px);
 transition: all .5s ease 0s ;
  -moz-transition:  all .5s ease 0s ; /* Firefox 4 */
  -webkit-transition:  all .5s ease 0s ; /* Safari 和 Chrome */
  -o-transition:  all .5s ease 0s;
}
.el-main {
  // float: right;
  height: calc(100vh - 70px);
  background-color: #ebeef5;
      overflow-y: auto;
}
.open{
  .el-aside{width: 200px!important;}
}
.close{
  .el-aside{width: 70px !important;}
}
</style>