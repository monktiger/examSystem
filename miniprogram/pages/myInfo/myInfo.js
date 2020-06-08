// pages/myInfo/myInfo.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  nickname(e){
    wx.navigateTo({
      url: "/pages/editNickName/editNickName"
    })
  },

  name(e) {
    wx.navigateTo({
      url: "/pages/editName/editName"
    })
  },

  onLoad: function (options) {
    this.setData({
      name:app.globalData.name,
      nickname:app.globalData.nickname
    })
    console.log('name',this.data.name,this.data.nickname)
  }
})