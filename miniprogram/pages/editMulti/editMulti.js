// pages/editMulti/editMulti.js
var app = getApp();
Page({
  data: {
    index: null,
    picker: ['1', '2', '3','4','5'],
    opt:2,
    optList:[
      { idx: 0, detail:""},
      { idx: 1, detail: ""},
    ]
  },

  PickerChange(e) {
    console.log(e);
    this.setData({
      index: e.detail.value
    })
  },

  // 获取选项内容
  optDetail: function (e) {
    // idx start from 0
    var idx = e.currentTarget.dataset.idx;
    var optList = this.data.optList;
    var details=e.detail.value;

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
  addOpt:function(e){
    var opt=this.data.opt+1;
    var optList=this.data.optList;
    if (opt<5){
      optList.push({
        idx: opt-1, detail:"",
      });
      this.setData({
        opt: opt,
        optList:optList
      });
    }
    else{
      // 弹窗
      wx.showToast({
        title: '选项不超过4个！', // 标题
        duration: 1500 // 提示窗停留时间，默认1500ms
      })
    }
    console.log(opt)
  },

  // 删除选项
  deleteOpt:function(e){
    var opts = this.data.opt;
    if (opts < 3) {
      // 弹窗
      wx.showToast({
        title: '至少2个选项！', // 标题
        duration: 1500 // 提示窗停留时间，默认1500ms
      })
    }
    else{
      var that =this;
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
            var opt=that.data.opt-1;
            that.setData({
              optList: filterRes,
              opt:opt,
            });
            that.onLoad();
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      });
    }
  },

  // 提交单选题
  confirm:function(e){
    // 创建一个数组，bindChange 改变的时候改变数组的值，判断current
    var that = this;
    // 发起网络请求
    wx.request({
      url: that.data.addQuestionUrl,
      method: "post",
      header: {
        "token": that.data.token
      },
      data:{
        title:that.data.title, //待修改
        score:that.data.index,
        type:2,
        current:"A",
        answerA:"AXDV64",// 获取数组的值
        answerB:"AXDV64",
        answerC:"AXDV64",
        answerD:"AXDV64",
        examId:"",
        questionId:"",
      },
      success: function (res) {
        if(res){
          wx.navigateTo({
            url:"../editPaper/editPaper"
          })
        }
        else{
          console.log(res.msg);
        }
      },
      fail: function (error) {
        console.log(error);
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var addQuestionUrl = app.globalData.url + "exam/addQuestion";
    this.setData({
      addQuestionUrl: addQuestionUrl,
    });
  },

})