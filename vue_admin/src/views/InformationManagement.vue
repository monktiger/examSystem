<template>
  <div class="informationmanagement">
    <el-tabs v-model="activeName">
      <el-tab-pane label="信息修改" name="first">
        <urlRevise v-if="bool" @urlEdit="urlEdit"></urlRevise>
        <editForm v-else :ruleForm="activeUrl" @back="back"></editForm>
      </el-tab-pane>
      <el-tab-pane label="增加" name="second">
        <informationform></informationform>
      </el-tab-pane>
      <el-tab-pane label="上传Excel" name="third">
        <upload></upload>
      </el-tab-pane>
      <el-tab-pane label="单选" name="fourth">
        <singleAnswer></singleAnswer>
      </el-tab-pane>
      <el-tab-pane label="多选题" name="fifth">
        <multipleChoice></multipleChoice>
      </el-tab-pane>
	   <el-tab-pane label="判断题" name="six">
        <judge></judge>
      </el-tab-pane>
	  	   <el-tab-pane label="填空题" name="seven">
        <fill></fill>
      </el-tab-pane>
	  	   <el-tab-pane label="主观题" name="eight">
        <subjective></subjective>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
// import {A} from "../api/InE" //写调用的接口
import informationform from "../components/informationform";
import upload from "../components/upload";
import urlRevise from "./urlRevise";
import editForm from "./editForm";
import singleAnswer from "../components/question/singleAnswer";
import multipleChoice from "../components/question/multipleChoice";
import judge from "../components/question/judge";
import fill from "../components/question/fill";
import subjective from "../components/question/subjective";
export default {
  data() {
    return {
      activeName: "first",
      bool: true,
      activeUrl: {}
    };
  },
  components: {
    informationform,
    upload,
    urlRevise,
    editForm,
    singleAnswer,
	multipleChoice,
	judge,
	fill,
	subjective
  },
  methods: {
    urlEdit(row) {
      this.bool = false;
      this.activeUrl = row;
    },
    back() {
      this.bool = true;
    }
  }
};
</script>
<style scoped>
</style>
