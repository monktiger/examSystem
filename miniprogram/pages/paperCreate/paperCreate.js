// pages/paperCreate/paperCreate.js
var app = getApp();
var date = new Date();
var currentHours = date.getHours();
var currentMinute = date.getMinutes();
Page({
  data: {
<<<<<<< Updated upstream
    startDate: "请选择日期",
    endDate: "请选择日期",
    multiArray: [['今天', '明天', '3-2', '3-3', '3-4', '3-5'], [0, 1, 2, 3, 4, 5, 6], [0, 10, 20]],
    multiIndex: [0, 0, 0],
  },

  examName: function(e){
    this.setData({
      examName: e.detail.value
    })
    // console.log("examName",e.detail.value);
  },

  detail: function(e) {
=======
  },

  examName: function (e) {
    this.setData({
      examName: e.detail.value
    })
    console.log("examName", e.detail.value);
  },

  detail: function (e) {
>>>>>>> Stashed changes
    this.setData({
      detail: e.detail.value
    })
  },

<<<<<<< Updated upstream
  createPaper: function(e){
    var that = this;
    var groupId = [this.data.groupId];
    var data = {
      name: this.data.examName,
      groupId: groupId,
      beginTime: "2020-06-01 10:00:00",
      endTime: "2020-07-01 10:00:00"
      // beginTime: "1591002038",
      // endTime: "1591866038"
      // beginTime: this.Convert.ToDateTime("2020-02-02 11:00:00"),
      // endTime: this.Convert.ToDateTime("2020-02-20 11:00:00"),
      // examId: examId,
    }
    // 发起网络请求
    wx.request({
      url: that.data.createExamUrl,
      method: "post",
      header: {
        "token": app.globalData.token,
        "Content-Type": "application/json",
      },
      data: JSON.stringify(data),
      success: function (res) {
        console.log(res);
        if(res.data.status == 1 ){
          app.globalData.examName=that.data.examName;
          app.globalData.beginTime=that.data.beginTime;
          app.globalData.endTime=that.data.endTime;
          wx.navigateTo({
            url: "../editPaper/editPaper"
          })
        } else {
          console.log(res.data.info);
        }
      },
      fail: function (error) {
        console.log(error);
      }
    })
=======
  createPaper: function (e) {
    var that = this;
    var groupId = [this.data.groupId];
    var examId = this.data.examId;
    var name = this.data.examName;
    var beginTime = app.globalData.startDateTime;
    var endTime = app.globalData.endDateTime;
    if(!name||!beginTime||!endTime){
      wx.showToast({
        title: '请填写所有试卷信息！', // 标题
        icon: 'none', // 图标类型，默认success
        duration: 1500 // 提示窗停留时间，默认1500ms
      })
    } else{
      var data = {
        name: name,
        groupId: groupId,
        beginTime: beginTime,
        endTime: endTime,
        examId: examId||"",
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
        success: function (res) {
          console.log(res);
          if (res.data.status == 1) {
            app.globalData.examName = name;
            app.globalData.beginTime = beginTime;
            app.globalData.endTime = endTime;
            app.globalData.examId = res.data.examId;
            wx.navigateTo({
              url: "../editPaper/editPaper"
            })
          } else if(res.data.status == -1){
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
>>>>>>> Stashed changes
  },

  onLoad: function (options) {
    var createExamUrl = app.globalData.url + "exam/createExam";
    this.setData({
      createExamUrl: createExamUrl,
<<<<<<< Updated upstream
      groupId:app.globalData.groupId
    });
    console.log("app.globalData.groupId",app.globalData.groupId)
  },

  pickerTap: function () {
    date = new Date();

    var monthDay = ['今天', '明天'];
    var hours = [];
    var minute = [];

    currentHours = date.getHours();
    currentMinute = date.getMinutes();

    // 月-日
    for (var i = 2; i <= 28; i++) {
      var date1 = new Date(date);
      date1.setDate(date.getDate() + i);
      var md = (date1.getMonth() + 1) + "-" + date1.getDate();
      monthDay.push(md);
    }

    var data = {
      multiArray: this.data.multiArray,
      multiIndex: this.data.multiIndex
    };

    if (data.multiIndex[0] === 0) {
      if (data.multiIndex[1] === 0) {
        this.loadData(hours, minute);
      } else {
        this.loadMinute(hours, minute);
      }
    } else {
      this.loadHoursMinute(hours, minute);
    }

    data.multiArray[0] = monthDay;
    data.multiArray[1] = hours;
    data.multiArray[2] = minute;

    return data;
  },

  pickerTapStart:function(e){
    var startData = this.pickerTap();
    console.log(startData);
    this.setData(startData);
  },

  // pickerTapEnd: function () {
  //   date = new Date();

  //   var monthDay = ['今天', '明天'];
  //   var hours = [];
  //   var minute = [];

  //   currentHours = date.getHours();
  //   currentMinute = date.getMinutes();

  //   // 月-日
  //   for (var i = 2; i <= 28; i++) {
  //     var date1 = new Date(date);
  //     date1.setDate(date.getDate() + i);
  //     var md = (date1.getMonth() + 1) + "-" + date1.getDate();
  //     monthDay.push(md);
  //   }

  //   var dataEnd = {
  //     multiArray: this.dataEnd.multiArray,
  //     multiIndex: this.dataEnd.multiIndex
  //   };

  //   if (dataEnd.multiIndex[0] === 0) {
  //     if (dataEnd.multiIndex[1] === 0) {
  //       this.loadData(hours, minute);
  //     } else {
  //       this.loadMinute(hours, minute);
  //     }
  //   } else {
  //     this.loadHoursMinute(hours, minute);
  //   }

  //   dataEnd.multiArray[0] = monthDay;
  //   dataEnd.multiArray[1] = hours;
  //   dataEnd.multiArray[2] = minute;

  //   this.setData(dataEnd);
  // },

  bindMultiPickerColumnChange: function (e) {
    date = new Date();

    var that = this;

    var monthDay = ['今天', '明天'];
    var hours = [];
    var minute = [];

    currentHours = date.getHours();
    currentMinute = date.getMinutes();

    var data = {
      multiArray: this.data.multiArray,
      multiIndex: this.data.multiIndex
    };
    // 把选择的对应值赋值给 multiIndex
    data.multiIndex[e.detail.column] = e.detail.value;

    // 然后再判断当前改变的是哪一列,如果是第1列改变
    if (e.detail.column === 0) {
      // 如果第一列滚动到第一行
      if (e.detail.value === 0) {

        that.loadData(hours, minute);

      } else {
        that.loadHoursMinute(hours, minute);
      }

      data.multiIndex[1] = 0;
      data.multiIndex[2] = 0;

      // 如果是第2列改变
    } else if (e.detail.column === 1) {

      // 如果第一列为今天
      if (data.multiIndex[0] === 0) {
        if (e.detail.value === 0) {
          that.loadData(hours, minute);
        } else {
          that.loadMinute(hours, minute);
        }
        // 第一列不为今天
      } else {
        that.loadHoursMinute(hours, minute);
      }
      data.multiIndex[2] = 0;

      // 如果是第3列改变
    } else {
      // 如果第一列为'今天'
      if (data.multiIndex[0] === 0) {

        // 如果第一列为 '今天'并且第二列为当前时间
        if (data.multiIndex[1] === 0) {
          that.loadData(hours, minute);
        } else {
          that.loadMinute(hours, minute);
        }
      } else {
        that.loadHoursMinute(hours, minute);
      }
    }
    data.multiArray[1] = hours;
    data.multiArray[2] = minute;
    this.setData(data);
  },

  loadData: function (hours, minute) {

    var minuteIndex;
    if (currentMinute > 0 && currentMinute <= 10) {
      minuteIndex = 10;
    } else if (currentMinute > 10 && currentMinute <= 20) {
      minuteIndex = 20;
    } else if (currentMinute > 20 && currentMinute <= 30) {
      minuteIndex = 30;
    } else if (currentMinute > 30 && currentMinute <= 40) {
      minuteIndex = 40;
    } else if (currentMinute > 40 && currentMinute <= 50) {
      minuteIndex = 50;
    } else {
      minuteIndex = 60;
    }

    if (minuteIndex == 60) {
      // 时
      for (var i = currentHours + 1; i < 24; i++) {
        hours.push(i);
      }
      // 分
      for (var i = 0; i < 60; i += 10) {
        minute.push(i);
      }
    } else {
      // 时
      for (var i = currentHours; i < 24; i++) {
        hours.push(i);
      }
      // 分
      for (var i = minuteIndex; i < 60; i += 10) {
        minute.push(i);
      }
    }
  },

  loadHoursMinute: function (hours, minute) {
    // 时
    for (var i = 0; i < 24; i++) {
      hours.push(i);
    }
    // 分
    for (var i = 0; i < 60; i += 10) {
      minute.push(i);
    }
  },

  loadMinute: function (hours, minute) {
    var minuteIndex;
    if (currentMinute > 0 && currentMinute <= 10) {
      minuteIndex = 10;
    } else if (currentMinute > 10 && currentMinute <= 20) {
      minuteIndex = 20;
    } else if (currentMinute > 20 && currentMinute <= 30) {
      minuteIndex = 30;
    } else if (currentMinute > 30 && currentMinute <= 40) {
      minuteIndex = 40;
    } else if (currentMinute > 40 && currentMinute <= 50) {
      minuteIndex = 50;
    } else {
      minuteIndex = 60;
    }

    if (minuteIndex == 60) {
      // 时
      for (var i = currentHours + 1; i < 24; i++) {
        hours.push(i);
      }
    } else {
      // 时
      for (var i = currentHours; i < 24; i++) {
        hours.push(i);
      }
    }
    // 分
    for (var i = 0; i < 60; i += 10) {
      minute.push(i);
    }
  },

  bindStartMultiPickerChange: function (e) {
    var that = this;
    var monthDay = that.data.multiArray[0][e.detail.value[0]];
    var hours = that.data.multiArray[1][e.detail.value[1]];
    var minute = that.data.multiArray[2][e.detail.value[2]];
    var myDate = new Date();
    var year = myDate.getFullYear();
    var timeStr = '';// 日期字符串

    if (monthDay === "今天") {
      var month = date.getMonth() + 1;
      var day = date.getDate();
      monthDay = month + "月" + day + "日";
      timeStr = year + "/" + month + "/" + day + " " + hours + ":" + minute;
    } else if (monthDay === "明天") {
      var date1 = new Date(date);
      date1.setDate(date.getDate() + 1);
      monthDay = (date1.getMonth() + 1) + "月" + date1.getDate() + "日";
      timeStr = year + "/" + month + "/" + date1 + " " + hours + ":" + minute;
    } else {
      var month = monthDay.split("-")[0]; // 返回月
      var day = monthDay.split("-")[1]; // 返回日
      monthDay = month + "月" + day + "日";
      timeStr = year + "/" + month + "/" + day + " " + hours + ":" + minute;
    }

    var timeDate = new Date(timeStr);// 构造一个日期型数据，值为传入的字符串
    var time = timeDate.getTime();// 得到时间戳

    var startDate = monthDay + " " + hours + ":" + minute;
    that.setData({
      startDate: startDate,
      time: time,
    })
  },
=======
      groupId: app.globalData.groupId,
      examId:app.globalData.examId,
      examName:app.globalData.examName,
    });
  },

 

>>>>>>> Stashed changes
})