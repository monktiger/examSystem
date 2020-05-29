// pages/editSingle/editSingle.js
// 未完成部分：
// 获取正确答案，
// 不知道是否能获取选项的内容
// title怎么传过来父组件
var app = getApp();
Page({
    data: {
        index: null,
        picker: ['1', '2', '3', '4', '5'],
        opt: 2,
        optList: [
            { idx: 0, detail: "" },
            { idx: 1, detail: "" },
        ],
        //>>>>>>>>>>尝试<<<<<<<<<<<
        array: [0], //默认显示一个
        inputVal: [], //所有input的内容

        //样例
        single: {
            "title": "这是一道多选题",
            "score": 20,
            "type": 2,
            "current": "AB",
            "answerA": "选项a",
            "answerB": "选项b",
            "answerC": "选项c",
            "answerD": "选项d"
        },


    },

    PickerChange(e) {
        console.log(e);
        this.setData({
            index: e.detail.value
        })
    },

    // 获取选项内容
    optDetail: function(e) {
        // idx start from 0
        var idx = e.currentTarget.dataset.idx;
        var optList = this.data.optList;
        var details = e.detail.value;

        //1. 得到对象
        var oj = optList.filter((p) => {
            return p.idx == idx;
        });

        //2. 修改数据
        oj[0].detail = details;

        this.setData({
            optList: optList,
        });
    },

    // 添加选项
    addOpt: function(e) {
        var opt = this.data.opt + 1;
        var optList = this.data.optList;
        if (opt < 5) {
            optList.push({
                idx: opt - 1,
                detail: "",
            });
            this.setData({
                opt: opt,
                optList: optList
            });
        } else {
            // 弹窗
            wx.showToast({
                title: '选项不超过4个！', // 标题
                duration: 1500 // 提示窗停留时间，默认1500ms
            })
        }
        console.log(opt)
    },

    // 删除选项
    deleteOpt: function(e) {
        var opts = this.data.opt;
        if (opts < 3) {
            // 弹窗
            wx.showToast({
                title: '至少2个选项！', // 标题
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
                        let idx = e.currentTarget.dataset.idx;
                        let list = that.data.optList;
                        let filterRes = list.filter((index) => {
                            return index != idx;
                        });
                        var opt = that.data.opt - 1;
                        that.setData({
                            optList: filterRes,
                            opt: opt,
                        });
                        that.onLoad();
                    } else if (res.cancel) {
                        console.log('用户点击取消')
                    }
                }
            });
        }
    },

    // // 提交单选题
    // confirm: function (e) {
    //   // 创建一个数组，bindChange 改变的时候改变数组的值，判断current
    //   // 类比豆瓣打分 设置[0,0,0,0] 点击就翻转？
    //   var that = this;
    //   var title = wx.getStorageSync("title");
    //   var score = parseInt(this.data.index) + 1;
    //   // 发起网络请求
    //   wx.request({
    //     url: that.data.addQuestionUrl,
    //     method: "post",
    //     header: {
    //       "token": app.globalData.token
    //     },
    //     data: 
    //     // {
    //       JSON.stringify(that.data.single)
    //       // title: title, //待修改
    //       // score: score,
    //       // type: 1,
    //       // current: "A",
    //       // answerA: "AXDV64",// 获取数组的值
    //       // answerB: "AXDV64",
    //       // answerC: "AXDV64",
    //       // answerD: "AXDV64",
    //       // examId: "",
    //       // questionId: "",
    //     // }
    //     ,
    //     success: function (res) {
    //       if (res) {
    //         // 设置题目缓存
    //         var singleQues = wx.getStorageSync('single_ques');
    //         var arr = []
    //         for (let i in singleQues) {
    //           let o = {};
    //           o[i] = singleQues[i];
    //           arr.push(o[i])
    //         }
    //         arr.push({
    //           title: title,
    //           score: score,
    //           current: "A",
    //           answerA: "AXDV64",// 获取数组的值
    //           answerB: "AXDV64",
    //           answerC: "AXDV64",
    //           answerD: "AXDV64",
    //         })
    //         wx.removeStorage({
    //           key: 'single_ques',
    //           success(res) {
    //             console.log(res)
    //           }
    //         })
    //         console.log("arr:", arr);
    //         wx.setStorageSync('single_ques', arr);
    //         wx.navigateTo({
    //           url: "../editPaper/editPaper"
    //         })
    //       }
    //       else {
    //         console.log(res.msg);
    //       }
    //     },
    //     fail: function (error) {
    //       console.log(error);
    //     }
    //   })
    // },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        var addQuestionUrl = app.globalData.url + "exam/addQuestion";
        this.setData({
            addQuestionUrl: addQuestionUrl,
        });
    },

    // >>>>>>>>>>>尝试动态添加删除input<<<<<<<<<<<<<<

    // 提交单选题
    confirm: function(e) {
        var that = this;
        var title = wx.getStorageSync("title");
        var score = parseInt(this.data.index) + 1;
        var data = {

            }
            // 发起网络请求
        wx.request({
            url: that.data.addQuestionUrl,
            method: "post",
            header: {
                "token": app.globalData.token,
                "Content-Type": "application/json",
            },
            data: JSON.stringify(that.data.single),
            success: function(res) {
                if (res) {
                    // 设置题目缓存
                    var singleQues = wx.getStorageSync('single_ques');
                    var arr = []
                    for (let i in singleQues) {
                        let o = {};
                        o[i] = singleQues[i];
                        arr.push(o[i])
                    }
                    arr.push({
                        title: title,
                        score: score,
                        current: "A",
                        answerA: "AXDV64", // 获取数组的值
                        answerB: "AXDV64",
                        answerC: "AXDV64",
                        answerD: "AXDV64",
                    })
                    wx.removeStorage({
                        key: 'single_ques',
                        success(res) {
                            console.log(res)
                        }
                    })
                    console.log("arr:", arr);
                    wx.setStorageSync('single_ques', arr);
                    wx.navigateTo({
                        url: "../editPaper/editPaper"
                    })
                } else {
                    console.log(res.msg);
                }
            },
            fail: function(error) {
                console.log(error);
            }
        })
    },

    //获取input的值
    getInputVal: function(e) {
        var nowIdx = e.currentTarget.dataset.idx; //获取当前索引
        var val = e.detail.value; //获取输入的值
        var oldVal = this.data.inputVal;
        oldVal[nowIdx] = val; //修改对应索引值的内容
        this.setData({
            inputVal: oldVal
        })
    },

    //添加input
    addInput: function() {
        var old = this.data.array;
        old.push(1); //这里不管push什么，只要数组长度增加1就行
        this.setData({
            array: old
        })
    },

    //删除input
    delInput: function(e) {
        var nowidx = e.currentTarget.dataset.idx; //当前索引
        var oldInputVal = this.data.inputVal; //所有的input值
        var oldarr = this.data.array; //循环内容
        oldarr.splice(nowidx, 1); //删除当前索引的内容，这样就能删除view了
        oldInputVal.splice(nowidx, 1); //view删除了对应的input值也要删掉
        if (oldarr.length < 1) {
            oldarr = [0] //如果循环内容长度为0即删完了，必须要留一个默认的。这里oldarr只要是数组并且长度为1，里面的值随便是什么
        }
        this.setData({
            array: oldarr,
            inputVal: oldInputVal
        })
    }
})