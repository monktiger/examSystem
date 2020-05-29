// pages/joinedGroup/joinedGroup.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    paperList:[]
  },
  goPaperDetails(e){
    wx.navigateTo({
      url:'/pages/paperDetails/paperDetails'
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
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
        console.log(result);
        let paperList = result.data.examList;
        let i;
        // console.log(elements.length);
        if (paperList) {
          for (i = 0; i < paperList.length; i++) {
            paperList[i].beginTime=new Date(paperList[i].beginTime);
            paperList[i].endTime=new Date(paperList[i].endTime);
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
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

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