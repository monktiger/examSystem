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
    // 按钮显示的字
    btn: '提交',
    // 试卷的ID
    examId: app.globalData.examId,
    // 题的序号
    questionIndex: 0,
    // 现在显示的题
    displayQuestion: {},
    // 状态码
    status: 0,
    // 试卷的题的长度
    length: 0,
    // 问题列表
    questionList: [],
    //是否需要进行判分
    isScore: app.globalData.isScore,
    score: 0
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
    // 判断是否进行成绩上传并上传
    that.judgeScore();
    // 选择组件
    if (this.data.status == '20002') {
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
    if (this.data.status == '20002') {
      that.selectComp();
    }
    wx.navigateBack({
      delta: 2
    })
  },
  // 选择组件进行提交操作
  selectComp(e) {
    let that = this;
    // 1单选
    if (that.data.displayQuestion.type == 1) {
      singleAnswer = that.selectComponent("#singleAnswer")
      console.log(singleAnswer);
      singleAnswer.updateAnswer();
      // 2多选
    } else if (that.data.displayQuestion.type == 2) {
      multipleChoice = that.selectComponent("#multipleChoice")
      multipleChoice.updateAnswer();
    }
    // 3填空
    else if (that.data.displayQuestion.type == 3) {
      fill = that.selectComponent("#fill")
      fill.updateAnswer();
    }
    // 4判断
    else if (that.data.displayQuestion.type == 4) {
      judge = that.selectComponent("#judge")
      judge.updateAnswer();
    }
    // 5主观
    else if (that.data.displayQuestion.type == 5) {
      subjective = that.selectComponent("#subjective")
      subjective.updateAnswer();
    }
  },
  // 提交答案
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
  // 去试题编辑页
  goPaperCreate(e) {
    let that = this
    app.globalData.examId = that.data.examId
    app.globalData.displayQuestion = that.data.displayQuestion
    wx.navigateTo({
      url: '/pages/paperCreate/paperCreate',
    })
  },
  // 模态窗
  showModal(e) {
    this.setData({
      modalName: e.currentTarget.dataset.target,
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  // 主观题判分框
  inputHd(e) {
    this.setData({
      score: parseInt(e.detail.value)
    })
  },
  // 判断是否进行成绩上传并上传
  judgeScore(e) {
    if (that.data.status == '10004' && that.data.isScore == false && that.data.displayQuestion.type == 5) {
      wx.request({
        url: 'http://monktiger.natapp1.cc/exam/judge/score',
        method: 'GET',
        data: {
          copyId: app.globalData.copyId,
          id: that.data.displayQuestion.id,
          score: that.data.score
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
    }
  },
  // 弹出写评论的弹框
  showJudge(e) {
    this.judgeScore();
    this.setData({
      modalName: e.currentTarget.dataset.target
    })
  },
  // 评价的value
  inputJudge(e) {
    this.setData({
      inputJudge: e.detail.value
    })
  },
  // 提交评价
  submitJudge(e) {
      wx.request({
        url: 'http://monktiger.natapp1.cc/exam/judge/score',
        method: 'GET',
        data: {
          copyId: app.globalData.copyId,
          judge: that.data.inputJudge
        },
        header: {
          "token": app.globalData.token
        },
        success: function (result) {
          console.log(result)
          wx.navigateBack({
            delta: 2
          })
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
    for (i = 0; i < questionList.length; i++) {
      if (questionList[i].answer != questionList[i].correct) {
        questionList[i].judge = false
      } else {
        questionList[i].judge = true
      }
    }
    // 10004审核之前为提交 审核时候为返回
    // 还没完善
    if (status == '20003' || status == '10003' || status == '10002') {
      btn = '返回'
    } else if (status == '10004' || status == "20002") {
      btn = "提交"
    }
    else if (status == '10001') {
      btn = "保存"
    }
    that.setData({
      status: status,
      length: questionList.length - 1,
      questionList: questionList,
      displayQuestion: questionList[0],
      btn: btn
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
    this.setData({
      isScore: app.globalData.isScore,
      inputJudge:'',
      score:0
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