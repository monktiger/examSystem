<template>
  <span class="pic">
    <div class="search">
      <el-input v-model="searchData" placeholder="请输入内容" size="mini"></el-input>
      <i class="el-icon-search" @click="search"></i>
    </div>
    <el-table :data="tableData" style="width: 100%" border>
      <el-table-column prop="name" label="名字"></el-table-column>
      <el-table-column prop="nickname" label="昵称"></el-table-column>
      <el-table-column prop="open_id" label="微信openid"></el-table-column>
      <el-table-column prop="isUser" label="是否被禁用"></el-table-column>
      <el-table-column label="操作" width="200" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="primary"
            icon="el-icon-edit"
            plain
            @click="handleEdit(scope.$index, scope.row)"
          ></el-button>

          <el-button
            size="small"
            type="danger"
            icon="el-icon-remove-outline"
            plain
            @click="handleDelete(scope.$index, scope.row)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 表单弹出窗 -->
    <el-dialog title="修改" :visible.sync="dialogFormVisible" center width="350px">
      <el-form :model="form">
        <el-form-item label="组名">
          <el-input v-model="form.name" auto-complete="off" style="margin-left:10px;width:200px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </div>
    </el-dialog>
    <div class="block pagination">
      <el-pagination
      background
        @prev-click="prevClick"
        @next-click="nextClick"
        :current-page="pageNum"
        :hide-on-single-page="(tableData.length<8?true:false)"
        layout="total,prev, pager, next"
        :page-size="15"
        :total="total"
        @current-change="handlePageChange"
      ></el-pagination>
    </div>
  </span>
</template>
<script>
import axios from "axios";
import { getUser, deleteUser, editeUser } from "../api/temp"; //写调用的接口
export default {
  data() {
    return {
      dialogFormVisible: false, //弹窗
      searchValue: "url",
      maxlength: 6,
      tableData: [], //弹出框的标题与上传
      searchData: "", //搜索框
      pageNum: 1, //现在第几页
      pages: 1, //总共多少页
      total: 1, //总共多少条
      form: {
        name: "", //组名
        openId: "" //管理员的id
      }
    };
  },
  //页面开始之前网络请求
  created: function() {
          let data = {
        pageNum: this.pageNum
      };
    this.getUser(data);
  },
  methods: {
    // 分页
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    prevClick(){

    },
    nextClick(){

    },
    handlePageChange(){

    },
    // 获取组
    getUser(data) {

      getUser(data).then(res => {
        console.log(res);
         for (let i = 0; i < res.data.userList.length; i++) {
           if(res.data.userList[i].available){
             res.data.userList[i].isUser="正常"
           }else{
             res.data.userList[i].isUser="被禁用"
           }
        }
        this.tableData = res.data.userList;
        this.pages = res.data.pages;
        this.total = res.data.total;
        this.pageNum = res.data.pageNum;
        console.log(res.data.groupList);
      });
    },
    search() {
      let tag = {
        search:this.searchData,
        pageNum:1
      };
      this.getUser(tag)
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    //文件上传之前的钩子函数，上传前对其文件的大小和类型进行判断
    beforeUpload(file) {
      const isSize = file.size / 1024 / 1024 < 1;
      if (!isSize) {
        this.$message({
          message: "上传的文件不能超过1MB",
          type: "error"
        });
      }
      console.log(file);
      return isSize;
    },
    // 编辑按钮
    handleEdit(index,row) {
      console.log(row);
      this.form.name = row.name;
      this.form.openId = row.openId;
      this.dialogFormVisible = true;
    },
    //确定按钮
    confirm() {
      this.centerDialogVisible = false;
      //网络请求，传递后端
      let data = {
        name: this.form.name,
        openId: this.form.openId
      };
      console.log(data);
      
      editeUser(data)
        .then(res => {
          console.log("dd");
          this.dialogFormVisible = false;
          console.log(res);
          this.$message({
            message: "修改成功",
            type: "success"
          });
          let data = {
            pageNum: this.pageNum
          };
          if (this.searchData != "") {
            data.search = this.searchData;
          }
          this.getUser(data);
        })
        .catch(err => {
          console.log(err);
          this.$message.error("上传失败");
        });
    },

    //删除按钮
    // 传给后端id
    handleDelete(index, row) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          console.log(row);
          deleteUser(row.openId)
            .then(res => {
              console.log(res);
              this.$message({
                message: "删除成功",
                type: "success"
              });
            })
            .catch(err => {
              console.log(err);
              this.$message.error("删除失败");
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  }
};
</script>
<style scoped>
.el-form-item {
  justify-content: center;
  display: flex;
}
.dialog-footer {
  margin-top: -20px;
  justify-content: space-around;
  display: flex;
}
.delete {
  margin-left: 10px;
}
.el-form-item {
  margin: 0;
}
.el-dialog--center .el-dialog__body {
  padding: 25px 25px 10px 25px !important;
}
.search {
  padding-bottom: 15px;
  display: flex;
  align-items: center;
}
.el-select {
  float: right;
  width: 150px;
}
.el-input {
  float: left;
  margin-top: 5px;
  display: inline-block !important;
  line-height: 50px;
  height: 50px;
  width: 250px;
}
i {
  float: left;
  left: -30px;
  top: 2px;
  line-height: 28px;
  position: relative;
  cursor: pointer;
}
.pagination {
  margin-top: 20px;
  width: 100%;
  position: relative;
  height: 30px;
}
.pagination .el-pagination {
  float: right;
}
</style>
