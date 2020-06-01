// pages/manageGroup/manageGroup.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    paperList:[]
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
    wx.navigateTo({
      url: '/pages/manageScore/manageScore'
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
        status:app.globalData.pageId
      },
      header: {
        "token": app.globalData.token
      },
      success: function (result) {
       
        that.setData({
          paperList: result.data.examList,   
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