// import request from '../utils/request';

import axios from "axios"
import qs from 'qs'


const BASEURL = 'http://monktiger.natapp1.cc'
const reqApi = axios.create({
    baseURL: BASEURL,
});


export const Login = (data) => {
    console.log(data);
    return reqApi({
        url: '/admin/login',
        method: 'post',
        data: qs.stringify(data),
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        }
    });
};
//获得组
export function getGroup(query) {
    return reqApi.get("/admin/group", {
        params: query
    })
}

// 删除得组
export function deleteGroup(query) {
    return reqApi({
        url: '/admin/group/' + query,
        method: 'delete',
    });
}

//编辑组
export function editeGroup(data) {
    let newData = {
        name: data.name
    }
    console.log(newData);
    console.log(qs.stringify(newData));

    return reqApi({
        url: '/admin/group/' + data.groupid,
        method: 'patch',
        data: qs.stringify(newData),
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        }
    });
}

//获取试卷
export function getExam(query) {
    console.log(query);
    return reqApi.get("/admin/exam", {
        params: query
    })
}
//编辑试卷
export function editeExam(data) {
    console.log(data);
    let newData = {
        name: data.examName
    }
    return reqApi({
        url: '/admin/exam/' + data.examId,
        method: 'patch',
        data: JSON.stringify(newData),
        headers: {
            'token': JSON.parse(sessionStorage.getItem('token')),
            "Content-Type": "application/json"
        }
    });
}

//删除试卷
export function deleteExam(query) {
    console.log(query);

    return reqApi({
        url: '/admin/exam/' + query,
        method: 'delete',
        headers: {
            'token': JSON.parse(sessionStorage.getItem('token')),
        }
    });
}

//获取学生
export function getUser(query) {
    console.log(query);
    return reqApi.get("/admin/user", {
        params: query
    })
}

//修改学生资料
export const editeUser = (data) => {
    let newData = {
        name: data.name
    }
    return reqApi({
        url: '/admin/user/' + data.openId,
        method: 'patch',
        data: qs.stringify(newData),
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        }
    });
}

//禁用学生
export function deleteUser(query) {
    return reqApi({
        url: '/admin/user/' + query,
        method: 'delete',
        // headers: {"Content-Type": "multipart/form-data"},
        params: query,
    });
}
//获取试题
export function getQuestion(query) {
    console.log(query);
    return reqApi.get("/admin/question", {
        params: query
    })
}

//修改试题
export function editeQuestion(data) {
    console.log(data);
    let newData = {
        title: data.title,
        type: data.type,
        category: data.category,
        current: data.current,
        answerA: data.answerA,
        answerB: data.answerB,
        answerC: data.answerC,
        answerD: data.answerD,
    }
    return reqApi({
        url: '/admin/question/' + data.id,
        method: 'patch',
        data: JSON.stringify(newData),
        headers: {
            'token': JSON.parse(sessionStorage.getItem('token')),
            "Content-Type": "application/json"
        }
    });
}

//删除试题
export function deleteQuestion(query) {
    return reqApi({
        url: '/admin/question/' + query,
        method: 'delete',
        // headers: {"Content-Type": "multipart/form-data"},
    });
}

    //上传试题
    export function uploadQuestion(data) {
        console.log(JSON.stringify(data));
        return reqApi({
            url: '/admin/question',
            method: 'post',
            data: JSON.stringify(data),
            headers: {
                'token': JSON.parse(sessionStorage.getItem('token')),
                "Content-Type": "application/json"
            }
        });
    }
