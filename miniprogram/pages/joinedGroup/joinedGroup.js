// pages/joinedGroup/joinedGroup.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    paperList: [],
  },
  goPaperDetails(e) {

    let that = this;
    wx.request({
      url: 'http://monktiger.natapp1.cc/exam/inExam',
      method: 'GET',
      data: {
        examId: e.detail.examId
      },
      header: {
        "token": app.globalData.token
      },
      success: function (result) {

        console.log(result.data.status);
        let status = result.data.status
        // let status = '20004';
        if (status == '20001') {
          // 试卷还未开放，请耐心等待
          that.setData({
            modalName: 'tipsModal',
          })
        } else if (status == '20002') {
          // 可以作答，跳转到作答页
          app.globalData.stuStatus = '20002'
          app.globalData.examId = e.detail.examId
          wx.navigateTo({
            url: '/pages/paperDetails/paperDetails'
          })
        }
        else if (status == '20003') {
          // 考试已经结束，可以查看成绩，跳转到错题查看页
          app.globalData.stuStatus = '20003'
          app.globalData.examId = e.detail.examId
          wx.navigateTo({
            url: '/pages/paperDetails/paperDetails'
          })
        }
        else if (status == '20004') {
          // 考试已经结束，等待老师批改
          that.setData({
            modalName: 'tipsMarkModal',
          })
        }
      }, fail(e) {
        console.log(e);

      }
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
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
  quit(e) {
let that = this
    wx.request({
      url: 'http://monktiger.natapp1.cc/group/leave',
      method: 'GET',
      data: {
        groupId: e.detail.groupid,
      },
      header: {
        "token": app.globalData.token
      },
      success: function (result) {
        console.log(result);
        that.getExam()
      }, fail(e) {
        console.log(e);

      }
    })

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  getExam(e) {
    let that=this
    wx.request({
      url: 'http://monktiger.natapp1.cc/exam/getExam',
      method: 'GET',
      data: {
        groupId: app.globalData.groupId,
        status: 0
      },
      header: {
        "token": app.globalData.token
      },
      success: function (result) {
        console.log(result);
        let paperList = result.data.examList;
        let i;
        // console.log(elements.length);
        if (paperList && paperList.length != 0) {
          for (i = 0; i < paperList.length; i++) {
            let beginTime = new Date(paperList[i].beginTime);
            paperList[i].beginTime = that.formatDate(beginTime);
            let endTime = new Date(paperList[i].endTime);
            paperList[i].endTime = that.formatDate(endTime);
          }
          that.setData({
            paperList: paperList,
          })
        }

      }, fail(e) {
        console.log(e);

      }
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    let that = this
    this.getExam()
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