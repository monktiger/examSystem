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
    this.getMemberListData(showMemberUrl,0);

    this.setData({
      deleteMemberUrl: deleteMemberUrl,
      groupId: app.globalData.group_id
    });
  },

  // 获得组成员列表
  getMemberListData: function (showMemberUrl,memberName) {
    var that = this;
    // 发起网络请求
    wx.request({
      url: showMemberUrl,
      method: "get",
      header: {
        "token": app.globalData.token
      },
      data: {
        groupId: that.data.groupId
      },
      success: function (res) {
        if (res.data.status == 1) {
          that.processMemberData(res.memberList,memberName);
        } else {
          console.log(res.msg);
        }
      },
      fail: function (error) {
        console.log(error);
      }
    })
  },

  // 展示组成员列表
  processMemberData: function (memberList, memberName) {
    var members = [];
    for (var idx in memberList) {
      if (memberName) {
        var names = memberList.filter((p) => {
          return p.name == memberName;
        });
        pushMemberList(names,members);
      } else {
        pushMemberList(memberList,members);
      }
    }
    this.setData({
      members: members
    });
  },

  // push成员列表
  pushMemberList: function(memberList,members){
    var subject = memberList[idx];
    var name = subject.name;
    if (name.length >= 6) {
      name = name.substring(0, 6) + "...";
    }
    var temp = {
      name: name,
      avatarUrl: subject.avatarUrl,
      openId: subject.openId,
    }
    members.push(temp);
    return members;
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
    that.getMemberListData(res.memberList,text);
  },

  onCancelImgTap: function (event) {
    this.setData({
      searchPanelShow: false,
      // 把所有成员展示
      searchResult: {}
    })
  },

  // 删除组成员
  delete(e) {
    var openId = e.currentTarget.dataset.openId;
    var that = this;
    wx.showModal({
      title: 'confirm的弹窗',
      content: '确认要删除该成员吗？',
      success: function (result) {
        if (result.confirm) {
          console.log('点击确认回调')
          // 发起网络请求
          wx.request({
            url: that.data.deleteMemberUrl,
            method: "get",
            header: {
              "token": that.data.token
            },
            data: {
              groupId: that.data.groupId,
              openId: openId
            },
            success: function (res) {
              if (res.data.status == 1) {
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