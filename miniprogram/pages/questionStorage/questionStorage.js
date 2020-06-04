// pages/questionStorage/questionStorage.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    typeIndex: 0,
    typeList: ['任意', '单选题', '多选题', '填空题', '判断题', '主观题'],
    selectType: false,
    selectMajor: false,
    pageNum: 1,
    search: null,
    category: null,
    type: 1,
    isEnd: false,
  },
  // type下拉列表
  typeChange(e) {
    this.setData({
      typeIndex: e.detail.value
    })
  },
  // 
  inputHd(e) {
    console.log(e.detail.value);

    this.setData({
      search: e.detail.value
    })
  },
  // 学科
  major(e) {
    console.log(e.detail.value);
    this.setData({
      category: e.detail.value
    })
  },
  // 关键字
  key(e) {
    console.log(e.detail.value);

    this.setData({
      search: e.detail.value
    })
  },
  // 搜索
  toSearch(e) {
    let that = this
    let data = {};
    if (that.data.search != null || that.data.search == '') {
      data.search = that.data.search;
    }
    if (that.data.category != null) {
      data.category = that.data.category;
    }
    if (that.data.typeIndex != 0) {
      data.type = that.data.typeIndex
    }
    data.pageNum = 1
    console.log(data);
    // 获得搜索列表
    that.getQuestionList(data);
  },
  // 模态窗
  showSearchModal(e) {
    this.setData({
      modalName: e.currentTarget.dataset.target,
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  toCopy(e) {
    let question = e.detail.question
    app.globalData.question = question;
    wx.wx.navigateBack({
      delta: 1
    });
  },
  // 获得列表
  getQuestionList(data) {
    let that = this
    wx.request({
      url: 'http://monktiger.natapp1.cc/question/getQuestionList',
      method: 'GET',
      data: data,
      header: {
        "token": app.globalData.token
      },
      success: function (result) {
        console.log(result);
        let paperDetails = result.data;
        that.setData({
          // 试卷的题
          questionList: paperDetails.questionList,
          pageNum: 1
        })
        that.hideModal();
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
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    let that = this
    let data = {}
    data.pageNum = 1
    that.getQuestionList(data)
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
    console.log("ddd");
    let that = this
    that.setData({
      pageNum: that.data.pageNum + 1
    })
    let data = {};
    if (that.data.search != null || that.data.search == '') {
      data.search = that.data.search;
    }
    if (that.data.category != null) {
      data.category = that.data.category;
    }
    if (that.data.typeIndex != 0) {
      data.type = that.data.typeIndex
    }
    data.pageNum = that.data.pageNum
    console.log(data);
    // 获得搜索列表
    wx.request({
      url: 'http://monktiger.natapp1.cc/question/getQuestionList',
      method: 'GET',
      data: data,
      header: {
        "token": app.globalData.token
      },
      success: function (result) {
        console.log(result);
        let paperDetails = result.data;
        that.setData({
          // 试卷的题
          questionList: that.data.questionList.concat(paperDetails.questionList),
          pageNum: 1
        })
        if (paperDetails.questionList.length == 0) {
          console.log(that.data.isEnd);
          console.log(that.data.questionList);
          that.setData({
            isEnd: true
          })
        }
        that.hideModal();
      }, fail(e) {
        console.log(e);
      }
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})