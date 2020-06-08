// import request from '../utils/request';

import axios from "axios"
import qs from 'qs'


const BASEURL = '/api'
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
