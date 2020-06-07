// pages/editMulti/editMulti.js
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
            examId: app.globalData.examId
        });

        // 如果有editQueNum则是修改题目：先取出原数据，赋值
        var editQueNum = app.globalData.editQueNum;
        if (editQueNum || editQueNum == 0) {
            var editques = wx.getStorageSync('multi_ques');
            var editData = editques[editQueNum].data;
            var current = editData.current;
            var score = editData.score;
            var queId = editData.id;
            wx.setStorageSync("title", editData.title);
            var checkedArr = [];
            if (current.indexOf('A') != -1) {
                checkedArr.push(true)
            } else {
                checkedArr.push(false)
            }
            if (current.indexOf('B') != -1) {
                checkedArr.push(true)
            } else {
                checkedArr.push(false)
            }
            if (current.indexOf('C') != -1) {
                checkedArr.push(true)
            } else {
                checkedArr.push(false)
            }
            if (current.indexOf('D') != -1) {
                checkedArr.push(true)
            } else {
                checkedArr.push(false)
            }
            console.log("checkedArr", checkedArr)
            this.setData({
                inputVal: [editData.answerA, editData.answerB, editData.answerC || "", editData.answerD || ""],
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
        // wx.redirectTo({
        //     url: "/pages/editPaper/editPaper"
        // })
        wx.navigateBack({
            delta:1
        })
    },

    // 提交多选题
    confirm: function (e) {
        wx.showLoading({
            title: '加载中...',
        })
        var that = this;
        var title = wx.getStorageSync("title");
        var score = parseInt(this.data.index) + 1;
        var answerA = this.data.inputVal[0];
        var answerB = this.data.inputVal[1];
        var answerC = this.data.inputVal[2];
        var answerD = this.data.inputVal[3];
        var current = this.data.current;
        var data = {
            "title": title,
            "score": score,
            "type": 2,
            "current": current,
            "answerA": answerA,
            "answerB": answerB,
            "answerC": answerC,
            "answerD": answerD,
            "examId": this.data.examId,
            "id": this.data.queId || ""
        };
        // 判断选项是否重复
        var isflag = true; //没有重复
        var array = this.data.inputVal;
        for (var i = 0; i < array.length; i++) {
            for (var j = i + 1; j < array.length; j++) {
                if (array[i] != "" && array[i] == array[j]) {
                    isflag = false; //有重复
                    break;
                }
            }
        }
        if (!title || !score || !answerA || !answerB || !current) {
            wx.showToast({
                title: '请填写所有题目信息！', // 标题
                icon: 'none', // 图标类型，默认success
                duration: 1500 // 提示窗停留时间，默认1500ms
            })
        } else if (isflag == false) {
            wx.showToast({
                title: '请检查是否有相同选项！', // 标题
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
                        var multiQues = wx.getStorageSync('multi_ques');
                        var arr = []
                        for (let i in multiQues) {
                            let o = {};
                            o[i] = multiQues[i];
                            arr.push(o[i])
                        }
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
                            key: 'multi_ques',
                            success(res) {
                                console.log(res)
                            }
                        })
                        console.log("arr:", arr);
                        wx.setStorageSync('multi_ques', arr);
                        wx.setStorageSync('title', "");
                        if (app.globalData.isEdit) {
                            // wx.redirectTo({
                            //     url: "../paper/paper"
                            // })
                            wx.navigateBack({
                                delta:1
                            })
                            app.globalData.isEdit = 1
                        } else {
                            // wx.redirectTo({
                            //     url: "../editPaper/editPaper"
                            // })
                            wx.navigateBack({
                                delta:1
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
                        if (oldarr.length < 1) {
                            oldarr = [0] //如果循环内容长度为0即删完了，必须要留一个默认的。这里oldarr只要是数组并且长度为1，里面的值随便是什么
                        }
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
        console.log('radio发生change事件，携带value值为：', e)
        var currentIdx = [];
        var current = "";
        const items = this.data.inputVal
        for (let i = 0, len = items.length; i < len; ++i) {
            for (let j = 0, valLen = e.detail.value.length; j < valLen; ++j) {
                if (items[i] === e.detail.value[j]) {
                    currentIdx.push(i);
                }
            }
        }
        console.log("currentIdx", currentIdx.includes(0));
        if (currentIdx.includes(0)) {
            current = current + "A"
        }
        if (currentIdx.includes(1)) {
            current = current + "B"
        }
        if (currentIdx.includes(2)) {
            current = current + "C"
        }
        if (currentIdx.includes(3)) {
            current = current + "D"
        }
        this.setData({
            current: current,
            currentIdx: currentIdx
        })
        console.log(current);
    },

})