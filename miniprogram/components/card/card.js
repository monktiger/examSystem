// components/card/card.js
var app = getApp();
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
  attached: function() {
    console.log(this.data.cardList);
    
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
<<<<<<< Updated upstream
      console.log(that.data.TabCur);
      this.triggerEvent('clickCard', data)
=======
      // console.log(that.data.TabCur);
      this.triggerEvent('clickCard', data);
      app.globalData.groupName=e.currentTarget.dataset.groupname;
      app.globalData.groupId=e.currentTarget.dataset.groupid;
>>>>>>> Stashed changes
    },
    // 退出组
    quit(e) {
      this.setData({
        modalName: null
      })
      let data={
        groupid:that.data.groupid
      }
      this.triggerEvent('delete', data)
    },
    // 删除组
    delete(e) {
      let that =this
      console.log(that.data.groupid);
      this.setData({
        modalName: null,
      })
      let data={
        groupid:that.data.groupid,
        index:that.data.index
      }
      this.triggerEvent('delete', data)
    },
    // 模态窗
    showModal(e) {
      this.setData({
        modalName: e.currentTarget.dataset.target,
      })
    },
    showModal1(e) {
      this.setData({
        modalName: e.currentTarget.dataset.target,
        groupid:e.currentTarget.dataset.groupid,
        index:e.currentTarget.dataset.index
      })
    },
    hideModal(e) {
      this.setData({
        modalName: null
      })
    },
  }
})
