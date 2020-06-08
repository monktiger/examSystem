// pages/paper/paper.js
const _UTIL = require("../../utils/util.js");
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
    isScore: true,
    isJudge: true,
    score: 0
  },

  //返回
  // back: function (e) {
  //   wx.redirectTo({
  //     url: '/pages/manageGroup/manageGroup',
  //   })
  // },

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
    wx.showLoading({
      title: '加载中...',
    })
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
        wx.hideLoading();
        console.log(result)
      }, fail(e) {
        console.log(e);
      }
    })
  },
  // 去试题编辑页
  goPaperCreate(e) {
    let that = this;
    // app.globalData.examId = this.data.examId;
    console.log("app.globalData.examId", app.globalData.examId)
    app.globalData.displayQuestion = that.data.displayQuestion
    app.globalData.isEdit = 1;
    // 判断跳转的页面
    var typeUrl = this.getType().typeUrl;
    app.globalData.editQueNum = this.getType().quesIdx;
    wx.navigateTo({
      url: typeUrl,
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

    let that = this
    if (that.data.status == '10004' && that.data.isScore == false && that.data.displayQuestion.type == 5) {
      wx.showLoading({
        title: '加载中...',
      })
      wx.request({
        url: 'http://monktiger.natapp1.cc/judge/score',
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
          wx.hideLoading();
          console.log(result)
        }, fail(e) {
          console.log(e);
        }
      })
    }
  },
  addScore(e) {
    this.judgeScore();
    wx.navigateBack({
      delta: 2
    })
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
    wx.showLoading({
      title: '加载中...',
    })
    let that = this
    wx.request({
      url: 'http://monktiger.natapp1.cc/judge/judge',
      method: 'GET',
      data: {
        copyId: app.globalData.copyId,
        judge: that.data.inputJudge
      },
      header: {
        "token": app.globalData.token
      },
      success: function (result) {
        wx.hideLoading();
        console.log(result)
        wx.navigateBack({
          delta: 2
        })
      }, fail(e) {
        console.log(e);
      }
    })
  },

  // 获得缓存前 把数组对象转为数组
  toArr: function (storage) {
    console.log("storage", storage);
    var ques = wx.getStorageSync(storage);
    console.log("ques", ques);
    var arr = []
    for (let i in ques) {
      let o = {};
      o[i] = ques[i];
      arr.push(o[i])
    }
    return arr;
  },

  // 删除题目
  delete: function (e) {
    wx.showLoading({
      title: '加载中...',
    })
    var that = this;
    console.log("app.globalData.examId", app.globalData.examId)
    wx.request({
      url: app.globalData.url + "exam/deleteQuestion",
      method: "get",
      header: {
        "token": app.globalData.token,
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        examId: app.globalData.examId,
        id: e.currentTarget.dataset.idx,
      },
      success: function (res) {

        console.log("res:", res);

        if (res.data.status == 1) {
          wx.hideLoading();
          // 确定本题在该题型中是第几题，更新缓存内容
          var quesIdx = that.getType().quesIdx;
          var storage = that.getType().storage;
          // 设置题目缓存
          var arr = that.toArr(storage);
          _UTIL.arrRemoveObj(arr, arr[quesIdx]);
          wx.removeStorage({
            key: storage,
            success(res) {
              console.log(res)
            }
          })
          console.log("arr:", arr);
          wx.setStorageSync(storage, arr);

          that.hideModal();
          // 刷新页面
          var questionIndex = that.data.questionIndex;

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
              app.globalData.questionList = result.data.questionList;
              let questionList = result.data.questionList;
              let status = app.globalData.stuStatus;
              if (questionList.length == 0) {
                wx.showModal({
                  title: '提示',
                  content: '已无剩余题目',
                  cancelText: '删除试卷',
                  confirmText: "编辑试卷",
                  success(res) {
                    if (res.confirm) {
                      console.log('编辑试卷')
                      // wx.redirectTo({
                      //   url: '/pages/paperDetails/paperDetails',
                      // })
                      wx.navigateBack({
                        delta: 2
                      })
                    } else if (res.cancel) {
                      console.log('删除试卷')
                      wx.request({
                        url: app.globalData.url + "exam/deleteExam",
                        method: "get",
                        header: {
                          "token": app.globalData.token,
                          "Content-Type": "application/x-www-form-urlencoded"
                        },
                        data: {
                          examId: app.globalData.examId
                        },
                        success: function (res) {
                          wx.showToast({
                            title: '删除成功',
                            icon: "none"
                          })
                          // wx.redirectTo({
                          //   url: '/pages/manageGroup/manageGroup',
                          // })
                          wx.navigateBack({
                            delta: 2
                          })
                        },
                        fail: function (error) {
                          wx.showToast({
                            title: '删除失败',
                            icon: "none"
                          })
                          console.log("errorMsg:" + error);
                        }
                      })
                    }
                  }
                })

              } else {
                that.setData({
                  status: status,
                  length: questionList.length - 1,
                  questionList: questionList,
                  displayQuestion: questionList[questionIndex],
                  questionIndex: questionIndex,
                  btn: "保存"
                })
              }

            }, fail(e) {
              console.log(e);
            }
          })


          // 如果没有题目，用户选择删除试卷或继续编辑


          that.onLoad();
        } else {
          console.log("errorMsg:" + res.msg);
        }
      },
      fail: function (error) {
        console.log("errorMsg:" + error);
      }
    })
  },

  //由type获得本题在该题型中是第几题,跳转相应的修改页面url,缓存名称
  getType: function (e) {
    var quesType = this.data.displayQuestion.type;
    var quesIdx;
    if (quesType == 1) {
      var storage = "single_ques";
      quesIdx = this.data.questionIndex;//题号
      var typeUrl = "/pages/editSingle/editSingle"
    } else if (quesType == 2) {
      var storage = "multi_ques";
      var arrLen = this.toArr("single_ques").length;
      quesIdx = this.data.questionIndex - arrLen;
      var typeUrl = "/pages/editMulti/editMulti"
    } else if (quesType == 3) {
      var storage = "fill_ques";
      var arrLen = this.toArr("single_ques").length + this.toArr("multi_ques").length + this.toArr("judge_ques").length;
      quesIdx = this.data.questionIndex - arrLen;
      var typeUrl = "/pages/editFill/editFill"
    } else if (quesType == 4) {
      var storage = "judge_ques";
      var arrLen = this.toArr("single_ques").length + this.toArr("multi_ques").length;
      quesIdx = this.data.questionIndex - arrLen;
      var typeUrl = "/pages/editJudge/editJudge"
    } else if (quesType == 5) {
      var storage = "short_ques";
      var arrLen = this.toArr("single_ques").length + this.toArr("multi_ques").length + this.toArr("judge_ques").length + this.toArr("fill_ques").length;
      quesIdx = this.data.questionIndex - arrLen;
      var typeUrl = "/pages/editShort/editShort"
    }
    var info = {
      storage: storage,
      quesIdx: quesIdx,
      typeUrl: typeUrl,
    }
    return info;
  },
  /**
   * 生命周期函数--监听页面加载
   */

  onShow: function (options) {
    // console.log(app.globalData.isJudge);
    let that = this
    this.setData({
      isJudge: app.globalData.isJudge,
      isScore: app.globalData.isScore,
      isAlreayEdit: app.globalData.isAlreadyEdit,
      inputJudge: '',
      score: 0
    })
    // 是否未编辑状态返回
    if (app.globalData.isAlreadyEdit) {
      app.globalData.isAlreadyEdit = false;
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
          app.globalData.questionList = result.data.questionList;
          let questionList = result.data.questionList;
          let status = app.globalData.stuStatus;
          that.setData({
            status: status,
            length: questionList.length - 1,
            questionList: questionList,
            displayQuestion: questionList[app.globalData.editQueNum],
            questionIndex: app.globalData.editQueNum,
            btn: "保存"
          })
        }, fail(e) {
          console.log(e);
        }
      })
    } else {
      console.log("app.globalData.questionList", app.globalData.questionList);
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