// pages/editName/editName.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  onLoad: function (options) {
    var editNameUrl = app.globalData.url + "user/nodify";
    this.setData({
      token:app.globalData.token,
      editNameUrl: editNameUrl,
      name: app.globalData.name
    });
  },

  // 获取姓名
  name: function (e) {
    this.setData({
      name: e.detail.value,
    });
    console.log("姓名" + this.data.name);
  },

  confirm(e) {
    var that = this;
    var name = that.data.name;
    // 发起网络请求
    wx.request({
      url: that.data.editNameUrl,
      method: "post",
      header: {
        "token": app.globalData.token,
        "Content-Type": "application/json",
      },
      data: {
        name: that.data.name
      },
      success: function (res) {
        if (res.data.status == 1) {
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
        } else {
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