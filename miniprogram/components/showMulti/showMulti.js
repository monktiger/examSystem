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

  },
  lifetimes: {
    attached: function (e) {
      // 获取多选题缓存
      var multiQues = wx.getStorageSync('multi_ques');
      this.setData({
        multiQues: multiQues
      })
      console.log("multiQues",multiQues)
    }
  },
})
