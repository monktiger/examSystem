<template>
  <div class="error-page" :style="{'background': thiscolor }">
    <div class="box animated  zoomIn">
      <div class="error-code ">
        <div>GO HOME</div>
        <div>YOU'RE DRUNK</div>
      </div>
      <div class="error-desc">404 Not Found</div>
      <div class="error-handle">
        <router-link to="/">
          <div class="backhome">返回首页</div>
        </router-link>
        <div class="goback" @click="goBack">返回上一页</div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      color: [
        "rgb(222, 77, 0)",
        "rgb(18, 205, 85)",
        "rgb(32, 137, 218)",
        "rgb(222, 77, 0)"
      ],
      thiscolor: "#1C1C1C",
      num: 0,
      timer: null
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    bgcolor() {
      let len = this.color.length;
      if (this.num == len - 1) {
        this.num = 0;
      } else {
        ++this.num;
      }
      return (this.thiscolor = this.color[this.num]);
    },
    setTimer() {
      this.timer = setTimeout(() => {
        setInterval(this.bgcolor, 3000);
      }, 500);
    },
  },
  created: function() {
    this.setTimer();
    this.$once("hook:beforeDestroy", () => {
      clearInterval(setInterval);
    });
  }
};
</script>
<style scoped>

.error-code {
  position: relative;
  animation: error-code 1s ease-in-out 2s infinite alternate;
  -moz-animation: error-code 1s ease-in-out 2s infinite alternate; /* Firefox */
  -webkit-animation: error-code 1s ease-in-out 2s infinite alternate; /* Safari and Chrome */
  -o-animation: error-code 1s ease-in-out 2s infinite alternate; /* Opera */
}

/* //margin-top会发生卡顿
@keyframes heart{
from{margin-top:0px;}
to{margin-top:-6px;}
} */

@keyframes error-code {
  from {
    transform: translate(0, 0);
  }
  to {
    transform: translate(0, 20px);
  }
}

@-moz-keyframes error-code /* Firefox */ {
  from {
    transform: translate(0, 0);
  }
  to {
    transform: translate(0, 20px);
  }
}

@-webkit-keyframes error-code /* Safari 和 Chrome */ {
  from {
    transform: translate(0, 0);
  }
  to {
    transform: translate(0, 20px);
  }
}

@-o-keyframes error-code /* Opera */ {
  from {
    transform: translate(0, 0);
  }
  to {
    transform: translate(0, 20px);
  }
}
a{
  text-decoration: none;
}
.error-page {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  background: #f3f3f3;
  box-sizing: border-box;

  transition: all 0.5s ease 0s;
  -moz-transition: all 0.5s ease 0s; /* Firefox 4 */
  -webkit-transition: all 0.5s ease 0s; /* Safari 和 Chrome */
  -o-transition: all 0.5s ease 0s;
}
.box {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.error-code {
  line-height: 1;
  font-size: 50px;
  font-weight: bolder;
  color: whitesmoke;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.error-code div {
  margin-top: 20px;
}
.error-desc {
  margin-top: 60px;
  font-size: 30px;
  color: whitesmoke;
  font-size: 18px;
  font-weight: 300;
  font-family: Seravek, "Myriad Pro", Myriad, "Open Sans", "Helvetica Neue",
    Helvetica, Arial, "Arial Black", sans-serif;
}
.backhome{
  font-size: 30px;
  color: whitesmoke;
  font-size: 18px;
  font-weight: 400;
  font-family: Seravek, "Myriad Pro", Myriad, "Open Sans", "Helvetica Neue",
    Helvetica, Arial, "Arial Black", sans-serif;
    text-decoration:none;
  border:2px solid oldlace;
  display: inline-block;
  padding: 10px;
}
.goback{
  font-size: 30px;
  margin-left:150px;
  color: whitesmoke;
  font-size: 18px;
  font-weight: 400;
  font-family: Seravek, "Myriad Pro", Myriad, "Open Sans", "Helvetica Neue",
  Helvetica, Arial, "Arial Black", sans-serif;
  text-decoration:none;
  border:2px solid oldlace;
  display: inline-block;
  padding: 10px;
  cursor: pointer;
}
.error-handle {
  margin-top: 50px;
  padding-bottom: 50px;
}
.error-btn {
  margin-left: 100px;
}
</style>