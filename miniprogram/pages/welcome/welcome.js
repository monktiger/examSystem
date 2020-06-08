// pages/welcome/welcome.js
const app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onGotUserInfo(e) {
    wx.showLoading({
      title: '加载中...',
    })
    let that = this
    console.log("DDD");

    // console.log(e.detail.errMsg);
    // console.log(e.detail.userInfo.nickName,e.detail.userInfo.gender,e.detail.userInfo.avatarUrl);
    // console.log(e.detail.rawData);
    // 登录
    wx.login({
      success: function (res) {
        console.log(res);
        // 获取登录的临时凭证
        var code = res.code;
        // 调用后端获取session_key,secret
        wx.request({
          url: 'http://monktiger.natapp1.cc/user/login',
          method: 'POST',
          data: {
            code: code,
            userName: e.detail.userInfo.nickName,
            headPic: e.detail.userInfo.avatarUrl
          },
          header: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          success: function (result) {
            wx.hideLoading();
            // 保存openid
            console.log(result);
            app.globalData.token = result.data.token;
            app.globalData.nickname = result.data.userInfo.nickname;
            app.globalData.name = result.data.userInfo.name;
            app.globalData.avatarUrl = result.data.userInfo.avatarurl;
            wx.navigateTo({
              url: '/pages/nav/nav'
              // url: '/pages/questionStorage/questionStorage'
            })        
          }, fail(e) {
            console.log(e);

          }
        })
      }
    })

  },
  onLoad: function (options) {
    // setTimeout(function () {
    //   wx.navigateTo({
    //     url: '/pages/nav/nav',
    //   })
    // }, 3000)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})