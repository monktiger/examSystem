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
        var typeUrl = "/pages/editSingle/editSingle";
      } else if (quesType == 2) {
        var typeUrl = "../../pages/editMulti/editMulti";
      } else if (quesType == 3) {
        var typeUrl = "../../pages/editFill/editFill";
      } else if (quesType == 4) {
        var typeUrl = "../../pages/editJudge/editJudge";
      } else if (quesType == 5) {
        var typeUrl = "../../pages/editShort/editShort";
      }
      app.globalData.editQueNum=this.data.quesIdx; //在对应题型里是第n题
      wx.redirectTo({
        url: typeUrl 
      })
      console.log("typeUrl",typeUrl)
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
                  // 设置题目缓存
                  var storage = that.data.storage;
                  console.log("storage",storage);
                  var ques = wx.getStorageSync(storage);
                  console.log("ques",ques);
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
                  var newStorage = { newStorage: arr};
                  that.triggerEvent("toNewStorage", newStorage )
            
                } else {
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
