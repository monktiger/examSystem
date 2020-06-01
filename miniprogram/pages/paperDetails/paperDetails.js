// pages/paperDetails/paperDetails.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    btnName:'',
    status:'20002',
    sum:0
  },
  goPaper(e){
    app.globalData.copyId=this.data.paperDetails.copyId;
    app.globalData.questionList=this.data.paperDetails.questionList;
    wx.navigateTo({
      url:'/pages/paper/paper'
    })
  },
  formatDate(now) { 
    var year=now.getFullYear();  //取得4位数的年份
    var month=now.getMonth()+1;  //取得日期中的月份，其中0表示1月，11表示12月
    var date=now.getDate();      //返回日期月份中的天数（1到31）
    var hour=now.getHours();     //返回日期中的小时数（0到23）
    var minute=now.getMinutes(); //返回日期中的分钟数（0到59）
    var second=now.getSeconds(); //返回日期中的秒数（0到59）
    if(month<'10') month='0'+month;
    if(date<'10') date='0'+date;
    if(hour<'10') hour='0'+hour;
    if(minute<'10') minute='0'+minute;
    if(second<'10') second='0'+second;
    return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
},
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    let status = app.globalData.stuStatus
    console.log(status);
    if(status=='20002'){
      wx.request({
        url: 'http://monktiger.natapp1.cc/in/toCopy',
        method: 'GET',
        data: {
          examId:app.globalData.examId
        },
        header: {
          "token": app.globalData.token
        },
        success: function (result) {
          console.log(result.data);
          let paperDetails=result.data;
          let start = new Date(paperDetails.beginTime);
          let end = new Date(paperDetails.endTime);
          paperDetails.beginTime=that.formatDate(start);
          paperDetails.endTime=that.formatDate(end);
          if(paperDetails.questionList){
            that.setData({
              status:status,
              paperDetails:paperDetails,
              btnName:'开始答题',
              length:paperDetails.questionList.length
            })
          }
        }, fail(e) {
          console.log(e);
        }
      })
    }else if(status=='20003'){
      console.log("ddd");
      
      wx.request({
        url: 'http://monktiger.natapp1.cc/in/toWrongBook',
        method: 'GET',
        data: {
          examId:app.globalData.examId
        },
        header: {
          "token": app.globalData.token
        },
        success: function (result) {
          console.log(result);
          let paperDetails=result.data;
          let i;
          let sum=0;
          let score=0;
          for(i=0;i<paperDetails.questionList.length;i++){
            sum = sum + paperDetails.questionList[i].getScore;
            score = score + paperDetails.questionList[i].score;
          }
          that.setData({
            status:status,
            paperDetails:paperDetails,
            btnName:'查看试卷',
            length:paperDetails.questionList.length,
            judge:paperDetails.judge,
            sum:sum,
            score:score
          })
        }, fail(e) {
          console.log(e);
        }
      })
      that.setData({
        status:status,
        btnName:'查看试卷'
      })
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})