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
      <el-table-column prop="name" label="名字"></el-table-column>
      <el-table-column prop="group_id" label="组ID"></el-table-column>
      <el-table-column prop="open_id" label="管理者ID"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
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
    <div class="addWrap">
      <el-button type="success" icon="el-icon-plus" circle class="add" @click="handleAdd"></el-button>
    </div>
    <el-dialog :title="title" :visible.sync="centerDialogVisible" width="30%" center>
      <div>
        <span class="editeTitle1">{{editeTitle1}}</span>
        <el-input v-model="input1" placeholder="增加的url" clearable></el-input>
      </div>
      <div>
        <span class="editeTitle2">{{editeTitle2}}</span>
        <el-input v-model="input2" placeholder="轮播顺序" clearable :disabled="disabled"></el-input>
      </div>
      <!-- 文件上传 -->
      <el-upload
        v-show="isShow"
        class="upload-demo"
        ref="upload"
        action="string"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :auto-upload="false"
        :http-request="httpRequest"
        :file-list="fileList"
        list-type="picture"
        :limit="limit"
        :before-upload="beforeUpload"
      >
        <el-button size="small" type="primary">上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过1MB</div>
      </el-upload>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </span>
    </el-dialog>
  </span>
</template>
<script>
import axios from "axios";
import { getGroup, deleteGroup, editeGroup } from "../api/temp"; //写调用的接口
export default {
  data() {
    return {
      searchValue: "url",
      banner: [],
      id: "",
      //轮播的条数,记得随时更新
      length: 0,
      maxlength: 6,
      //弹出框的标题与上传
      title: "",
      // 输入url
      input0: "",
      input1: "",
      input2: "",
      editeTitle1: "",
      editeTitle2: "",
      isShow: false,
      //限制文件数目
      limit: 1,
      // 上传文件
      fileList: [],
      //上传的文件
      img: "",
      centerDialogVisible: false,
      tableData: [],
      disabled: false
    };
  },
  //页面开始之前网络请求
  created: function() {
    getGroup().then(res => {
      console.log(res);
      this.tableData = res.data.groupList;
      this.length = this.tableData.length;
      console.log(res.data.groupList);
    });
  },
  methods: {
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
    //文件上传,覆盖默认上传行为
    httpRequest(params) {
      let fd = new FormData();
      console.log(params);
      fd.append("picture", params.file);
      fd.append("url", this.input1);
      fd.append("rank", this.input2);
      fd.forEach((value, key) => {
        console.log(key, typeof value, value);
      });
      console.log(fd);
      let config = {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      };
      axios
        .post("http://jdall.nenu.edu.cn:8080/pic/insertPic", fd, config)
        .then(res => {
          if (res.data == true) {
            this.$message({
              type: "success",
              message: "添加成功"
            });
            getGroup().then(res => {
              console.log(res.data);
              this.tableData = res.data;
              this.length = this.tableData.length;
            });
            this.$refs.upload.clearFiles();
            this.$refs.uploadForm.resetFields();
          } else {
            console.log(res);
            this.$message({
              type: "error",
              message: res.data
            });
          }
        })
        .catch(res => {
          console.log(res);
        });
      return true;
    },
    // 编辑按钮
    handleEdit(index, row) {
      console.log(row);
      this.centerDialogVisible = true;
      console.log(index, row);
      this.id = parseInt(row.id);
      this.input1 = row.url;
      this.input2 = parseInt(row.rank);
      this.disabled = false;
      this.title = "编辑轮播图";
      this.isShow = false;
      this.editeTitle1 = "修改图片对应的url";
      this.editeTitle2 = "修改轮播顺序";
      this.inpue0 = this.input1;
    },
    //新增按钮
    handleAdd() {
      if (this.length == this.maxlength) {
        this.$alert("轮播图的图片最多只能" + this.maxlength + "张", "警告", {
          confirmButtonText: "确定"
        });
      } else {
        this.centerDialogVisible = true;
        this.title = "增加轮播图";
        this.content = "上传";
        this.input1 = "";
        this.input2 = this.length + 1;
        // this.input2 = 3;
        this.disabled = true;
        this.title = "增加轮播图";
        this.isShow = true;
        this.editeTitle1 = "输入图片对应的url";
        this.editeTitle2 = "输入轮播顺序";
      }
    },
    //确定按钮
    confirm() {
      this.centerDialogVisible = false;
      //网络请求，传递后端
      if (this.isShow == true) {
        console.log(this.fileList);
        if (this.input1 == "" || this.input2 == "") {
          this.$message({
            type: "error",
            message: "增加信息失败，请输入完整信息"
          });
        } else {
          if (this.input2 > this.maxlength) {
            this.$alert(
              "轮播图的图片最多只能" + this.maxlength + "张",
              "警告",
              {
                confirmButtonText: "确定"
              }
            );
          } else {
            this.disabled = true;
            this.$refs.upload.submit(); // 这里是执行文件上传的函数，其实也就是获取我们要上传的文件
          }
        }
      } else {
        if (this.input1 == "" || this.input2 == "") {
          this.$message({
            type: "error",
            message: "修改失败，请输入完整信息"
          });
        } else {
          if (this.input2 >= this.maxlength) {
            this.$alert(
              "轮播图的图片最多只能" + this.maxlength + "张",
              "警告",
              {
                confirmButtonText: "确定"
              }
            );
          } else {
            if (this.input2 <= this.length) {
              // show=false;
              editeGroup({
                id: this.id,
                url: this.input1,
                rank: this.input2
              }).then(res => {
                if (res.data == true) {
                  this.$message({
                    type: "success",
                    message: "修改成功"
                  });
                  getGroup().then(res => {
                    console.log(res.data);
                    this.tableData = res.data;
                    this.length = this.tableData.length;
                  });
                } else {
                  this.$message({
                    type: "error",
                    message: "修改失败"
                  });
                }
              });
            } else {
              this.$message({
                type: "error",
                message: "请不要做无用的修改"
              });
            }
          }
        }
      }
    },
    //删除按钮
    // 传给后端id
    handleDelete(index, row) {
      console.log(index, row);
      let id = parseInt(row.id);
      this.$confirm("此操作将永久删除轮播, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //网络请求
          deleteGroup({ id: id }).then(res => {
            console.log(id);
            console.log(typeof id);
            console.log(res);
            if (res.data) {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              getGroup().then(res => {
                console.log(res.data);
                this.tableData = res.data;
                this.length = this.tableData.length;
              });
            } else {
              this.$message({
                type: "error",
                message: "删除失败"
              });
            }
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
.addWrap {
  padding-right: 10px;
}

.add {
  margin-top: 10px;
  float: right;
  /* margin-right: 10px; */
}

.delete {
  margin-left: 10px;
}

.upload-demo {
  margin-top: 10px;
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
</style>
