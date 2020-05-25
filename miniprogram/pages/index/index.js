// components/index/index.js
const app = getApp()
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    // TabCur:0
  },

  /**
   * 组件的初始数据
   */
  data: {
    TabCur: 0,
    scrollLeft: 0,
    title: ["我管理的组", "我加入的组"],
    elements: [{
      title: '高三数学特训',
      name: 'GRPUP',
      color: 'cyan',
      icon: 'newsfill'
    },
    {
      title: '司法考试',
      name: 'GRPUP',
      color: 'blue',
      icon: 'newsfill'
    },
    ],
  },
  attached: function() {
    let that=this
    this.setData({
      token:app.globalData.token
    })
    wx.request({
      url: 'http://monktiger.natapp1.cc/:group/getList',
      method: 'GET',
      data: {
      },
      header: {
        "token":that.data.token
      },
      success: function (result) {
        console.log(result);
        this.setData({
          elements: result
        })
        
      }, fail(e) {
        console.log(e);

      }
    })
  },
  /**
   * 组件的方法列表
   */
  methods: {
    // 去组页面
    clickCard(e){
      if(e.detail.TabCur==0){
        wx.navigateTo({
          url: '/pages/manageGroup/manageGroup',
        })
      }else{
        wx.navigateTo({
          url: '/pages/joinedGroup/joinedGroup',
        })
      }
    },
  // 导航栏
  tabSelect(e) {
    let that = this
    app.globalData.pageId=e.currentTarget.dataset.id;
    if (e.currentTarget.dataset.id == 0) {
      // console.log(e.currentTarget.dataset.id);
      
      this.setData({
        TabCur: e.currentTarget.dataset.id,
        elements: [{
          title: '高三数学特训',
          name: 'GRPUP',
          color: 'cyan',
          icon: 'newsfill'
        },
        {
          title: '司法考试',
          name: 'GRPUP',
          color: 'blue',
          icon: 'newsfill'
        },
        ],
      })
    } else {
      // console.log(e.currentTarget.dataset.id);
      this.setData({
        TabCur: e.currentTarget.dataset.id,
        elements: [{
          title: '大一高数考试',
          name: 'GRPUP',
          color: 'pink',
          icon: 'newsfill'
        },
        {
          title: '大二',
          name: 'GRPUP',
          color: 'green',
          icon: 'newsfill'
        },
        ],
      })
    }
  },
  // 模态窗
  showModal(e) {
    this.setData({
      modalName: e.currentTarget.dataset.target
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  creatInput(e){
    this.setData({
      creatInput: e.detail.value
    })
  },
  addInput(e){
    this.setData({
      addInput: e.detail.value
    })
  },
  // 创建组
  create(e){
    let that = this;
    console.log(this.data.creatInput);
    
    wx.request({
      url: 'http://monktiger.natapp1.cc/group/create',
      method: 'GET',
      data: {
        name:that.data.creatInput
      },
      header: {
        // "Content-Type": "application/x-www-form-urlencoded",
        "token":that.data.token
      },
      success: function (result) {
        console.log(result);
        
      }, fail(e) {
        console.log(e);

      }
    })
  },
  // 加入组
  add(e){
    let that = this;
    console.log(this.data.creatInput);
    wx.request({
      url: 'http://monktiger.natapp1.cc/group/join',
      method: 'GET',
      data: {
        groupId:that.data.addInput
      },
      header: {
        // "Content-Type": "application/x-www-form-urlencoded",
        "token":that.data.token
      },
      success: function (result) {
        console.log(result);
        
      }, fail(e) {
        console.log(e);

      }
    })
  },
  // 退出组
  quit(e){

  },
  // 解散组
  delete(e){

  }
  }
})
