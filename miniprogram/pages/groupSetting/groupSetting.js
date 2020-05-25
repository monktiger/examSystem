// pages/groupSetting/groupSetting.js
var app = getApp();
Page({

  data: {

  },

  onLoad: function(options) {
    var editGroupUrl = app.globalData.url + "group/nodify";
    this.setData({
      editGroupUrl: editGroupUrl,
    });
  },

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

  // 获取组名
  groupName: function(e) {
    this.setData({
      groupName: e.detail.value,
    });
    console.log("组名" + this.data.groupName);
  },

  // 设置组名
  setGroupName(e) {
    var that = this;
    // 发起网络请求
    wx.request({
      url: that.data.editGroupUrl,
      method: "get",
      header: {
        "content-type": ""
      },
      data: {
        name: that.data.groupName,
        groupId: "ASXERV" // ***组Id
      },
      success: function(res) {
        if (res.status == 1) {
          // 隐藏modal
          that.setData({
            modalName: null
          })
          // 弹窗成功
          wx.showToast({
            title: '修改成功！', // 标题
            icon: 'success', // 图标类型，默认success
            duration: 1500 // 提示窗停留时间，默认1500ms
          })
          // 刷新页面
          that.onLoad();
        }
        else{
          // 弹窗失败
          wx.showToast({
            title: '修改失败！', // 标题
            icon: 'none', // 图标类型，默认success
            duration: 1500 // 提示窗停留时间，默认1500ms
          })
          console.log("status:"+res.status+";msg:"+res.msg);
        }
      },
      fail: function(error) {
        console.log(error);
      }
    })
  },




})