import { Login } from "../../api/index"
import {  setLoginStatus, getLoginStatus, removeLoginStatus } from "../../utils/app"
const state = {
    isCollapse: JSON.parse(sessionStorage.getItem('isCollapse')) || false,
    status: getLoginStatus() || '',
}
const getters = {
    isCollapse: state => state.isCollapse,
    status: state => state.status,
}
const mutations = {
    // 设置菜单的开闭
    SET_COLLAPSE(state) {
        state.isCollapse = !state.isCollapse;
        sessionStorage.setItem('isCollapse', JSON.stringify(state.isCollapse));
    }, 
    // 设置status
    SET_STATUS(state, value) {
        state.status = value;
        sessionStorage.setItem('token', JSON.stringify(state.status));
    },
    REMOVE_STATUS(state) {
        state.status = '';
    },
}
const actions = {
    login(content, data) {
        return new Promise((resolve, reject) => {
            // 获得status数据
            Login(data).then((response) => {
                let data = response.data;
                console.log(data);
                // 将登录成功status存储起来
                content.commit("SET_STATUS", data.token.split(':')[1]);
                setLoginStatus(data.token);
                resolve(response);
            }).catch((error) => {
                reject(error);
            })
        })
    },
    exit(content) {
        return new Promise((resolve) => {
            // 删除状态
            removeLoginStatus();
            content.commit("REMOVE_STATUS");
            resolve();
        })
    }
}
export default {
    namespaced: true,//命名空间
    state,
    getters,
    actions,
    mutations
};