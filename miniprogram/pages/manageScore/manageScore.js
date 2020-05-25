// pages/manageScore/manageScore.js
var app = getApp();
Page({

  data: {

  },

  onLoad: function (options) {
    var scoreListUrl = app.globalData.url + "in/toScoreList";
    this.setData({
      scoreListUrl: scoreListUrl,
    });
    this.getScoreList(scoreListUrl);
  },

  getScoreList: function(url){
    var that = this;
    // 发起网络请求
    wx.request({
      url: url,
      method: "get",
      header: {
        "content-type": ""
      },
      data: {
        examId: "10086", // ***examId
        groupId: "ASX123" // ***组Id
      },
      success: function (res) {
        that.processScore(res.scoreList);
      },
      fail: function (error) {
        console.log(error);
      }
    })
  },

  processScore: function(scoreList){
    var scores = [];
    for (var idx in scoreList) {
      var subject = scoreList[idx];
      var studentName = subject.studentName;
      if (studentName.length >= 6) {
        studentName = studentName.substring(0, 6) + "...";
      }
      var temp = {
        studentName: studentName,
        score: subject.score,
      }
      scores.push(temp);
    }
    this.setData({
      scores: scores
    });
  }
})