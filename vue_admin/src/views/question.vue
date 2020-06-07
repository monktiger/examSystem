<template>
  <div class="informationmanagement">
    <el-tabs v-model="activeName">
      <el-tab-pane label="试题管理" name="first">
        <urlRevise v-if="isQuestion" @questionType="questionType"></urlRevise>
        <singleAnswer v-else-if="isSingleAnswer" @back="back" :question="question"></singleAnswer>
        <multipleChoice  v-else-if="isMultipleChoice" @back="back" :question="question"></multipleChoice>
        <subjective  v-else-if="isSubjective" @back="back" :question="question"></subjective>
        <judge  v-else-if="isJudge" @back="back" :question="question"></judge>
         <fill  v-else-if="isFill" @back="back" :question="question"></fill>
      </el-tab-pane>
      <el-tab-pane label="上传Excel" name="third">
        <upload></upload>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
// import {A} from "../api/InE" //写调用的接口
// import informationform from "../components/informationform";
import upload from "../components/upload";
import urlRevise from "./urlRevise";
import singleAnswer from "../components/question/singleAnswer";
import multipleChoice from "../components/question/multipleChoice";
import judge from "../components/question/judge";
import fill from "../components/question/fill";
import subjective from "../components/question/subjective";
export default {
  data() {
    return {
      activeName: "first",
      isSingleAnswer: false,
      isMultipleChoice: false,
      isJudge: false,
      isFill: false,
      isSubjective: false,
      isQuestion: true,
      question: {}
    };
  },
  components: {
    // informationform,
    upload,
    urlRevise,
    singleAnswer,
    multipleChoice,
    judge,
    fill,
    subjective
  },
  methods: {
    questionType(row) {
      this.question = row;
      if (row.type == 1) {
        this.isSingleAnswer = true;
        this.isMultipleChoice = false;
        this.isJudge = false;
        this.isFill = false;
        this.isSubjective = false;
        this.isQuestion = false;
      } else if (row.type == 2) {
        this.isSingleAnswer = false;
        this.isMultipleChoice = true;
        this.isJudge = false;
        this.isFill = false;
        this.isSubjective = false;
        this.isQuestion = false;
      } else if (row.type == 3) {
        this.isSingleAnswer = false;
        this.isMultipleChoice = false;
        this.isJudge = false;
        this.isFill = true;
        this.isSubjective = false;
        this.isQuestion = false;
      } else if (row.type == 4) {
        this.isSingleAnswer = false;
        this.isMultipleChoice = false;
        this.isJudge = true;
        this.isFill = false;
        this.isSubjective = false;
        this.isQuestion = false;
      } else if (row.type == 5) {
        this.isSingleAnswer = false;
        this.isMultipleChoice = false;
        this.isJudge = false;
        this.isFill = false;
        this.isSubjective = true;
        this.isQuestion = false;
      }
    },

    back() {
      this.isSingleAnswer = false;
      this.isMultipleChoice = false;
      this.isJudge = false;
      this.isFill = false;
      this.isSubjective = false;
      this.isQuestion = true;
    }
  }
};
</script>
<style scoped>
</style>
