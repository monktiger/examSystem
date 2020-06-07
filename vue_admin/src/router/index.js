import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);
/** 
 * 系统分配
 * 角色分配
*/

/** 
 * 1.默认路由，所有人都可以访问
 * 2.动态路由
*/

// 定义一个默认的路由
export const defaultRouterMap = [
	{
		path: "/",
		redirect: "/login"
	},
	{
		path: "/login",
		name: 'login',
		component: () => import("../views/login"),
	},
	{
		path: "/index",
		component: () => import("../components/Home"),
		redirect: "/introduction",
		children: [
			{
				path: "/introduction",
				meta: { name: "管理试卷", icon: "el-icon-menu"},
				component: () => import("../views/index")
			},
			{
				path: "/InformationManagement",
				meta: { name: "管理试题", icon: "el-icon-tickets"},
				component: () => import("../views/InformationManagement")
		
			},
			{
				path: "/carouselPictures",
				meta: { name: "管理组", icon: "el-icon-setting"},
				component: () => import("../views/CarouselPictures")
			},
			{
				path: "/reviewSharedURL",
				meta: { name: "管理人员", icon: "el-icon-view" },
				component: () => import("../views/ReviewSharedURL")
			},
		],
	}, 
	{
		path: "*",
		name: 'error',
		component: () => import("../views/404"),
	},
];

const router = new VueRouter({
	routes: defaultRouterMap
});

export default router;
