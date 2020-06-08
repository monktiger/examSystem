/**
 * token还没有完成
 */

import cookie from "cookie_js"
// const adminToKen = "admin_toKen"
const loginStatus = "loginStatus"
// export function getToken() {
//     return cookie.get(adminToKen);
// }
// export function setToken(toKen) {
//     return cookie.set(adminToKen, toKen)
// }
// export function removeToken() {
//     return cookie.remove(adminToKen)
// }getLoginStatus
export function setLoginStatus(value) {
    return cookie.set(loginStatus, value)
}
export function getLoginStatus(){
    return cookie.get(loginStatus)
}
export function removeLoginStatus(){
    return cookie.remove(loginStatus)
}