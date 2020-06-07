// pages/manageGroup/manageGroup.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    paperList: []
  },
  back: function (e) {
    wx.navigateBack({
      delta: 1
    })
    app.globalData.examName="";
  },
  // 去管理人员界面
  goManagePeople(e) {
    wx.navigateTo({
      url: '/pages/managePeople/managePeople',
    });
  },
  // 去添加试题界面
  goPaperCreate(e) {
    wx.navigateTo({
      url: '/pages/paperCreate/paperCreate',
    });
  },
  // 去设置界面
  goSetting(e) {
    wx.navigateTo({
      url: '/pages/groupSetting/groupSetting',
    });
  },
  // 去管理成绩页面
  goManageScore(e) {
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
        // let status = '10001'
        if (status == '10001') {
          // 跳转到试卷编辑页
          app.globalData.stuStatus = '10001'
          app.globalData.beginTime = e.detail.beginTime,
            app.globalData.endTime = e.detail.endTime,
            app.globalData.examId = e.detail.examId
          wx.navigateTo({
            url: '/pages/paperDetails/paperDetails'
          })
        } else if (status == '10002') {
          // 考试即将开始，跳转到试卷查看页
          app.globalData.stuStatus = '10002'
          app.globalData.beginTime = e.detail.beginTime,
            app.globalData.endTime = e.detail.endTime,
            app.globalData.examId = e.detail.examId
          wx.navigateTo({
            url: '/pages/paperDetails/paperDetails'
          })
        }
        else if (status == '10003') {
          // 考试已经开始，跳转到试卷查看页
          console.log(e.detail.beginTime);

          app.globalData.stuStatus = '10003',
            app.globalData.beginTime = e.detail.beginTime,
            app.globalData.endTime = e.detail.endTime,
            app.globalData.examId = e.detail.examId
          wx.navigateTo({
            url: '/pages/paperDetails/paperDetails'
          })
        }
        else if (status == '10004') {
          // 跳转到成绩排行页
          app.globalData.stuStatus = '10004'
          app.globalData.examId = e.detail.examId
          wx.navigateTo({
            url: '/pages/manageScore/manageScore'
          })
        }
      }, fail(e) {
        console.log(e);

      }
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
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      groupname: app.globalData.groupname
    })
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
    wx.showLoading({
      title: '加载中...',
    })
    let that = this
    wx.request({
      url: 'http://monktiger.natapp1.cc/exam/getExam',
      method: 'GET',
      data: {
        groupId: app.globalData.groupId,
        status: app.globalData.pageId
      },
      header: {
        "token": app.globalData.token
      },
      success: function (result) {
        wx.hideLoading();
        console.log(result);
        let paperList = result.data.examList;
        let i;
        // console.log(elements.length);
        if (paperList) {
          for (i = 0; i < paperList.length; i++) {
            let beginTime = new Date(paperList[i].beginTime);
            paperList[i].beginTime = that.formatDate(beginTime);
            let endTime = new Date(paperList[i].endTime);
            paperList[i].endTime = that.formatDate(endTime);
          }
        }
        that.setData({
          paperList: paperList.reverse(),
        })
      }, fail(e) {
        console.log(e);

      }
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