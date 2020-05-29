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
})
