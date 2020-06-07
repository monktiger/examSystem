// pages/editPaper/editPaper.js
var app = getApp();
Page({

  data: {
    show: false,
    style: false
  },

  single(e) {
    let that = this
    wx.navigateTo({
      url: "/pages/editSingle/editSingle",
      success: function () {
        that.setData({
          show: false
        })
      }
    })

  },

  multi(e) {
    let that = this
    wx.navigateTo({
      url: "/pages/editMulti/editMulti",
      success: function () {
        that.setData({
          show: false
        })
      }
    })
  },

  short(e) {
    let that = this
    wx.navigateTo({
      url: "/pages/editShort/editShort",
      success: function () {
        that.setData({
          show: false
        })
      }
    })
  },

  judge(e) {
    let that = this
    wx.navigateTo({
      url: "/pages/editJudge/editJudge",
      success: function () {
        that.setData({
          show: false
        })
      }
    })
  },

  fill(e) {
    let that = this
    wx.navigateTo({
      url: "/pages/editFill/editFill",
      success: function () {
        that.setData({
          show: false
        })
      }
    })
  },

  toStore(e) {
    let that = this
    wx.navigateTo({
      url: "/pages/questionStorage/questionStorage",
      success: function () {
        that.setData({
          show: false
        })
      }
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
  back() {
    console.log("ddd");
    
    wx.navigateBack({
      delta: 2
    });
  },
  onLoad: function (options) {

  },
  onShow: function () {
    app.pageScrollToBottom("#view"); // 默认停留在页面底部 方便添加题目
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
      key: 'judge_ques',
      success(res) {
        console.log(res)
      }
    })
    wx.removeStorage({
      key: 'fill_ques',
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
    wx.navigateBack({
      delta: 2
      // url: "../manageGroup/manageGroup"
    })
    wx.showToast({
      title: '创建成功！', // 标题
      icon: 'success', // 图标类型，默认success
      duration: 1500 // 提示窗停留时间，默认1500ms
    });
    app.globalData.examName = "";
    app.globalData.startDate = "";
    app.globalData.endDate = "";
  },

  // 修改试卷信息
  setPaperMsg: function (e) {
    // wx.redirectTo({
    //   url: "../paperCreate/paperCreate"
    // })
    wx.navigateBack({
      delta: 1
    })
  },
  // showModal(e) {
  //   this.setData({
  //     modalName: e.currentTarget.dataset.target
  //   })
  // },
  // hideModal(e) {
  //   this.setData({
  //     modalName: null
  //   })
  // },
})