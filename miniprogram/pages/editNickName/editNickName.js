// pages/editNickName/editNickName.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  onLoad: function (options) {
    var editNickNameUrl = app.globalData.url + "user/nodify";
    this.setData({
      token: app.globalData.token,
      editNickNameUrl: editNickNameUrl,
      nickname: app.globalData.nickname
    });
  },

  // 获取昵称
  nickname: function (e) {
    this.setData({
      nickname: e.detail.value,
    });
    console.log("昵称" + this.data.nickname);
  },

  confirm: function (e) {
    wx.showLoading({
      title: '加载中...',
    })
    var that = this;
    // 发起网络请求
    wx.request({
      url: that.data.editNickNameUrl,
      method: "get",
      header: {
        "token": app.globalData.token,
        "Content-Type": "application/json"
      },
      data: {
        nickname: that.data.nickname
      },
      success: function (res) {
        console.log("nickname", res)
        if (res.data.status == 1) {
          wx.hideLoading();
          app.globalData.nickname = that.data.nickname;
          // 跳转
          wx.redirectTo({
            url: '/pages/myInfo/myInfo',
          })
          // 弹窗成功
          wx.showToast({
            title: '修改成功！', // 标题
            icon: 'success',  // 图标类型，默认success
            duration: 1500  // 提示窗停留时间，默认1500ms
          })
        } else {
          // 弹窗失败
          wx.showToast({
            title: '修改失败！', // 标题
            icon: 'none',
            duration: 1500  // 提示窗停留时间，默认1500ms
          })
        }
      },
      fail: function (error) {
        console.log(error);
      }
    })
  }

})