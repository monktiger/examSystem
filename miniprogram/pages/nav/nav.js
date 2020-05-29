var app = getApp();
Page({
  data: {
    PageCur: 'chat'
  },
  NavChange(e) {
    this.setData({
      PageCur: e.currentTarget.dataset.cur
    })
  },
  onLoad: function (options) {
    // this.setData({
    //   name:app.globalData.name,
    //   avatarUrl:app.globalData.avatarUrl,
    // })
    // console.log("name:",app.globalData.name)
    // console.log("onloadNav")
  },
})