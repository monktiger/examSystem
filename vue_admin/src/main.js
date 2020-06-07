import Vue from "vue";
import App from "./App.vue";
import axios from 'axios'
import router from "./router/index";
import store from "./store";
import './plugins/element.js';
import animate from 'animate.css';
import './assets/icon/iconfont.css';
import './router/premit'
Vue.config.productionTip = false;
axios.defaults.withCredentials=true;
new Vue({
  axios,
  router,
  store,
  animate,
  render: h => h(App)
}).$mount("#app");