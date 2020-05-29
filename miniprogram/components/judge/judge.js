// components/singleAnswer/singleAnswer.js
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
    cur:''
  },

  /**
   * 组件的方法列表
   */
  methods: {
    clickCur(e){
      this.setData({
        cur:e.currentTarget.dataset.cur
      })
    }
  }
})
