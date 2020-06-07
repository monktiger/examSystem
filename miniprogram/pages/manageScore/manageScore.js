// pages/manageScore/manageScore.js
var app = getApp();
Page({

  data: {
  },

  onLoad: function (options) {
    var scoreListUrl = app.globalData.url + "in/toScoreList";
    this.setData({
      scoreListUrl: scoreListUrl
    });
    this.getScoreList(scoreListUrl);
  },

  // 获得成绩列表
  getScoreList: function (url) {
    wx.showLoading({
      title: '加载中...',
    })
    var that = this;
    // 发起网络请求
    wx.request({
      url: url,
      method: "get",
      header: {
        "token": app.globalData.token
      },
      data: {
        examId: app.globalData.examId,
        groupId: app.globalData.groupId,
      },
      success: function (res) {
        wx.hideLoading();
        console.log("getScoreList", res);
        // that.processScore(res.scoreList);
        that.setData({
          scoreList: res.data.scoreList
        })
        console.log(res.data.scoreList)
      },
      fail: function (error) {
        console.log(error);
      }
    })
  },

  // // 展示成绩列表
  // processScore: function(scoreList){
  //   var scores = [];
  //   for (var idx in scoreList) {
  //     var subject = scoreList[idx];
  //     var studentName = subject.studentName;
  //     if (studentName.length >= 6) {
  //       studentName = studentName.substring(0, 6) + "...";
  //     }
  //     var temp = {
  //       studentName: studentName,
  //       score: subject.score,
  //     }
  //     scores.push(temp);
  //   }
  //   this.setData({
  //     scores: scores
  //   });
  // },

  // 查看详情
  detail: function (e) {
    app.globalData.copyId=e.currentTarget.dataset.copyid
      wx.navigateTo({
        url:'/pages/paperDetails/paperDetails'
      })
  }
})