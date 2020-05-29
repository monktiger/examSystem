// components/optionQue/optionQue.js
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
    attached: function (e) {
      // 获取简答题缓存
      var singleQues = wx.getStorageSync('single_ques');
      this.setData({
        singleQues: singleQues
      })
    }
  }
})
