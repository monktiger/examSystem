// pages/editShort/editShort.js
var app = getApp();
Page({
  data: {
    index: null,
    picker: ['1', '2', '3', '4', '5'],

    //样例
    short: {
      "title": "这是一道多选题",
      "score": 20,
      "type": 5,
    },
  },

  PickerChange(e) {
    this.setData({
      index: e.detail.value
    })
  },

  //返回
  back: function (e) {
    wx.redirectTo({
      url: "/pages/editPaper/editPaper"
    })
  },

  //获取答案
  getAns: function (e) {
    this.setData({
      current: e.detail.value,
    });
  },

  // 提交简答题
  confirm: function (e) {
    var that = this;
    var title = wx.getStorageSync("title");
    var score = parseInt(this.data.index) + 1;
    var current = that.data.current;
    var data = {
      title: title,
      score: score,
      type: 5,
      current: current,
      questionId: "",
      "examId": this.data.examId,
    }
    if (!title || !score || !current) {
      wx.showToast({
        title: '请填写所有题目信息！', // 标题
        icon: 'none', // 图标类型，默认success
        duration: 1500 // 提示窗停留时间，默认1500ms
      })
    } else {
      // 发起网络请求
      wx.request({
        url: that.data.addQuestionUrl,
        method: "post",
        header: {
          "token": app.globalData.token,
          "Content-Type": "application/json"
        },
        data: JSON.stringify(data),
        success: function (res) {
          console.log("res:", res);
          if (res.data.status == 1) {
            // 设置题目缓存
            var shortQues = wx.getStorageSync('short_ques');
            var arr = []
            for (let i in shortQues) {
              let o = {};
              o[i] = shortQues[i];
              arr.push(o[i])
            }
            arr.push({
              title: title,
              score: score,
            })
            wx.removeStorage({
              key: 'short_ques',
              success(res) {
                console.log(res)
              }
            })
            console.log("arr:", arr);
            wx.setStorageSync('short_ques', arr);
            wx.navigateTo({
              url: "../editPaper/editPaper"
            })
          } else {
            console.log("errorMsg:" + res.msg);
          }
        },
        fail: function (error) {
          console.log("errorMsg:" + error);
        }
      })
    }
  },
  onLoad: function (options) {
    var addQuestionUrl = app.globalData.url + "exam/addQuestion";
    this.setData({
      addQuestionUrl: addQuestionUrl,
      examId: app.globalData.examId
    });
  }
})