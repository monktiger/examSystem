// pages/paper/paper.js
const app = getApp()
let singleAnswer
let multipleChoice
let judge
let fill
let subjective
Page({

  /**
   * 页面的初始数据
   */
  data: {
    questionIndex: 0,
    btn:'提交'
  },

  // 上一道题
  preQuestion(e) {
    let that = this;
    let questionIndex = that.data.questionIndex - 1;
    that.setData({
      questionIndex: questionIndex,
      displayQuestion: that.data.questionList[questionIndex],
    })
  },
  // 下一道题
  nextQuestion(e) {
    console.log(this.data.length);
    console.log(this.data.questionIndex);
    let that = this;
    // 选择组件
    if(this.data.status == '20002'){
      that.selectComp();
    }
    let questionIndex = that.data.questionIndex + 1;
    that.setData({
      questionIndex: questionIndex,
      displayQuestion: that.data.questionList[questionIndex],
    })
  },
  // 最后一道题
  lastQuestion(e) {
    let that = this;
    // 选择组件
    if(this.data.status == '20002'){
      that.selectComp();
    }
    wx.navigateBack({
      delta: 2
    })
  },
  // 选择组件进行提交操作
  selectComp(e) {
    let that = this;
    if (that.data.displayQuestion.type == 1) {
      singleAnswer = that.selectComponent("#singleAnswer")
      console.log(singleAnswer);
      singleAnswer.updateAnswer();
    } else if (that.data.displayQuestion.type == 2) {
      multipleChoice = that.selectComponent("#multipleChoice")
      multipleChoice.updateAnswer();
    }
    else if (that.data.displayQuestion.type == 3) {
      fill = that.selectComponent("#fill")
      fill.updateAnswer();
    }
    else if (that.data.displayQuestion.type == 4) {
      judge = that.selectComponent("#judge")
      judge.updateAnswer();
    }
    else if (that.data.displayQuestion.type == 5) {
      subjective = that.selectComponent("#subjective")
      subjective.updateAnswer();
    }
  },
  submitAnwer(e) {
    let that = this
    console.log(e.detail.id, e.detail.answer);
    wx.request({
      url: 'http://monktiger.natapp1.cc/exam/answerSubmit',
      method: 'GET',
      data: {
        copyId: app.globalData.copyId,
        id: e.detail.id,
        answer: e.detail.answer
      },
      header: {
        "token": app.globalData.token
      },
      success: function (result) {
        console.log(result)
      }, fail(e) {
        console.log(e);
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */

  onLoad: function (options) {
    console.log(app.globalData.questionList);
    let that = this;
    let btn = that.data.btn;
    let status = app.globalData.stuStatus;
    let questionList = app.globalData.questionList;
    let i;
    for(i=0;i<questionList.length;i++){
      if(questionList[i].answer!=questionList[i].correct){
        questionList[i].judge=false
      }else{
        questionList[i].judge=true
      }
    }
    if(status == '20003'){
      btn='返回'
    }else{
      btn="提交"
    }
    that.setData({
      status: status,
      length: questionList.length - 1,
      questionList: questionList,
      displayQuestion: questionList[0],
      btn:btn
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