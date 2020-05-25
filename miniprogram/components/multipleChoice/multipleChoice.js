// components/multipleChoice/multipleChoice.js
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
    curA:false,
    curB:false,
    curC:false,
    curD:false,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    clickCur(e){
      let that = this
      if(e.currentTarget.dataset.cur=='A'){
        that.setData({
          curA:!that.data.curA
        })
      }else if(e.currentTarget.dataset.cur=='B'){
        that.setData({
          curB:!that.data.curB
        })
      }else if(e.currentTarget.dataset.cur=='C'){
        that.setData({
          curC:!that.data.curC
        })
      }else{
        that.setData({
          curD:!that.data.curD
        })
      }
    }
  }
})
