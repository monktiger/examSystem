// pages/my/my.js
var app = getApp();
Component({
  data: {
    
  },
  methods: {
    attached: function (e) {
      console.log("Component",this.data)
    },
    info(e){
      wx.navigateTo({
        url: "/pages/myInfo/myInfo"
      })
    },
  },
  lifetimes: {
    attached: function (e) {
      this.setData({
        name:app.globalData.name,
        avatarUrl:app.globalData.avatarUrl
      });
    }
  },
  pageLifetimes: {
    show: function() {
      // 页面被展示
      this.setData({
        name:app.globalData.name,
        avatarUrl:app.globalData.avatarUrl
      });
    },
  }
})
