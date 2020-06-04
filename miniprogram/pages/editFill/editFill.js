// pages/editFill/editFill.js
const _UTIL = require("../../utils/util.js");
var app = getApp();
Page({
  data: {
    index: null,
    picker: ['1', '2', '3', '4', '5'],
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
      type: 3,
      current: current,
      questionId: this.data.queId||"",
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
            data.id = res.data.id;
            data.type = res.data.type;
            // 设置题目缓存
            var fillQues = wx.getStorageSync('fill_ques');
            var arr = []
            for (let i in fillQues) {
              let o = {};
              o[i] = fillQues[i];
              arr.push(o[i])
            }
            // 如果是修改，则先把storage对应的题删掉 再重新push
            console.log("editQueNum", app.globalData.editQueNum)
            if (app.globalData.editQueNum) {
              var editQueNum = that.data.editQueNum;
              _UTIL.arrRemoveObj(arr, arr[editQueNum]);
            }
            arr.push({
              data: data,
            })
            wx.removeStorage({
              key: 'fill_ques',
              success(res) {
                console.log(res)
              }
            })
            console.log("arr:", arr);
            wx.setStorageSync('fill_ques', arr);
            wx.removeStorage({
              key: 'title',
              success(res) {
                console.log(res)
              }
            })
            if (app.globalData.isEdit) {
              wx.redirectTo({
                url: "../paper/paper"
              })
            } else {
              wx.redirectTo({
                url: "../editPaper/editPaper"
              })
            }
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
    // 如果有editQueNum则是修改题目：先取出原数据，赋值
    var editQueNum = app.globalData.editQueNum;
    console.log("editQueNum",editQueNum)
    if (editQueNum) {
      var editques = wx.getStorageSync('fill_ques');
      console.log("editques",editques);
      var editData = editques[editQueNum].data;
      var score = editData.score;
      var queId = editData.queId;
      wx.setStorageSync("title", editData.title);
      this.setData({
        index: parseInt(score) - 1,
        current: editData.current,
        editQueNum: editQueNum,
        queId: queId
      })
      console.log("index", this.data.index)
    }
  }
})