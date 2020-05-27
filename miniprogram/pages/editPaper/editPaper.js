// pages/editPaper/editPaper.js
var app = getApp();
Page({

  data: {
    show:false,
    style:false
  },

  single(e){
    wx.navigateTo({       
      url: "/pages/editSingle/editSingle"
    })
  },

  multi(e) {
    wx.navigateTo({
      url: "/pages/editMulti/editMulti"
    })
  },

  short(e) {
    wx.navigateTo({
      url: "/pages/editShort/editShort"
    })
  },

  showType(e){
    this.setData({
      show:true
    })
  },

  hideType(e) {
    this.setData({
      show: false
    })
  },

  onLoad: function (options) {
    var addQuestionUrl = app.globalData.url + "exam/addQuestion";
    this.setData({
      addQuestionUrl: addQuestionUrl,
    });
    // 获取简答题缓存
    var shortQues = wx.getStorageSync('short_ques');
    this.setData({
      shortQues:shortQues
    })
    for(let i in shortQues){

    }
    // if(shortQues){
      // 如果有缓存，则放进data内

      console.log("editPaper",shortQues);
    // } 
    // else {
    //   //没有缓存则定义缓存数组？
    //   shortQues=[];
    //   wx.setStorageSync('short_ques', shortQues);
    // }
    
    // wx.removeStorage({
    //   key: 'short_ques',
    //   success (res) {
    //     console.log(res)
    //   }
    // })
  },

  create:function(e){
    wx.navigateTo({
      url: "/pages/manageGroup/manageGroup"
    })
    wx.showToast({
      title: '创建成功！', // 标题
      icon: 'success', // 图标类型，默认success
      duration: 1500 // 提示窗停留时间，默认1500ms
    })

  }
})