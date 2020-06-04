// components/showShort/showShort.js
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
    getStorage:function(e){
      this.setData({
        shortQues:e.detail.newStorage
      })
      console.log("newStorage",e.detail)
    },
  },

  lifetimes: {
    attached: function (e) {
      // 获取简答题缓存
      var shortQues = wx.getStorageSync('short_ques');
      var multiQues = wx.getStorageSync('multi_ques');
      var SingleQus = wx.getStorageSync('single_ques');
      var fillQues = wx.getStorageSync('fill_ques');
      var judgeQues = wx.getStorageSync('judge_ques');
      var quesLen=SingleQus.length+multiQues.length+fillQues.length+judgeQues.length;
      this.setData({
        shortQues: shortQues,
        quesLen:quesLen
      })
    }
  },

})
