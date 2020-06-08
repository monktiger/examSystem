import router from "./index"
import { removeLoginStatus, getLoginStatus } from "../utils/app"
import store from "../store/index"
// 守卫路由
const whiteRouter = ['/login'];
router.beforeEach((to, from, next) => {
    // 登录
    if (getLoginStatus()) {
        // 去的页面是登录页面,将status设置为0
        if (to.path === '/login') {
            removeLoginStatus();
            store.commit('app/REMOVE_STATUS');
            next();
        // 否则就去
        } else {
            next()
        }
    } 
    // 未登录
    else {
        if (whiteRouter.indexOf(to.path) !== -1) {  //存在
            next(); //不带参数则会去to的页面
        } else {
            next('/login')//index带参数则回去跑beforeEach
        }
    }

})