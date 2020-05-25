// pages/myInfo/myInfo.js
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

  }
})