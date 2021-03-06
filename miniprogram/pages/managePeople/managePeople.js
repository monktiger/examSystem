// pages/managePeople/managePeople.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    searchPanelShow: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var deleteMemberUrl = app.globalData.url + "group/deleteMember";
    var showMemberUrl = app.globalData.url + "group/showMember";
    this.setData({
      showMemberUrl:showMemberUrl,
      deleteMemberUrl: deleteMemberUrl,
      groupId: app.globalData.groupId
    });
    this.getMemberListData(showMemberUrl);
  },

  // 获得组成员列表
  getMemberListData: function (showMemberUrl) {
    wx.showLoading({
      title: '加载中...',
    })
    var that = this;
    var groupId = this.data.groupId;
    console.log(this.data.groupId)
    // 发起网络请求
    wx.request({
      url: showMemberUrl,
      method: "get",
      header: {
        "token": app.globalData.token
      },
      data: {
        groupId: groupId
      },
      success: function (res) {
        wx.hideLoading();
        console.log("getMemberListData",res);
        if (res.data.state == 1) {
 
          // that.processMemberData(res.memberList,memberName);
          that.setData({
            memberList: res.data.memberList
          });
          wx.setStorageSync('memberList', res.data.memberList);
        } else if(res.data.state == -1){
          wx.hideLoading();
          wx.showToast({
            title: '小组暂无成员',
            icon: 'none'
          })
        }else {
          console.log(res.msg);
        }
      },
      fail: function (error) {
        wx.hideLoading();
        console.log(error);
      }
    })
  },

  // ListTouch触摸开始
  ListTouchStart(e) {
    this.setData({
      ListTouchStart: e.touches[0].pageX
    })
  },

  // ListTouch计算方向
  ListTouchMove(e) {
    this.setData({
      ListTouchDirection: e.touches[0].pageX - this.data.ListTouchStart > 0 ? 'right' : 'left'
    })
  },

  // ListTouch计算滚动
  ListTouchEnd(e) {
    if (this.data.ListTouchDirection == 'left') {
      this.setData({
        modalName: e.currentTarget.dataset.target
      })
    } else {
      this.setData({
        modalName: null
      })
    }
    this.setData({
      ListTouchDirection: null
    })
  },

  // 搜索成员
  onBindFocus: function (event) {
    this.setData({
      searchPanelShow: true
    })
  },

  // 展示相应成员
  onBindConfirm: function (event) {
    var text = event.detail.value;
    let memberList=wx.getStorageSync('memberList');
    let searchList=[];
    for(let i in memberList){
      if(memberList[i].name.indexOf(text)!=-1){
        searchList.push(memberList[i]);
      }
    }
    this.setData({
      memberList: searchList
    })
    console.log("searchList",searchList)
  },

  onCancelImgTap: function (event) {
    this.setData({
      searchPanelShow: false,
      searchVal:"",
      memberList: wx.getStorageSync('memberList')
    })
  },

  // 邀请组成员 复制邀请码
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

  copyText: function (e) {
    console.log(e)
    var that=this;
    wx.setClipboardData({
      data: e.currentTarget.dataset.text,
      success: function (res) {
        wx.getClipboardData({
          success: function (res) {
            wx.showToast({
              title: '复制成功'
            })
            that.hideModal();
          }
        })
      }
    })
  },

  // 删除组成员
  delete(e) {
    var openId =e.currentTarget.dataset.openid;
    var groupId = this.data.groupId;
    var that = this;
    wx.showModal({
      title: '',
      content: '确认要删除该成员吗？',
      success: function (result) {
        if (result.confirm) {
          wx.showLoading({
            title: '加载中...',
          })
          console.log('点击确认回调')
          // 发起网络请求
          wx.request({
            url: that.data.deleteMemberUrl,
            method: "get",
            header: {
              "token": app.globalData.token
            },
            data: {
              groupId: groupId,
              openId: openId
            },
            success: function (res) {
              console.log(res);
              if (res.data.state == 1) {
                wx.hideLoading();
                // 弹窗成功
                wx.showToast({
                  title: '删除成功！',
                  icon: 'success',
                  duration: 1500
                })
                // 刷新页面
                that.onLoad();
              } else {
                wx.showToast({
                  title: '删除失败！',
                  icon: 'none',
                  duration: 1500
                })
                console.log(res.msg);
              }
            },
            fail: function (error) {
              console.log(error);
            }
          })
        } else {
          console.log('点击取消回调')
        }
      }
    });
  },
})