// pages/paperCreate/paperCreate.js
var app = getApp();
var date = new Date();
var currentHours = date.getHours();
var currentMinute = date.getMinutes();
Page({
    data: {},

    examName: function(e) {
        this.setData({
            examName: e.detail.value
        })
        console.log("examName", e.detail.value);
    },

    detail: function(e) {
        this.setData({
            detail: e.detail.value
        })
    },

    createPaper: function(e) {
        var that = this;
        var groupId = [this.data.groupId];
        var examId = this.data.examId;
        var name = this.data.examName;
        var beginTime = app.globalData.startDateTime;
        var endTime = app.globalData.endDateTime;
        if (!name || !beginTime || !endTime) {
            wx.showToast({
                title: '请填写所有试卷信息！', // 标题
                icon: 'none', // 图标类型，默认success
                duration: 1500 // 提示窗停留时间，默认1500ms
            })
        } else {
            var data = {
                name: name,
                groupId: groupId,
                beginTime: beginTime,
                endTime: endTime,
                examId: examId || "",
            };
            // 发起网络请求
            wx.request({
                url: that.data.createExamUrl,
                method: "post",
                header: {
                    "token": app.globalData.token,
                    "Content-Type": "application/json"
                },
                data: JSON.stringify(data),
                success: function(res) {
                    console.log(res);
                    if (res.data.status == 1) {
                        app.globalData.examName = name;
                        app.globalData.beginTime = beginTime;
                        app.globalData.endTime = endTime;
                        app.globalData.examId = res.data.examId;
                        wx.navigateTo({
                            url: "../editPaper/editPaper"
                        })
                    } else if (res.data.status == -1) {
                        wx.showToast({
                            title: '时间设置存在问题！', // 标题
                            icon: 'none', // 图标类型，默认success
                            duration: 1500 // 提示窗停留时间，默认1500ms
                        })
                    } else {
                        console.log(res.data.info);
                    }
                },
                fail: function(error) {
                    console.log(error);
                }
            })
        }
    },

    onLoad: function(options) {
        var createExamUrl = app.globalData.url + "exam/createExam";
        this.setData({
            createExamUrl: createExamUrl,
            groupId: app.globalData.groupId,
            examId: app.globalData.examId,
            examName: app.globalData.examName,
        });
    },



})