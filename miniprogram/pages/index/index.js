//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
<<<<<<< Updated upstream
=======
          // 在跳转前？
          app.globalData.group_id = result.groupList.group_id;
          app.globalData.group_name = result.groupList.name;
        }, fail(e) {
          console.log(e);

        }
      })
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
    creatInput(e) {
      this.setData({
        creatInput: e.detail.value
      })
    },
    addInput(e) {
      this.setData({
        addInput: e.detail.value
      })
    },
    // 创建组
    create(e) {
      let that = this;
      console.log(this.data.creatInput);
      wx.request({
        url: 'http://monktiger.natapp1.cc/group/create',
        method: 'GET',
        data: {
          name: that.data.creatInput
        },
        header: {
          // "Content-Type": "application/x-www-form-urlencoded",
          "token": that.data.token
        },
        success: function (result) {
          console.log(result);
          wx.request({
            url: 'http://monktiger.natapp1.cc/group/getList',
            method: 'GET',
            data: {
              type: that.data.TabCur
            },
            header: {
              "token": that.data.token
            },
            success: function (result) {
              console.log(result);
              that.setData({
                elements: result.data.groups,
              })
            }, fail(e) {
              console.log(e);
            }
          })
        }, fail(e) {
          console.log(e);

        }
      })
    },
    // 加入组
    add(e) {
      let that = this;
      console.log(this.data.addInput);

      console.log(this.data.token);
      wx.request({
        url: 'http://monktiger.natapp1.cc/group/join',
        method: 'GET',
        data: {
          groupId: that.data.addInput
        },
        header: {
          // "Content-Type": "application/x-www-form-urlencoded",
          "token": that.data.token
        },
        success: function (result) {
          console.log(result);

        }, fail(e) {
          console.log(e);
        }
      })
    },
    // 解散组
    delete(e) {
      let that = this;
      console.log(e.detail.groupid);
      wx.request({
        url: 'http://monktiger.natapp1.cc/group/quit',
        method: 'GET',
        data: {
          groupId: e.detail.groupid
        },
        header: {
          // "Content-Type": "application/x-www-form-urlencoded",
          "token": that.data.token
        },
        success: function (result) {
          console.log(result);
          let elements = that.data.elements;
          elements.splice(e.detail.index, 1)
        }, fail(e) {
          console.log(e);

>>>>>>> Stashed changes
        }
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})
