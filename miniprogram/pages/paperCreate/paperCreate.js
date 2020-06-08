// pages/paperCreate/paperCreate.js
var app = getApp();
var date = new Date();
var currentHours = date.getHours();
var currentMinute = date.getMinutes();
Page({
    data: {},

    examName: function (e) {
        this.setData({
            examName: e.detail.value
        })
        console.log("examName", e.detail.value);
    },

    detail: function (e) {
        this.setData({
            detail: e.detail.value
        })
    },

    back: function (e) {
        wx.navigateBack({
            delta: 1
        })
    },

    createPaper: function (e) {
        wx.showLoading({
            title: '加载中...',
        })
        var that = this;
        var groupId = [this.data.groupId];
        var examId = this.data.examId;
        var name = this.data.examName;
        var beginTime = app.globalData.beginTime;
        var endTime = app.globalData.endTime;
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
            console.log(data);

            // 发起网络请求
            wx.request({
                url: that.data.createExamUrl,
                method: "post",
                header: {
                    "token": app.globalData.token,
                    "Content-Type": "application/json"
                },
                data: JSON.stringify(data),
                success: function (res) {
                    console.log(res);
                    if (res.data.status == 1) {
                        wx.hideLoading();
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
                fail: function (error) {
                    console.log(error);
                }
            })
        }
    },

    onLoad: function (options) {
        var createExamUrl = app.globalData.url + "exam/createExam";
        this.setData({
            createExamUrl: createExamUrl,
            groupId: app.globalData.groupId,
            examId: app.globalData.examId,
            examName: app.globalData.examName,
        });

    },

    onShow: function () {
        wx.removeStorage({
            key: 'single_ques',
            success(res) {
                console.log(res)
            }
        })
        wx.removeStorage({
            key: 'multi_ques',
            success(res) {
                console.log(res)
            }
        })
        wx.removeStorage({
            key: 'judge_ques',
            success(res) {
                console.log(res)
            }
        })
        wx.removeStorage({
            key: 'fill_ques',
            success(res) {
                console.log(res)
            }
        })
        wx.removeStorage({
            key: 'short_ques',
            success(res) {
                console.log(res)
            }
        })
        wx.removeStorage({
            key: 'title',
            success(res) {
                console.log(res)
            }
        })
    }

})