// pages/paperDetails/paperDetails.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    btnName: '',
    status: '20002',
    sum: 0,
    examId: app.globalData.examId
  },
  // 去试卷界面
  goPaper(e) {
    app.globalData.copyId = this.data.paperDetails.copyId;
    app.globalData.questionList = this.data.paperDetails.questionList;
    wx.navigateTo({
      url: '/pages/paper/paper'
    })
  },
  // 去添加试题界面
  goPaperCreate(e) {
    wx.navigateTo({
      url: '/pages/paperCreate/paperCreate',
    });
  },
  formatDate(now) {
    var year = now.getFullYear();  //取得4位数的年份
    var month = now.getMonth() + 1;  //取得日期中的月份，其中0表示1月，11表示12月
    var date = now.getDate();      //返回日期月份中的天数（1到31）
    var hour = now.getHours();     //返回日期中的小时数（0到23）
    var minute = now.getMinutes(); //返回日期中的分钟数（0到59）
    var second = now.getSeconds(); //返回日期中的秒数（0到59）
    if (month < '10') month = '0' + month;
    if (date < '10') date = '0' + date;
    if (hour < '10') hour = '0' + hour;
    if (minute < '10') minute = '0' + minute;
    if (second < '10') second = '0' + second;
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    let status = app.globalData.stuStatus
    console.log(status);
    if (status == '20002') {
      wx.request({
        url: 'http://monktiger.natapp1.cc/in/toCopy',
        method: 'GET',
        data: {
          examId: app.globalData.examId
        },
        header: {
          "token": app.globalData.token
        },
        success: function (result) {
          console.log(result.data);
          let paperDetails = result.data;
          let start = new Date(paperDetails.beginTime);
          let end = new Date(paperDetails.endTime);
          paperDetails.beginTime = that.formatDate(start);
          paperDetails.endTime = that.formatDate(end);
          let score = 0;
          let i;
          for (i = 0; i < paperDetails.questionList.length; i++) {
            score = score + paperDetails.questionList[i].score;
          }
          if (paperDetails.questionList) {
            that.setData({
              score: score,
              status: status,
              paperDetails: paperDetails,
              btnName: '开始答题',
              length: paperDetails.questionList.length
            })
          }
        }, fail(e) {
          console.log(e);
        }
      })
    } else if (status == '20003') {
      wx.request({
        url: 'http://monktiger.natapp1.cc/in/toWrongBook',
        method: 'GET',
        data: {
          examId: app.globalData.examId
        },
        header: {
          "token": app.globalData.token
        },
        success: function (result) {
          console.log(result);
          let paperDetails = result.data;
          let i;
          let sum = 0;
          let score = 0;
          for (i = 0; i < paperDetails.questionList.length; i++) {
            sum = sum + paperDetails.questionList[i].getScore;
            score = score + paperDetails.questionList[i].score;
          }
          that.setData({
            status: status,
            paperDetails: paperDetails,
            btnName: '查看试卷',
            length: paperDetails.questionList.length,
            judge: paperDetails.judge,
            sum: sum,
            score: score
          })
        }, fail(e) {
          console.log(e);
        }
      })
      that.setData({
        status: status,
        btnName: '查看试卷'
      })
    } else if (status == '10004') {
      wx.request({
        url: 'http://monktiger.natapp1.cc/in/toWrongBook',
        method: 'GET',
        data: {
          // copyId: app.globalData.copyId,
          copyId: '452'
        },
        header: {
          "token": app.globalData.token
        },
        success: function (result) {
          console.log(result);
          let paperDetails = result.data;
          let i;
          let sum = 0;
          let score = 0;
          let btnName = '查看试卷';
          let isScore = true;
          let isJudge = true;
          console.log(paperDetails);
          // 判断是否需要进行评论
          if (paperDetails.judge == null) {
            isJudge = false
          }
          for (i = 0; i < paperDetails.questionList.length; i++) {
            // 试卷总分
            score = score + paperDetails.questionList[i].score;
            // 判断是否需要进行批改
            if (paperDetails.questionList[i].getScore == null) {
              console.log(paperDetails.questionList[i].getScore);

              btnName = '批改试卷';
              isScore = false
            } else {
              console.log(paperDetails.questionList[i].getScore);

              // 学生得分
              sum = sum + paperDetails.questionList[i].getScore;
            }
          }
          app.globalData.isJudge = isJudge;
          app.globalData.isScore = isScore;
          that.setData({
            isJudge:isJudge,
            isScore: isScore,// 是否需要进行批改
            status: status,
            paperDetails: paperDetails,
            btnName: btnName,
            length: paperDetails.questionList.length,
            judge: paperDetails.judge,
            sum: sum,
            score: score
          })
        }, fail(e) {
          console.log(e);
        }
      })
      that.setData({
        status: status,
        btnName: '查看试卷'
      })
    } else if (status == '10003' || status == "10002" || status == "10001") {
      wx.request({
        url: 'http://monktiger.natapp1.cc/in/toAdminShow',
        method: 'GET',
        data: {
          examId: app.globalData.examId
        },
        header: {
          "token": app.globalData.token
        },
        success: function (result) {
          console.log(result);
          let paperDetails = result.data;
          let i;
          let score = 0;
          let btnName;
          // 算卷子总分
          for (i = 0; i < paperDetails.questionList.length; i++) {
            score = score + paperDetails.questionList[i].score;
          }
          paperDetails.beginTime = app.globalData.beginTime;
          paperDetails.endTime = app.globalData.endTime;
          if (status == "10001") {
            btnName = '编辑试卷'
          } else {
            btnName = '查看试卷'
          }
          that.setData({
            // 总分
            score: score,
            // 状态码
            status: status,
            // 试卷的题
            paperDetails: paperDetails,
            // 按钮的字
            btnName: btnName,
            // 卷子多少个题
            length: paperDetails.questionList.length,
          })
        }, fail(e) {
          console.log(e);
        }
      })
      that.setData({
        status: status,
        btnName: '查看试卷'
      })
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      isScore: true
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})