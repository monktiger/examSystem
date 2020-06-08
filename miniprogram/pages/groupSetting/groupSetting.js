// pages/groupSetting/groupSetting.js
var app = getApp();
Page({

  data: {

  },

  onLoad: function (options) {
    console.log(app.globalData.groupId);
    
    var editGroupUrl = app.globalData.url + "group/nodify";
    var showMemberUrl = app.globalData.url + "group/showMember";
    this.setData({
      editGroupUrl: editGroupUrl,
      groupName: app.globalData.groupName,
      groupId: app.globalData.groupId
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

  // 获得组成员人数
  // getMemberCount: function (e) {
  //   var that = this;
  //   // 发起网络请求
  //   wx.request({
  //     url: that.data.showMemberUrl,
  //     method: "get",
  //     header: {
  //       "token": app.globalData.token
  //     },
  //     data: {
  //       groupId: that.data.groupId
  //     },
  //     success: function (res) {
  //       if (res.data.status == 1) {
  //         var count = res.memberList.length;
  //         this.setData({
  //           count: count
  //         })
  //       }
  //       else {
  //         console.log("status:" + res.status + ";msg:" + res.msg);
  //       }
  //     },
  //     fail: function (error) {
  //       console.log(error);
  //     }
  //   })
  // },

  // 获取组名
  getGroupName: function (e) {
    this.setData({
      setName: e.detail.value,
    });
    console.log("组名" + this.data.setName);
  },

  // 设置组名
  setGroupName(e) {
    wx.showLoading({
      title: '加载中...',
    })
    var that = this;
    // 发起网络请求
    wx.request({
      url: that.data.editGroupUrl,
      method: "get",
      header: {
        "token": app.globalData.token
      },
      data: {
        name: that.data.setName,
        groupId: that.data.groupId
      },
      success: function (res) {
        console.log("editGroup",res)
        if (res.data.status == 1) {
          wx.hideLoading();
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
          app.globalData.groupName=that.data.setName;
          that.onLoad();
        }
        else {
          // 弹窗失败
          wx.showToast({
            title: '修改失败！', // 标题
            icon: 'none', // 图标类型，默认success
            duration: 1500 // 提示窗停留时间，默认1500ms
          })
          console.log("status:" + res.status + ";msg:" + res.msg);
        }
      },
      fail: function (error) {
        console.log(error);
      }
    })
  },




})