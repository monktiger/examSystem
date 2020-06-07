<template>
  <div class="main">
    <!-- <div class="main-box"> -->
    <router-view></router-view>
    <!-- </div> -->
  </div>
</template>
<style scoped>
.main {
  background: white;
  padding: 20px;
  box-sizing: border-box;
  box-shadow: 0 5px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  min-height: 100%;
}
</style>
<script>
export default {};
</script>