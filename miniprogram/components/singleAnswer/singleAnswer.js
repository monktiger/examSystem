// components/singleAnswer/singleAnswer.js

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    question: Object,
    index: Number
  },

  /**
   * 组件的初始数据
   */
  data: {
    cur: '',
  },
  observers: {
    'question.answer': function(answer) {
      let that = this
      that.setData({
        cur: that.data.question.answer || ''
      })
    },
    },
  /**
   * 组件的方法列表
   */
  methods: {
    updateAnswer(e) {
      let data = {
        id: this.data.question.id,
        answer: this.data.cur
      }
      this.triggerEvent('updateAnswer', data)
    },
    clickCur(e) {
      this.setData({
        cur: e.currentTarget.dataset.cur
      })
    }
  }
})
