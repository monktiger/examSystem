<template>
  <div class="out">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="类别">
        <el-select v-model="ruleForm.class" placeholder="请选择工作类别">
          <el-option label="教育类" value="教育类"></el-option>
          <el-option label="政法类" value="政法类"></el-option>
          <el-option label="经贸类" value="经贸类"></el-option>
          <el-option label="IT类" value="IT类"></el-option>
          <el-option label="艺术类" value="艺术类"></el-option>
          <el-option label="传媒类" value="传媒类"></el-option>
          <el-option label="环境类" value="环境类"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="创建时间" required>
        <el-col :span="11">
          <el-form-item prop="creatDate">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="ruleForm.creatDate"
              style="width: 100%;"
              value-format="yyyy-MM-dd"
              format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
          <!--value-format="yyyy-MM-dd" format="yyyy-MM-dd"-->
        </el-col>
      </el-form-item>

      <el-form-item label="过期时间">
        <el-col :span="11">
          <el-form-item>
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="ruleForm.expirationDate"
              style="width: 100%;"
              value-format="yyyy-MM-dd"
              format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-form-item>

      <el-form-item label="推荐">
        <el-switch v-model="ruleForm.isRecommend"></el-switch>
      </el-form-item>

      <el-form-item label="工作地点" prop="position">
        <el-input v-model="ruleForm.position"></el-input>
      </el-form-item>

      <el-form-item label="公司名称" prop="companyName">
        <el-input v-model="ruleForm.companyName"></el-input>
      </el-form-item>

      <el-form-item label="公司性质">
        <el-radio-group v-model="ruleForm.companyNature">
          <el-radio label="民营"></el-radio>
          <el-radio label="国企"></el-radio>
          <el-radio label="其它"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="职务名称">
        <el-input v-model="ruleForm.jobName"></el-input>
      </el-form-item>

      <el-form-item label="职务性质">
        <el-select v-model="ruleForm.jobNature" placeholder="请选择职务性质">
          <el-option label="实习生" value="实习生"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="教育背景">
        <el-select v-model="ruleForm.eduBackground" placeholder="请选择教育背景">
          <el-option label="研究生" value="研究生"></el-option>
          <el-option label="本科" value="本科"></el-option>
          <el-option label="大专" value="大专"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="链接地址" prop="url">
        <el-input v-model="ruleForm.url"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {formUrl} from "../api/index";
export default {
  name: "informationform",
  // components:{upload},
  data() {
    return {
      ruleForm: {
        class: "", //类别
        creatDate: "", //创建时间
        expirationDate: "", //过期时间
        isRecommend: false, //是否推荐
        position: "", //工作地点
        companyName: "", //公司名称
        companyNature: "", //公司性质
        jobName: "", //职务名称
        jobNature: "",
        eduBackground: "", //教育背景
        url: "", //链接地址
        emitData: false
      },

      rules: {
        class: [
          { required: true, message: "请选择工作类别", trigger: "change" }
        ],
        creatDate: [
          {
            required: true,
            message: "请选择创建日期",
            trigger: "change"
          }
        ],
        position: [
          { required: true, message: "请输入工作地点", trigger: "blur" }
        ],
        companyName: [
          { required: true, message: "请输入公司名称", trigger: "blur" }
        ],
        url: [
          { required: true, message: "请输入链接地址", trigger: "blur" }
        ]
      },
      ExcelData: []
    };
  },
  methods: {
    open() {
        this.$alert('这是一段内容', '标题名称', {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        });
      },
    submitForm(formName) {
      // var that = this;
      this.$refs[formName].validate(valid => {
        if (valid) {
          //如果验证成功
          if (this.$route.path === "/reviewSharedURL") {
                this.emitData = true;
                this.$emit("isEmit", this.emitData);
                this.emitData = false;
              }
          formUrl(this.ruleForm).then(function(){
            alert("创建成功");
            // console.log(that.ruleForm)
          }) //发送服务器成功执行
            .catch(function(){
              alert("创建失败，请重试")
            }); //发送服务器失败执行
        } else {
          alert("请将必填项填写完整");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>

<style scoped>
.out {
  width: 80%;
  /* height: 515px; */
  /* overflow-y: scroll; */
  margin: 0 auto;
}
</style>