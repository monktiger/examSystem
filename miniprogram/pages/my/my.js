// pages/my/my.js
var app = getApp();
Page({

  data: {

  },

  info(e){
    wx.navigateTo({
      url: "/pages/myInfo/myInfo"
    })
  },

  onLoad: function (options) {
    this.setData({
      name:app.globalData.name,
      avatarUrl:app.globalData.avatarUrl,
    })
  },

 
})
