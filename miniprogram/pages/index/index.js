// components/index/index.js
const app = getApp()
Component({
    /**
     * 组件的属性列表
     */
    properties: {},

    /**
     * 组件的初始数据
     */
    data: {
        TabCur: 0,
        scrollLeft: 0,
        color: ['cyan', 'blue', 'pink', 'green'],
        title: ["我管理的组", "我加入的组"],
        elements: [],
    },
    attached: function() {
        let that = this
        this.setData({
                token: app.globalData.token
            })
            // type 0自己创建的组
            // type 1自己加入的组
            // 获得组列表
        wx.request({
            url: 'http://monktiger.natapp1.cc/group/getList',
            method: 'GET',
            data: {
                type: that.data.TabCur
            },
            header: {
                "token": that.data.token
            },
            success: function(result) {
                console.log(result);
                let elements = result.data.groups;
                let color = that.data.color;
                let i;
                if (elements) {
                    for (i = 0; i < elements.length; i++) {
                        elements[i].color = color[i % 4];
                    }
                }
                that.setData({
                    elements: elements,
                })


            },
            fail(e) {
                console.log(e);

            }
        })
    },
    /**
     * 组件的方法列表
     */
    methods: {
        // 去组页面
        clickCard(e) {
            if (e.detail.TabCur == 0) {
                wx.navigateTo({
                    url: '/pages/manageGroup/manageGroup',
                })
            } else {
                wx.navigateTo({
                    url: '/pages/joinedGroup/joinedGroup',
                })
            }
        },
        // 导航栏
        tabSelect(e) {
            let that = this
            app.globalData.pageId = e.currentTarget.dataset.id;
            // 根据id获取组列表
            // type 0自己创建的组
            // type 1自己加入的组
            wx.request({
                url: 'http://monktiger.natapp1.cc/group/getList',
                method: 'GET',
                data: {
                    type: e.currentTarget.dataset.id
                },
                header: {
                    "token": that.data.token
                },
                success: function(result) {
                    console.log(result);
                    let elements = result.data.groups;
                    let color = that.data.color;
                    let i;
                    // console.log(elements.length);
                    if (elements) {
                        for (i = 0; i < elements.length; i++) {
                            elements[i].color = color[i % 4];
                        }
                    }
                    that.setData({
                        elements: elements,
                        TabCur: e.currentTarget.dataset.id,
                    })
                },
                fail(e) {
                    console.log(e);

                }
            })
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
        creatInput(e) {
            this.setData({
                creatInput: e.detail.value
            })
        },
        addInput(e) {
            this.setData({
                addInput: e.detail.value
            })
        },
        // 创建组
        create(e) {
            let that = this;
            console.log(this.data.creatInput);
            wx.request({
                url: 'http://monktiger.natapp1.cc/group/create',
                method: 'GET',
                data: {
                    name: that.data.creatInput
                },
                header: {
                    // "Content-Type": "application/x-www-form-urlencoded",
                    "token": that.data.token
                },
                success: function(result) {
                    console.log(result);
                    wx.request({
                        url: 'http://monktiger.natapp1.cc/group/getList',
                        method: 'GET',
                        data: {
                            type: that.data.TabCur
                        },
                        header: {
                            "token": that.data.token
                        },
                        success: function(result) {
                            console.log(result);
                            that.setData({
                                elements: result.data.groups,
                            })
                        },
                        fail(e) {
                            console.log(e);
                        }
                    })
                },
                fail(e) {
                    console.log(e);

                }
            })
        },
        // 加入组
        add(e) {
            let that = this;
            console.log(this.data.addInput);

            console.log(this.data.token);
            wx.request({
                url: 'http://monktiger.natapp1.cc/group/join',
                method: 'GET',
                data: {
                    groupId: that.data.addInput
                },
                header: {
                    // "Content-Type": "application/x-www-form-urlencoded",
                    "token": that.data.token
                },
                success: function(result) {
                    console.log(result);
                },
                fail(e) {
                    console.log(e);
                }
            })
        },
        // 解散组
        delete(e) {
            let that = this;
            console.log(e.detail.groupid);
            wx.request({
                url: 'http://monktiger.natapp1.cc/group/quit',
                method: 'GET',
                data: {
                    groupId: e.detail.groupid
                },
                header: {
                    // "Content-Type": "application/x-www-form-urlencoded",
                    "token": that.data.token
                },
                success: function(result) {
                    console.log(result);
                    let elements = that.data.elements;
                    elements.splice(e.detail.index, 1)
                },
                fail(e) {
                    console.log(e);
                }
            })
        }
    }
})