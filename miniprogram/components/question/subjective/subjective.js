// components/subjective/subjective.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    question:Object,
    index:Number
  },

  /**
   * 组件的初始数据
   */
  data: {
    answerContent:""
  },
  observers: {
    'question': function(answer) {
      let that = this
      if (that.data.question.current) {
        that.setData({
          answerContent: that.data.question.current || ''
        })
      } else {
        that.setData({
          answerContent: that.data.question.answer || ''
        })
      }
    },
    },
  /**
   * 组件的方法列表
   */
  methods: {
    inputHd(e){
      this.setData({
        answerContent:e.detail.value
      })
    },
    updateAnswer(e){
      let data={
        id:this.data.question.id,
        answer:this.data.answerContent
      }
      console.log(this.data.answerContent);
      this.triggerEvent('updateAnswer', data)
    },
  }
})
