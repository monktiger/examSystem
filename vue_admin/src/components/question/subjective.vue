<template>
  <div class="urlRevise">
    <el-form>
      <div style="display:flex">
        <div
          class="el-icon-back"
          @click="back"
          style="font-size:30px;color:teal;  cursor: pointer;"
        ></div>
        <el-tag style="margin-right:20px;margin-bottom:20px;margin-left:20px">判断题</el-tag>
      </div>
      <el-input
        type="textarea"
        class="question"
        :rows="5"
        placeholder="请输入内容"
        v-model="question.title"
      ></el-input>
      <div class="option">
        <div style="width:50px;line-height:40px">答案</div>
        <el-input
          type="textarea"
          class="question"
          :rows="5"
          placeholder="请输入内容"
          v-model="question.current"
        ></el-input>
      </div>
      <el-form-item style="display:flex;justify-content:center">
        <el-button type="primary" @click="submit" style="width:150px;">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { editeQuestion } from "../../api/temp";
export default {
  data() {
    return {};
  },
  props: ["question"],
  created() {
    console.log(this.question);
  },

  methods: {
    submit(e) {
      console.log(this.question);
      editeQuestion(this.question)
        .then(res => {
          console.log(res);
          this.$message({
            message: "修改成功！",
            type: "success"
          });
          this.back();
        })
        .catch(err => {
          console.log(err);
          this.$message.error("上传失败");
        });
    },
    back() {
      this.$emit("back");
    }
  }
};
</script>

<style scoped lang="less">
.el-textarea {
  border-radius: 20px;
}
.urlRevise {
  padding: 0px 20px 0px 20px;
}
.option {
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 20px 0;
}
.icon {
  cursor: pointer;
  height: 40px;
  width: 40px;
  border-radius: 20px;
  text-align: center;
  line-height: 40px;
  border: 1px solid #dcdfe6;
  margin-right: 20px;
}
.cur {
  background: #cce6e6;
}
</style>