// components/showFill/showFill.js
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

  /**
   * 组件的方法列表
   */
  methods: {
    getStorage: function (e) {
      this.setData({
        fillQues: e.detail.newStorage
      })
      console.log("newStorage", e.detail)
    },
  },

  pageLifetimes: {
    show: function () {
      // 获取简答题缓存
      var fillQues = wx.getStorageSync('fill_ques');
      var multiQues = wx.getStorageSync('multi_ques');
      var SingleQus = wx.getStorageSync('single_ques');
      var judgeQues = wx.getStorageSync('judge_ques');
      var quesLen = SingleQus.length + multiQues.length + judgeQues.length;
      this.setData({
        fillQues: fillQues,
        quesLen: quesLen
      })
    },
  }
})
