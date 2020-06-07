// pages/editJudge/editJudge.js
const _UTIL = require("../../utils/util.js");
var app = getApp();
Page({
    data: {
        index: null,
        picker: ['1', '2', '3', '4', '5'],
        array: [0, 0], //默认显示一个
        inputVal: [], //所有input的内容
        checked: []
    },

    PickerChange(e) {
        console.log(e);
        this.setData({
            index: e.detail.value
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        var addQuestionUrl = app.globalData.url + "exam/addQuestion";
        this.setData({
            addQuestionUrl: addQuestionUrl,
            examId: app.globalData.examId,
        });

        // 如果有editQueNum则是修改题目：先取出原数据，赋值
        var editQueNum = app.globalData.editQueNum;
        if (editQueNum || editQueNum == 0) {
            var editques = wx.getStorageSync('judge_ques');
            var editData = editques[editQueNum].data;
            var currentIdx = editData.currentIdx;
            var score = editData.score;
            var queId = editData.id;
            wx.setStorageSync("title", editData.title);
            var checkedArr = [];
            for (var i = 0; i < 2; i++) {
                if (i == currentIdx) {
                    checkedArr.push(true)
                } else {
                    checkedArr.push(false)
                }
            };
            this.setData({
                checked: checkedArr,
                index: parseInt(score) - 1,
                current: editData.current,
                editQueNum: editQueNum,
                queId: queId
            })
            console.log("index", this.data.index)
        }
    },

    //返回
    back: function (e) {
        wx.redirectTo({
            url: "/pages/editPaper/editPaper"
        })
    },

    // 提交判断题
    confirm: function (e) {
        wx.showLoading({
            title: '加载中...',
        })
        var that = this;
        var title = wx.getStorageSync("title");
        var score = parseInt(this.data.index) + 1;
        var current = this.data.current;
        var currentIdx = this.data.currentIdx;
        var data = {
            "title": title,
            "score": score,
            "type": 4,
            "current": current,
            "examId": this.data.examId,
            "id": this.data.queId || ""
        };
        if (!title || !score || !current) {
            wx.showToast({
                title: '请填写所有题目信息！', // 标题
                icon: 'none', // 图标类型，默认success
                duration: 1500 // 提示窗停留时间，默认1500ms
            })
        } else {
            // 发起网络请求
            wx.request({
                url: that.data.addQuestionUrl,
                method: "post",
                header: {
                    "token": app.globalData.token,
                    "Content-Type": "application/json"
                },
                data: JSON.stringify(data),
                success: function (res) {
                    console.log(res)
                    if (res.data.status == 1) {
                        wx.hideLoading();
                        data.id = res.data.id;
                        data.type = res.data.type;
                        // 设置题目缓存
                        var judgeQues = wx.getStorageSync('judge_ques');
                        var arr = []
                        for (let i in judgeQues) {
                            let o = {};
                            o[i] = judgeQues[i];
                            arr.push(o[i])
                        }
                        data.currentIdx = currentIdx;
                        // 如果是修改，则先把storage对应的题删掉 再重新push
                        console.log("editQueNum", app.globalData.editQueNum)
                        if (app.globalData.editQueNum) {
                            var editQueNum = that.data.editQueNum;
                            _UTIL.arrRemoveObj(arr, arr[editQueNum]);
                        }
                        arr.push({
                            data: data,
                        })
                        wx.removeStorage({
                            key: 'judge_ques',
                            success(res) {
                                console.log(res)
                            }
                        })
                        console.log("arr:", arr);
                        wx.setStorageSync('judge_ques', arr);
                        wx.setStorageSync('title', "");
                        if (app.globalData.isEdit) {
                            wx.redirectTo({
                                url: "../paper/paper"
                            })
                            app.globalData.isEdit = 0
                        } else {
                            wx.redirectTo({
                                url: "../editPaper/editPaper"
                            })
                        }
                    } else {
                        console.log(res.msg);
                    }
                },
                fail: function (error) {
                    console.log(error);
                }
            })
        }
    },

    //获取input的值
    getInputVal: function (e) {
        var nowIdx = e.currentTarget.dataset.idx; //获取当前索引
        var val = e.detail.value; //获取输入的值
        var oldVal = this.data.inputVal;
        oldVal[nowIdx] = val; //修改对应索引值的内容
        this.setData({
            inputVal: oldVal
        })
        console.log(oldVal)
    },

    //添加input
    addInput: function () {
        var old = this.data.array;
        var oldLen = old.length;
        if (oldLen > 3) {
            // 弹窗
            wx.showToast({
                title: '最多4个选项！', // 标题
                icon: 'none',
                duration: 1500 // 提示窗停留时间，默认1500ms
            })
        } else {
            var old = this.data.array;
            old.push(1); //这里不管push什么，只要数组长度增加1就行
            this.setData({
                array: old
            })
        }
    },

    //删除input
    delInput: function (e) {
        var old = this.data.array;
        var oldLen = old.length;
        if (oldLen < 3) {
            // 弹窗
            wx.showToast({
                title: '至少2个选项！', // 标题
                icon: 'none',
                duration: 1500 // 提示窗停留时间，默认1500ms
            })
        } else {
            var that = this;
            // 弹窗
            wx.showModal({
                title: '提示',
                content: '是否确定删除该选项？',
                success(res) {
                    if (res.confirm) {
                        var nowidx = e.currentTarget.dataset.idx; //当前索引
                        var oldInputVal = that.data.inputVal; //所有的input值
                        var oldarr = that.data.array; //循环内容
                        oldarr.splice(nowidx, 1); //删除当前索引的内容，这样就能删除view了
                        oldInputVal.splice(nowidx, 1); //view删除了对应的input值也要删掉
                        that.setData({
                            array: oldarr,
                            inputVal: oldInputVal
                        })
                    } else if (res.cancel) {
                        console.log('用户点击取消')
                    }
                }
            });
        }
    },

    // 获取check值
    radioChange(e) {
        console.log('radio发生change事件，携带value值为：', e.detail.value)
        var current = "";
        if (e.detail.value == 0) {
            var currentIdx = 0;
            current = "A";
        } else if (e.detail.value == 1) {
            var currentIdx = 1;
            current = "B";
        }
        this.setData({
            current: current,
            currentIdx: currentIdx
        })

    }
})