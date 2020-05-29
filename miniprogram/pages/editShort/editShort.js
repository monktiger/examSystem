// pages/editShort/editShort.js
var app = getApp();
Page({
  data: {
    index: null,
    picker: ['1', '2', '3', '4', '5'],

    //样例
    short:{ 
      "title":"这是一道多选题", 
      "score":20, 
      "type":5, 
    },
  },

  PickerChange(e) {
    this.setData({
      index: e.detail.value
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
    var data = {
      title: title,
      score: score,
      type: 5,
      current: that.data.current,
      examId: "",
      questionId: "",
    }
    // 发起网络请求
    wx.request({
      url: that.data.addQuestionUrl,
      method: "post",
      header: {
        "token": app.globalData.token,
        "Content-Type": "application/json",
      },
      data: JSON.stringify(data),
      success: function (res) {
        console.log("res:",res);
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
            success (res) {
              console.log(res)
            }
          })
          console.log("arr:",arr);
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
  },
  onLoad: function (options) {
    var addQuestionUrl = app.globalData.url + "exam/addQuestion";
    this.setData({
      addQuestionUrl: addQuestionUrl,
    });

  }
})