// components/paperCard/paperCard.js
const app = getApp();
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    paperList:Object
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
      let data={
        examId:e.currentTarget.dataset.examid,
        beginTime:e.currentTarget.dataset.begintime,
        endTime:e.currentTarget.dataset.endtime,
      }
      console.log(data);
      
      this.triggerEvent('changePage',data)
      // console.log("examId",app.globalData.examId)
    },
    showModal(e){
      this.setData({
        modalName: e.currentTarget.dataset.target,
      })
    },
    hideModal(e) {
      this.setData({
        modalName: null
      })
    },
    }
})
