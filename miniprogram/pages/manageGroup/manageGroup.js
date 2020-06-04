// pages/manageGroup/manageGroup.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    paperList: []
  },
  goManagePeople(e) {
    wx.navigateTo({
      url: '/pages/managePeople/managePeople',
    });
  },
  goPaperCreate(e) {
    wx.navigateTo({
      url: '/pages/paperCreate/paperCreate',
    });
  },
  goSetting(e) {
    wx.navigateTo({
      url: '/pages/groupSetting/groupSetting',
    });
  },
  goManageScore(e) {
    app.globalData.examId=e.detail.examId
    wx.navigateTo({
      url: '/pages/manageScore/manageScore'
    })
  },
  formatDate(now) { 
    var year=now.getFullYear();  //取得4位数的年份
    var month=now.getMonth()+1;  //取得日期中的月份，其中0表示1月，11表示12月
    var date=now.getDate();      //返回日期月份中的天数（1到31）
    var hour=now.getHours();     //返回日期中的小时数（0到23）
    var minute=now.getMinutes(); //返回日期中的分钟数（0到59）
    var second=now.getSeconds(); //返回日期中的秒数（0到59）
    if(month<'10') month='0'+month;
    if(date<'10') date='0'+date;
    if(hour<'10') hour='0'+hour;
    if(minute<'10') minute='0'+minute;
    if(second<'10') second='0'+second;
    return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
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
          paperList: paperList,
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