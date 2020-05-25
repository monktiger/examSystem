// pages/managePeople/managePeople.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var deleteMemberUrl = app.globalData.url + "group/deleteMember";
    var showMemberUrl = app.globalData.url + "group/showMember";
    this.getMemberListData(showMemberUrl);

    this.setData({
      deleteMemberUrl: deleteMemberUrl,
    });
  },

  getMemberListData: function(showMemberUrl){
    var that = this;
    // 发起网络请求
    wx.request({
      url: showMemberUrl,
      method: "get",
      header: {
        "content-type": ""
      },
      data:{
        groupId:"AXDV64" //待修改
      },
      success: function (res) {
        that.processMemberData(res.memberList);
      },
      fail: function (error) {
        console.log(error);
      }
    })
  },

  processMemberData: function (memberList) {
    var members = [];
    for (var idx in memberList) {
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
    }
    this.setData({
      members: members
    });
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
  onBindConfirm: function (event) {
    var text = event.detail.value;
    // var searchUrl = app.globalData.doubanBase + "/v2/movie/search?q=" + text;
    // this.getMovieListData(searchUrl, "searchResult", "");
  },

  delete(e) {
    var that = this;
    wx.showModal({
      title: 'confirm的弹窗',
      content: '确认要删除该成员吗？',
      success: function(res) {
        if (res.confirm) {
          console.log('点击确认回调')
          // 发起网络请求
          wx.request({
            url: that.data.deleteMemberUrl,
            method: "get",
            header: {
              "content-type": ""
            },
            data: {
              groupId: "AXDV64",
              openId: "AXasdadsdadaDV64aaa"
            },
            success: function (res) {
              // 弹窗成功
              wx.showToast({
                title: '删除成功！', // 标题
                icon: 'success', // 图标类型，默认success
                duration: 1500 // 提示窗停留时间，默认1500ms
              })
              // 刷新页面
              that.onLoad();
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