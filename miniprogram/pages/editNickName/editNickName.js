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
      editNickNameUrl: editNickNameUrl,
    });
  },

  // 获取昵称
  nickname: function(e){
    this.setData({
      nickname: e.detail.value,
    });
    console.log("昵称" + this.data.nickname);
  },

  confirm: function (e) {
    var that = this;
    // 发起网络请求
    wx.request({
      url: that.data.editNickNameUrl,
      method: "post",
      header: {
        "content-type": ""
      },
      data:{
        nickname: that.data.nickname
      },
      success: function (res) {
        // 弹窗成功
        wx.showToast({
          title: '修改成功！', // 标题
          icon: 'success',  // 图标类型，默认success
          duration: 1500  // 提示窗停留时间，默认1500ms
        })
        // 跳转
        wx.navigateTo({
          url: '/pages/myInfo/myInfo',
        })
      },
      fail: function (error) {
        console.log(error);
      }
    })
  }

})