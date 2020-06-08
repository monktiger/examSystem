// components/paperCard/paperCard.js
const app = getApp();
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    paperList: Object
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
      let data = {
        examId: e.currentTarget.dataset.examid,
        beginTime: e.currentTarget.dataset.begintime,
        endTime: e.currentTarget.dataset.endtime,
      }
      console.log(data);

      this.triggerEvent('changePage', data)
      // console.log("examId",app.globalData.examId)
    },
    showModal(e) {
      this.setData({
        modalName: e.currentTarget.dataset.target,
      })
    },
    hideModal(e) {
      this.setData({
        modalName: null
      })
    },
    showbottomModal(e) {
      // console.log(e.currentTarget.dataset.examid);
      this.setData({
        examId: e.currentTarget.dataset.examid,
        index: e.currentTarget.dataset.index,
        modalName: e.currentTarget.dataset.target,
      })
    },
    deleteExam(e) {
      wx.showLoading({
        title: '加载中...',
      })
      let that = this
      wx.request({
        url: 'http://monktiger.natapp1.cc/exam/deleteExam',
        method: 'GET',
        data: {
          examId: that.data.examId,
        },
        header: {
          "token": app.globalData.token
        },
        success: function (result) {
          wx.hideLoading();
          // console.log(result.data.status);
          if (result.data.status == -3) {
            that.setData({
              modalName: 'tipsMarkModal'
            })
          } else {
            let paperList = that.data.paperList;
            paperList.splice(that.data.index, 1);
            that.setData({
              paperList: paperList,
            })
            that.hideModal()
          }

        }, fail(e) {
          console.log(e);

        }
      })
    }
  }
})
