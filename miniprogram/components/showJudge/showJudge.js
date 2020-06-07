// components/showJudge/showJudge.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  methods: {
    edit: function (e) {
      // app.globalData.queId=e.currentTarget.dataset.num; //这道题在这套试卷里的题号
      // app.globalData.queNum=e.currentTarget.dataset.quenum; //这道题在这个题型里的题号
    },
    getStorage: function (e) {
      this.setData({
        judgeQues: e.detail.newStorage
      })
      console.log("newStorage", e.detail)
    },
  },
  pageLifetimes: {
    show: function () {
      // 获取单选题缓存
      var judgeQues = wx.getStorageSync('judge_ques');
      var multiQues = wx.getStorageSync('multi_ques');
      var SingleQus = wx.getStorageSync('single_ques');
      var quesLen = SingleQus.length + multiQues.length;
      this.setData({
        judgeQues: judgeQues,
        quesLen: quesLen
      })
      console.log("judgeQues", judgeQues)
    },
  }


})
