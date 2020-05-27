// pages/manageScore/manageScore.js
var app = getApp();
Page({

  data: {
  },

  onLoad: function (options) {
    var scoreListUrl = app.globalData.url + "in/toScoreList";
    this.setData({
      scoreListUrl: scoreListUrl,
      groupId: app.globalData.group_id,
      examId: app.globalData.exam_id
    });
    this.getScoreList(scoreListUrl);
  },

  // 获得成绩列表
  getScoreList: function(url){
    var that = this;
    // 发起网络请求
    wx.request({
      url: url,
      method: "get",
      header: {
        "token": that.data.token
      },
      data: {
        examId: that.data.examId,
        groupId: that.data.groupId
      },
      success: function (res) {
        that.processScore(res.scoreList);
      },
      fail: function (error) {
        console.log(error);
      }
    })
  },

  // 展示成绩列表
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
  },
  
  // 查看详情
  detail:function(e){
    wx.navigateTo({
      url: '/pages/paperDetail/paperDetail',
    })
  }
})