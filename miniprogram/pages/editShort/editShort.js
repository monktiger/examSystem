// pages/editShort/editShort.js
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
    // wx.redirectTo({
    //   url: "/pages/editPaper/editPaper"
    // })
    wx.navigateBack({
      delta:1
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
    wx.showLoading({
      title: '加载中...',
    })
    console.log("queId", this.data.queId);
    var that = this;
    var title = wx.getStorageSync("title");
    var score = parseInt(this.data.index) + 1;
    var current = that.data.current;
    var data = {
      title: title,
      score: score,
      type: 5,
      current: current,
      id: this.data.queId || "",
      examId: this.data.examId,
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
            wx.hideLoading();
            data.id = res.data.id;
            data.type = res.data.type;
            // 设置题目缓存
            var shortQues = wx.getStorageSync('short_ques');
            var arr = []
            for (let i in shortQues) {
              let o = {};
              o[i] = shortQues[i];
              arr.push(o[i])
            }
            // 如果是修改，则先把storage对应的题删掉 再重新push
            console.log("editQueNumRequest", app.globalData.editQueNum)
            if (app.globalData.editQueNum || app.globalData.editQueNum == 0) {
              console.log("delete");
              var editQueNum = that.data.editQueNum;
              _UTIL.arrRemoveObj(arr, arr[editQueNum]);
            }
            arr.push({
              data: data
            })
            wx.removeStorage({
              key: 'short_ques',
              success(res) {
                console.log(res)
              }
            })
            console.log("arr:", arr);
            wx.setStorageSync('short_ques', arr);
            wx.setStorageSync('title', "");
            app.globalData.editQueNum = "";
            if (app.globalData.isEdit) {
              // wx.redirectTo({
              //   url: "../paper/paper"
              // })
              app.globalData.isAlreadyEdit=true;
              app.globalData.isEdit = 0
              wx.navigateBack({
                delta:1
            })
             
            } else {
              // wx.redirectTo({
              //   url: "../editPaper/editPaper"
              // })
              wx.navigateBack({
                delta:1
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
    console.log("editQueNum", editQueNum);
    if (editQueNum || editQueNum == 0) {
      var editques = wx.getStorageSync('short_ques');
      var editData = editques[editQueNum].data;
      console.log("editData", editData)
      var score = editData.score;
      var queId = editData.id;
      wx.setStorageSync("title", editData.title);
      this.setData({
        index: parseInt(score) - 1,
        current: editData.current,
        editQueNum: editQueNum,
        queId: queId,
        examId: editData.examId
      })
      console.log("index", this.data.index)
    }
  }
})