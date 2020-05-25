// components/card/card.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    TabCur: {
      type: Number,
      defult: 0
    },
    cardList: {
      type: Object,
      defult: []
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
  },
  // observers:{
  //   'TabCur'(TabCur) { 
  //     let that = this //单个监听
  //     that.setData({
  //       TabCur:TabCur
  //     })
  //   },
  // },
  /**
   * 组件的方法列表
   */
  methods: {
    // 点击大框，跳转页面
    clickCard: function (e) {
      let that = this
      let data = {
        TabCur: that.data.TabCur
      }
      console.log(that.data.TabCur);

      this.triggerEvent('clickCard', data)
    },
    // 退出组
    quit(e) {
      this.setData({
        modalName: null
      })
      let data={

      }
      this.triggerEvent('quit', data)
    },
    // 删除组
    delete(e) {
      this.setData({
        modalName: null
      })
      let data={

      }
      this.triggerEvent('delete', data)
    },
    // 模态窗
    showModal(e) {
      this.setData({
        modalName: e.currentTarget.dataset.target
      })
    },
    hideModal(e) {
      this.setData({
        modalName: null
      })
    },
  }
})
