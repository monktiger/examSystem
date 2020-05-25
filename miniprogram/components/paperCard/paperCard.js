// components/paperCard/paperCard.js
const app = getApp();
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
    pageId: 0
  },
  attached: function () {
    this.setData({
      pageId: app.globalData.pageId
    })
  },
  /**
   * 组件的方法列表
   */
  methods: {
    changePage(e) {
      this.triggerEvent('changePage')
    }
  }
})
