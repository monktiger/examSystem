
import axios from "axios"
axios.defaults.withCredentials = true

axios.interceptors.request.use(
    config => {
      config.headers = {
       'Origin': 'http://localhost:8080/',
      }
     return config
    },
    error => {
     return Promise.reject(error)
    }
   )


const BASEURL = 'http://jdall.nenu.edu.cn:8080'
const reqApi = axios.create({
    baseURL: BASEURL,
    // timeout:2000,
});

/*
*写对应模块的接口
*/

// 信息管理界面的新增，用表单上传URL
export const formUrl = (info) => {
    return reqApi.get("/info/insert", {
        params: info
    })
}
//信息管理界面的 用文件上传url
export const uploadUrl = (info) => {
    return reqApi.get("/info/insert", {
        params: info
    })
}
//信息管理的信息修改界面 获取所有信息
export const allInfo = () => {
    return reqApi.get("/info/multiSelect")
}
//信息管理的信息修改的界面 修改信息
export const updateInfo = (info) => {
    return reqApi.get("/info/updateByPrimaryKeySelective", {
        params: info
    })
}
//信息管理的信息修改的界面 删除信息
export const deleteInfo = (id) => {
    return reqApi.get("/info/deleteByPrimaryKey", {
        params: id
    })
}
//信息管理的信息修改的界面 搜索信息
export const searchInfo = (tag) => {
    return reqApi.get("/info/multiSelect", {
        params: tag
    })
}
//轮播图管理界面 获取轮播列表
export function getAll() {
    return reqApi({
        url: '/pic/getAll',
        method: 'post',
    });
}

// 轮播图管理界面 删除的接口
export function deleteA(query) {
    return reqApi({
        url: '/pic/delete',
        method: 'post',
        params: query,
    });
}

//轮播图管理界面 编辑文件的接口
export function edite(query) {
    return reqApi({
        url: '/pic/update',
        method: 'post',
        // headers: {"Content-Type": "multipart/form-data"},
        params: query,
    });
}
//审核url界面 获取url
export const fetchData = () => {
    return reqApi.get("/admin/showUrl")
}

//审核url界面 删除URL
export const deleData = (data) => {
    return reqApi.request({
        url: '/admin/deleteUrl?ids=' + data.ids,
        method: 'get'
    });
}
//审核url界面 已阅
export const readed = (data) => {
    return reqApi.request({
        url: '/admin/updateState?ids=' + data.ids,
        method: 'get'
    })
}

//登录页面
export const Login = (info) => {
    return reqApi.get("/login", {
        params: info
    })

}