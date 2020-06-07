<template>
  <span class="pic">
    <div class="search">
      <el-select v-model="searchValue" placeholder="请选择" size="mini">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-input v-model="searchData" placeholder="请输入内容" size="mini"></el-input>
      <i class="el-icon-search" @click="search"></i>
    </div>
    <el-table :data="tableData" style="width: 100%" border>
      <el-table-column fixed="type" prop="url" label="题目类型" ></el-table-column>
      <el-table-column prop="category" label="学课类型"></el-table-column>
      <el-table-column prop="title" label="问题"></el-table-column>
      <el-table-column prop="current" label="正确答案" ></el-table-column>
      <el-table-column fixed="right" label="操作" width="140" align="center">
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
            icon="el-icon-delete"
            plain
            @click="handleDelete(scope.$index, scope.row)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 表单弹出窗 -->
    <el-dialog title="修改" :visible.sync="dialogFormVisible" center>
      <el-form :model="form">
        <el-form-item label="试卷名">
          <el-input v-model="form.name" auto-complete="off" style="margin-left:10px;width:200px"></el-input>
        </el-form-item>
        <el-form-item label="组ID">
          <el-input v-model="form.groupId" auto-complete="off" style="margin-left:10px;width:200px"></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-time-picker
            arrow-control
            v-model="form.beginTime"
            placeholder="任意时间点"
          ></el-time-picker>
          <el-date-picker
            v-model="form.beginDate"
            type="date"
            placeholder="选择日期"
            style="margin-left:10px;width:200px"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-time-picker
            arrow-control
            v-model="form.endTime"
            placeholder="任意时间点"
          ></el-time-picker>
          <el-date-picker
            v-model="form.endDate"
            type="date"
            placeholder="选择日期"
            style="margin-left:10px;width:200px"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 分页 -->
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
import { getQuestion, deleteQuestion, editeQuestion } from "../api/temp"; //写调用的接口
export default {
  data() {
    return {
      dialogFormVisible: false, //弹窗
      searchValue: "url",
      length: 0, // 条数
      maxlength: 6,
      tableData: [], //弹出框的标题与上传
      searchData: "", //搜索框
      pageNum: 1, //现在第几页
      pages: 1, //总共多少页
      total: 1, //总共多少条
      options: [
        //选项
        {
          value: "url",
          label: "按链接地址搜索"
        },
        {
          value: "creatTime",
          label: "按创建时间搜索"
        },
        {
          value: "companyName",
          label: "按公司名称搜索"
        },
        {
          value: "jobName",
          label: "按职位名称搜索"
        },
        {
          value: "isRecommend",
          label: "按是否推荐搜索"
        }
      ],
      form: {
        name: "", //组名
        examId: "", //试卷id
        beginTime: "", //开始时间
        beginDate:"",
        endTime: "" ,//结束时间
        endDate:""
      }
    };
  },
  //页面开始之前网络请求
  created: function() {
    this.getQuestion();
  },

  methods: {
    // 分页
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    prevClick() {},
    nextClick() {},
    handlePageChange() {},
    // 获取组
    getQuestion() {
      let data = {
        pageNum: this.pageNum
      };
      getQuestion(data).then(res => {
        console.log(res.data);
        this.tableData = res.data.questionList;
        this.pages = res.data.pages;
        this.total = res.data.total;
        this.pageNum = res.data.pageNum;
        this.length = this.tableData.length;
      });
    },
    formatDate: function(value) {
      var val = parseInt(value);
      let date = new Date(val);
      let y = date.getFullYear();
      this.y = y;
      let MM = date.getMonth() + 1;
      this.m = MM;
      MM = MM < 10 ? "0" + MM : MM;
      let d = date.getDate();
      this.d = d;
      d = d < 10 ? "0" + d : d;
      let h = date.getHours();
      this.h = h;
      h = h < 10 ? "0" + h : h;
      let m = date.getMinutes();
      this.m = m;
      m = m < 10 ? "0" + m : m;
      let s = date.getSeconds();
      s = s < 10 ? "0" + s : s;
      return y + "-" + MM + "-" + d + " " + h + ":" + m + ":" + s;
    },
    search() {
      let tag = {};
      tag[this.searchValue] = this.searchData;
      console.log(tag);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    // 编辑按钮
    handleEdit(index, row) {
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
        examName: this.form.name,
        examId:this.form.examId
      };
      console.log(this.form);
      
      editeQuestion(data)
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
          this.getQuestion(data);
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
          deleteQuestion(row.id)
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
}
.el-select {
  float: right;
  width: 150px;
}
.el-input {
  float: right;
  margin: 0px 5px 15px;
  display: inline-block !important;
  width: 150px;
}
i {
  float: right;
  line-height: 28px;
  position: relative;
  left: 150px;
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
