// components/multiQue/multiQue.js
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
        multiQues: e.detail.newStorage
      })
      console.log("newStorage", e.detail)
    },
  },
  pageLifetimes: {
    show: function () {
      // 获取多选题缓存
      var multiQues = wx.getStorageSync('multi_ques');
      var SingleQus = wx.getStorageSync('single_ques');
      var SingleQusLen = SingleQus.length
      this.setData({
        multiQues: multiQues,
        SingleQusLen: SingleQusLen
      })
      console.log("multiQues", multiQues)
    },
  },
})
