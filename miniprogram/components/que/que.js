// components/que/que.js
var app = getApp();
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    // title:{
    //   type:String
    // }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    getTitle:function(e){
      var title=e.detail.value;
      wx.setStorageSync("title",title);
    },
  },
  lifetimes: {
    ready: function (e) {
      var title=wx.getStorageSync("title");
      this.setData({
        title:title
      })
    }
  },
})
