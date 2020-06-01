// pages/editPaper/editPaper.js
var app = getApp();
Page({

  data: {
    show: false,
    style: false
  },

  single(e) {
    wx.redirectTo({
      url: "/pages/editSingle/editSingle"
    })
  },

  multi(e) {
    wx.redirectTo({
      url: "/pages/editMulti/editMulti"
    })
  },

  short(e) {
    wx.redirectTo({
      url: "/pages/editShort/editShort"
    })
  },

  showType(e) {
    this.setData({
      show: true
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
      examName: app.globalData.examName,
      beginTime: app.globalData.beginTime,
      endTime: app.globalData.endTime,
    });
  },

  // 创建试卷
  create: function (e) {
    wx.removeStorage({
      key: 'single_ques',
      success(res) {
        console.log(res)
      }
    })
    wx.removeStorage({
      key: 'multi_ques',
      success(res) {
        console.log(res)
      }
    })
    wx.removeStorage({
      key: 'short_ques',
      success(res) {
        console.log(res)
      }
    })
    wx.redirectTo({
      url: "../manageGroup/manageGroup"
    })
    wx.showToast({
      title: '创建成功！', // 标题
      icon: 'success', // 图标类型，默认success
      duration: 1500 // 提示窗停留时间，默认1500ms
    });
    app.globalData.startDate="";
    app.globalData.endDate="";
  },

  // 修改试卷信息
  setPaperMsg: function (e) {
    wx.navigateTo({
      url: "../paperCreate/paperCreate"
    })
  }
})