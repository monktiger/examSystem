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
      <ul class="option">
        <li>
          <div :class="curA?'cur icon ':'icon'" @click="select(A)">A</div>
          <el-input v-model="question.answerA" placeholder="请输入内容"></el-input>
        </li>
        <li>
          <div :class="curB?'cur icon ':'icon'" @click="select(B)">B</div>
          <el-input v-model="question.answerB" placeholder="请输入内容"></el-input>
        </li>
        <li>
          <div :class="curC?'cur icon ':'icon'" @click="select(C)">C</div>
          <el-input v-model="question.answerC" placeholder="请输入内容"></el-input>
        </li>
        <li>
          <div :class="curD?'cur icon ':'icon'" @click="select(D)">D</div>
          <el-input v-model="question.answerD" placeholder="请输入内容"></el-input>
        </li>
      </ul>
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
    return {
      A: "A",
      B: "B",
      C: "C",
      D: "D",
      cur: "",
      curA: false,
      curB: false,
      curC: false,
      curD: false
    };
  },
  props: ["question"],
  created() {
    console.log(this.question);
    let current;
    current = this.question.current.split("");
    for (let i = 0; i < current.length; i++) {
      if (current[i] == "/") {
        continue;
      }
      if (current[i] == "A") {
        this.curA = true;
      }
      if (current[i] == "B") {
        this.curB = true;
      }
      if (current[i] == "C") {
        this.curC = true;
      }
      if (current[i] == "D") {
        this.curD = true;
      }
    }
  },
  methods: {
    select: function(e) {
      if (e == "A") {
        this.curA = !this.curA;
      } else if (e == "B") {
        this.curB = !this.curB;
      } else if (e == "C") {
        this.curC = !this.curC;
      } else if (e == "D") {
        this.curD = !this.curD;
      }
    },
    submit(e) {
      console.log(this.question);
      let current = "";
      if (this.curA == true) {
        current = current + "A";
      }
      if (this.curB == true) {
        current = current + "B";
      }
      if (this.curC == true) {
        current = current + "C";
      }
      if (this.curD == true) {
        current = current + "D";
      }
      this.question.current = current;
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