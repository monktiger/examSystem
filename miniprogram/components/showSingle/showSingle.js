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
    edit:function(e){
      app.globalData.queId=e.currentTarget.dataset.num; //这道题在这套试卷里的题号
      app.globalData.queNum=e.currentTarget.dataset.quenum; //这道题在这个题型里的题号
    },
    getStorage:function(e){
      this.setData({
        singleQues:e.detail.newStorage
      })
      console.log("newStorage",e.detail)
    },
  },
  lifetimes: {
    attached: function (e) {
      // 获取单选题缓存
      var singleQues = wx.getStorageSync('single_ques');
      this.setData({
        singleQues: singleQues
      })
      console.log("singleQues",singleQues)
    }
  },

})
