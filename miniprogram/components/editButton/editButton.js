// components/editButton/editButton.js
var app = getApp();
const _UTIL = require("../../utils/util.js");
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    quesId: {
      type: String,
      value: ""
    },
    quesType: {
      type: String,
      value: ""
    },
    examId: {
      type: String,
      value: ""
    },
    quesIdx: {
      type: String,
      value: ""
    },
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    getType: function (e) {
      var quesType = this.data.quesType;
      if (quesType == 1) {
        var storage = "single_ques";
      } else if (quesType == 2) {
        var storage = "multi_ques";
      } else if (quesType == 3) {
        var storage = "fill_ques";
      } else if (quesType == 4) {
        var storage = "judge_ques";
      } else if (quesType == 5) {
        var storage = "short_ques";
      }
      this.setData({
        storage: storage,
      })
    },

    // 修改题目
    edit: function (e) {
      var quesType = this.data.quesType;
      if (quesType == 1) {
        var typeUrl = "../../pages/editSingle/editSingle";
      } else if (quesType == 2) {
        var typeUrl = "../../pages/editMulti/editMulti";
      } else if (quesType == 3) {
        var typeUrl = "../../pages/editFill/editFill";
      } else if (quesType == 4) {
        var typeUrl = "../../pages/editJudge/editJudge";
      } else if (quesType == 5) {
        var typeUrl = "../../pages/editShort/editShort";
      }
      app.globalData.editQueNum = this.data.quesIdx; //在对应题型里是第n题
      wx.redirectTo({
        url: typeUrl
      })
      console.log("typeUrl", typeUrl)
    },

    hideModal(e) {
      this.setData({
        modalName: null
      })
    },

    showModal(e) {
      this.setData({
        modalName: e.currentTarget.dataset.target
      })
    },

    // 获得学科名称
    getMajor(e){
      var val=e.detail.value;
      this.setData({
        majorVal:val
      })
    },

    // 添加到试题库
    toStore(e) {
      wx.showLoading({
        title: '加载中...',
      })
      var that=this;
      var storage = this.data.storage;
      var quesIdx = this.data.quesIdx;
      // 获取所属学科
      // app.globalData.modalName = e.currentTarget.dataset.target;
      var majorVal = this.data.majorVal;
      // 题目缓存
      var storage = wx.getStorageSync(storage);
      var data={
        title:storage[quesIdx].data.title,
        type:storage[quesIdx].data.type,
        category:majorVal,
        current:storage[quesIdx].data.current,
        answerA:storage[quesIdx].data.answerA||"",
        answerB:storage[quesIdx].data.answerB||"",
        answerC:storage[quesIdx].data.answerC||"",
        answerD:storage[quesIdx].data.answerD||"",
      }
      console.log("addstorage",data)
      wx.request({
        url: app.globalData.url+"question/addQuestion",
        method: "post",
        header: {
          "token": app.globalData.token,
          "Content-Type": "application/json"
        },
        data: JSON.stringify(data),
        success: function (res) {
          console.log(res)
          if (res.data.status == 1) {
            wx.hideLoading();
            that.setData({
              majorVal:""
            })
            that.hideModal();
            wx.showToast({
              title: '添加成功'
            })
          } else {
            console.log(res.msg);
          }
        },
        fail: function (error) {
          console.log(error);
        }
      })
    },

    // 删除题目
    delete: function (e) {
      var that = this;
      console.log(this.data.examId)
      wx.showModal({
        title: '',
        content: '确认要删除该题目吗？',
        success: function (result) {
          if (result.confirm) {
            wx.showLoading({
              title: '加载中...',
            })
            var quesIdx = that.data.quesIdx;
            wx.request({
              url: app.globalData.url + "exam/deleteQuestion",
              method: "get",
              header: {
                "token": app.globalData.token,
                "Content-Type": "application/x-www-form-urlencoded"
              },
              data: {
                examId: that.data.examId,
                id: that.data.quesId,
              },
              success: function (res) {
                console.log("res:", res);
                if (res.data.status == 1) {
                  wx.hideLoading();
                  // 设置题目缓存
                  var storage = that.data.storage;
                  console.log("storage", storage);
                  var ques = wx.getStorageSync(storage);
                  console.log("ques", ques);
                  var arr = []
                  for (let i in ques) {
                    let o = {};
                    o[i] = ques[i];
                    arr.push(o[i])
                  }
                  _UTIL.arrRemoveObj(arr, arr[quesIdx]);
                  wx.removeStorage({
                    key: storage,
                    success(res) {
                      console.log(res)
                    }
                  })
                  console.log("arr:", arr);
                  wx.setStorageSync(storage, arr);

                  // 刷新页面
                  var newStorage = { newStorage: arr };
                  that.triggerEvent("toNewStorage", newStorage)

                } else {
                  wx.hideLoading();
                  wx.showToast({
                    title: '删除失败',
                    icon:'none'
                  })
                  console.log("errorMsg:" + res.msg);
                }
              },
              fail: function (error) {
                console.log("errorMsg:" + error);
              }
            })
          } else {
            console.log('点击取消回调')
          }
        }
      });
    }
  },
  lifetimes: {
    attached: function (e) {

    }
  },
})
