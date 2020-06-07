<template>
  <div class="upload">
    <el-button type="primary" class="button">
      <input type="file" class="input" ref="input" @change="readFile" />
      点击上传
    </el-button>

    <transition name="fade">
      <div v-if="fileList.length">
        <ul>
          <li class="fileItem" v-for="item in fileList" v-bind:key="item.name">
            <span class="el-icon-document"></span>
            <span class="text">{{ item.name }}</span>
            <span class="el-icon-error" @click="deleteItem"></span>
          </li>
        </ul>
        <el-button type="primary" class="button submit" @click="submit">确认提交</el-button>
      </div>
    </transition>
  </div>
</template>

<script>
import XLSX from "xlsx";
import { uploadUrl } from "../api/index";

export default {
  name: "upload",
  data() {
    return {
      detailData: [], // 解析出来的数据
      a: 0,
      fileList: [],
      listFinal: []
    };
  },
  created() {
    console.log(this.formatDate(42618, "/"));
  },
  methods: {
    readFile(e) {
      // 获取文件对象
      let files = e.target.files;
      // xls或者xlsx格式
      let reg = /.(xlsx|xlsm|xlsb|xls)/g;
      // 判断文件的名字是否时.xlsx结尾
      if (!reg.test(files[0].name)) {
        this.$message.error("请上传类型相符的文件");
        return;
      }
      // fileList 第一个对象 名字
      this.fileList = [
        {
          name: files[0].name
        }
      ];
      // 创建一个fielRreader对象
      let fileReader = new FileReader();
      // 加载
      fileReader.onload = ev => {
        let data = ev.target.result;
        // 以二进制流方式读取得到整份excel表格对象
        let workbook = XLSX.read(data, {
          type: "binary"
        }); 
        let fromTo = "";
        // 遍历每张表读取
        for (let sheet in workbook.Sheets) {
          // 遍历对象
          if (workbook.Sheets) {
            fromTo = workbook.Sheets[sheet]["!ref"]; // 开始和结束坐标
            if (!fromTo) continue; // 这个表里没有内容
            // 连接数组
            this.detailData = this.detailData.concat(
              XLSX.utils.sheet_to_json(workbook.Sheets[sheet])
            );
          }
        }
      };
      fileReader.readAsBinaryString(files[0]);
      this.$refs.input.value = "";
    },
    deleteItem() {
      this.listFinal = [];
      this.fileList = [];
      this.detailData = [];
      this.$refs.input.value = "";
    },
    submit() {
      this.detailData.forEach(item => {
        this.listFinal.push({
          type: item["类别"] ? item["类别"] : "",
          creatTime: this.formatDate(item["创建时间"], "-"),
          expirationTime: item["过期时间"]
            ? this.formatDate(item["过期时间"], "-")
            : "",
          isRecommend:
            item["是否推荐"] === "是" ? "是" : "否",
          position: item["工作地点"] ? item["工作地点"] : "",
          companyName: item["公司名称"],
          companyNature: item["公司性质"] ? item["公司性质"] : "",
          jobName: item["职务名称"],
          jobNature: item["职务性质"] ? item["职务性质"] : "",
          eduBackground: item["教育背景"] ? item["教育背景"] : "",
          url: item["链接地址"]
        });
      });
      console.log(this.listFinal);
      let bool = true;
      this.listFinal.forEach(item => {
        uploadUrl(item)
          .then(res => {
            console.log(res);
          })
          .catch(err => {
            bool = false;
            throw err;
          });
      });
      if (bool) {
        this.$message({
          message: "上传成功",
          type: "success"
        });
      }
      this.listFinal = [];
      this.fileList = [];
      this.detailData = [];
      this.$refs.input.value = "";
    },
    formatDate(numb, format) {
      const time = new Date((numb - 1) * 24 * 3600000 + 1);
      time.setYear(time.getFullYear() - 70);
      const year = time.getFullYear() + "";
      const month = time.getMonth() + 1 + "";
      const date = time.getDate() - 1 + "";
      if (format && format.length === 1) {
        return year + format + month + format + date;
      }
      return (
        year +
        (month < 10 ? "0" + month : month) +
        (date < 10 ? "0" + date : date)
      );
    }
  }
};
</script>

<style scoped lang="less">
.upload {
  width: 100%;
  height: 100%;
}

ul {
  height: 40px;
}

.button {
  position: relative;
  margin: 0;
  overflow: hidden;
  display: block;
}
.submit {
  margin-top: 20px;
  transition: 0.3s;
}
.input {
  display: block;
  position: absolute;
  top: 0;
  left: 0;
  opacity: 0;
  width: 100%;
  height: 100%;
}

.fileItem {
  margin-top: 20px;
  list-style: none;
  padding: 10px;
  width: 200px;
  height: 20px;
  line-height: 20px;
  text-align: left;
  font-size: 14px;
  color: #999;
  border: 1px solid #999;
  cursor: pointer;

  span {
    display: block;
    height: 20px;
    line-height: 20px;
    float: left;
  }

  .el-icon-document {
    margin-right: 10px;
  }

  .el-icon-error {
    opacity: 0;
    float: right;
    transition: 0.3s;
  }

  .text {
    max-width: 160px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
}

.fileItem:hover .el-icon-error {
  opacity: 1;
}

.fade-enter-active {
  transition: all 0.3s ease;
}

.fade-leave-active {
  transition: all 0.3s ease;
}

.fade-enter,
.fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}
</style>
