// components/multipleChoice/multipleChoice.js
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
    curA: false,
    curB: false,
    curC: false,
    curD: false,
    answer: ""
  },
  observers: {
    'question': function (answer) {
      let that = this
      // console.log(that.data.question.answer);
      if (that.data.question.current) {
        let that = this;
        let b = that.data.question.current;
        let curA = that.data.curA
        let curB = that.data.curB
        let curC = that.data.curC
        let curD = that.data.curD
        console.log(b);
        for (let i = 0; i < b.length; i++) {
          if (b.indexOf('A')!=-1 ) {
            curA = true
          }
          if (b.indexOf('B')!=-1 ) {
            curB = true
          }
          if (b.indexOf('C')!=-1 ) {
            curC = true
          }
          if (b.indexOf('D')!=-1){
            curD = true
          }
        }
        that.setData({
          curA: curA,
          curB: curB,
          curC: curC,
          curD: curD,
        })
      } else if (that.data.question.answer != '' || that.data.question.answer != null) {
        let that = this;
        let b = that.data.question.answer.split('');
        let curA = that.data.curA
        let curB = that.data.curB
        let curC = that.data.curC
        let curD = that.data.curD
        console.log(b);
        for (let i = 0; i < b.length; i++) {
          if (b[i] == 'A') {
            curA = true
          }
          if (b[i] == 'B') {
            curB = true
          }
          if (b[i] == 'C') {
            curC = true
          }
          if (b[i] == 'D') {
            curD = true
          }
        }
        that.setData({
          curA: curA,
          curB: curB,
          curC: curC,
          curD: curD,
        })
      }

    },
  },
  /**
   * 组件的方法列表
   */
  methods: {
    updateAnswer(e) {
      let that = this;
      let answer = that.data.answer
      if (that.data.curA == true) {
        answer = answer.concat("A")
      }
      if (that.data.curB == true) {
        answer = answer.concat("B")
      }
      if (that.data.curC == true) {
        answer = answer.concat("C")
      }
      if (that.data.curD == true) {
        answer = answer.concat("D")
      }
      let data = {
        id: that.data.question.id,
        answer: answer
      }
      console.log(answer);
      that.triggerEvent('updateAnswer', data)
      that.setData({
        answer: []
      })
    },
    clickCur(e) {
      let that = this
      if (e.currentTarget.dataset.cur == 'A') {
        that.setData({
          curA: !that.data.curA
        })
      } else if (e.currentTarget.dataset.cur == 'B') {
        that.setData({
          curB: !that.data.curB
        })
      } else if (e.currentTarget.dataset.cur == 'C') {
        that.setData({
          curC: !that.data.curC
        })
      } else {
        that.setData({
          curD: !that.data.curD
        })
      }
    }
  }
})
