<template>
  <div class="urlRevise">
    <el-form>
      <el-tag style="margin-right:20px;margin-bottom:20px">判断题</el-tag>
      <el-input type="textarea" class="question" :rows="5" placeholder="请输入内容" v-model="title"></el-input>
      <ul class="option">
        <li @click="select(A)">
          <div :class="curA?'cur icon el-icon-check':'icon el-icon-check'"></div>
          <el-input :class="curA?'cur':''" v-model="answerA" placeholder="请输入内容"></el-input>
        </li>
        <li @click="select(B)">
          <div :class="curB?'cur icon el-icon-close':'icon el-icon-close'"></div>
          <el-input :class="curB?'cur':''" v-model="answerA" placeholder="请输入内容"></el-input>
        </li>
      </ul>
      <el-form-item style="display:flex;justify-content:center">
        <el-button type="primary" @click="submitForm('ruleForm')" style="width:150px;">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// import { allInfo, deleteInfo, searchInfo } from "../api/index";
export default {
  data() {
    return {
      A: "A",
      B: "B",
      cur: "",
      curA: false,
      curB: false,
      title:
        "dkkkkkkk憨憨顶顶顶顶顶顶顶顶顶顶kkkkkkkkkkkdkkkkkkk憨憨顶顶顶顶顶顶顶顶顶顶kkkkkkkkkkkdkkkkkkk憨憨顶顶顶顶顶顶顶顶顶顶kkkkkkkkkkkdkkkkkkk憨憨顶顶顶顶顶顶顶顶顶顶kkkkkkkkkkkdkkkkkkk憨憨顶顶顶顶顶顶顶顶顶顶kkkkkkkkkkkdkkkkkkk憨憨顶顶顶顶顶顶顶顶顶顶kkkkkkkkkkkdkkkkkkk憨憨顶顶顶顶顶顶顶顶顶顶kkkkkkkkkkkdkkkkkkk憨憨顶顶顶顶顶顶顶顶顶顶kkkkkkkkkkk",
      answerA: "憨憨顶顶顶顶顶顶顶顶顶顶",
      answerB: "憨憨顶顶顶顶顶顶顶顶顶顶",
      answerC: "憨憨顶顶顶顶顶顶顶顶顶顶",
      answerD: "憨憨顶顶顶顶顶顶顶顶顶顶",
      correct: "憨憨顶顶顶顶顶顶顶顶顶顶"
    };
  },
  props: ["quetion"],
  created() {},
  methods: {
    select: function(e) {
      if (e == "A") {
        this.curA = true;
        this.curB = false;
        this.curC = false;
        this.curD = false;
      } else if (e == "B") {
        this.curA = false;
        this.curB = true;
        this.curC = false;
        this.curD = false;
      }
    }
  },
  back() {
    this.$emit("back");
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
  flex-direction: column;
  padding: 20px 0;
}
.option li {
  display: flex;
  flex-direction: row;
  line-height: 34px;
  margin-bottom: 20px;
}
li {
  list-style: none;
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