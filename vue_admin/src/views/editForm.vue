<template>
    <div class="out">
        <el-button @click="back" type="text" size="small"><i class="el-icon-back"></i> 返回</el-button>
        <el-form
                :model="ruleForm"
                :rules="rules"
                ref="ruleForm"
                label-width="100px"
                class="demo-ruleForm"
        >
            <el-form-item label="类别" prop="type">
                <el-select v-model="ruleForm.type" placeholder="请选择工作类别">
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
                    <el-form-item prop="creatTime">
                        <el-date-picker
                                type="date"
                                placeholder="选择日期"
                                v-model="ruleForm.creatTime"
                                style="width: 100%;"
                                value-format="timestamp"
                                format="yyyy-MM-dd"
                        ></el-date-picker>
                    </el-form-item>
                    <!--value-format="yyyy-MM-dd" format="yyyy-MM-dd"-->
                </el-col>
            </el-form-item>
            
            <el-form-item label="过期时间">
                <el-col :span="11">
                    <el-form-item prop="outdate">
                        <el-date-picker
                                type="date"
                                placeholder="选择日期"
                                v-model="ruleForm.expirationTime"
                                style="width: 100%;"
                                value-format="timestamp"
                                format="yyyy-MM-dd"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
            </el-form-item>
            
            <el-form-item label="推荐" prop="isRecommend">
                <el-switch v-model="ruleForm.isRecommend"></el-switch>
            </el-form-item>
            
            <el-form-item label="工作地点" prop="position">
                <el-input v-model="ruleForm.position"></el-input>
            </el-form-item>
            
            <el-form-item label="公司名称" prop="companyName">
                <el-input v-model="ruleForm.companyName"></el-input>
            </el-form-item>
            
            <el-form-item label="公司性质" prop="companyNature">
                <el-radio-group v-model="ruleForm.companyNature">
                    <el-radio label="民营"></el-radio>
                    <el-radio label="国企"></el-radio>
                    <el-radio label="其它"></el-radio>
                </el-radio-group>
            </el-form-item>
            
            <el-form-item label="职务名称" prop="jobName">
                <el-input v-model="ruleForm.jobName"></el-input>
            </el-form-item>
            
            <el-form-item label="职务性质" prop="jobNature">
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
                <el-button type="primary" @click="submitForm('ruleForm')">提交修改</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {updateInfo} from "../api/index"
	export default {
		name: "informationform",
        props:{
		    ruleForm:{
		    	required:true
            }
        },
        created() {
	        this.ruleForm.creatTime = new Date(this.ruleForm.creatTime);
            this.ruleForm.expirationTime = new Date(this.ruleForm.expirationTime);
	        this.ruleForm.isRecommend=this.ruleForm.isRecommend==="否"?false:true;
        },
		data() {
			return {
				rules: {
					type: [
						{ message: "请选择工作类别", trigger: "change" }
					],
					creatTime: [
						{
							required: true,
							message: "请选择创建日期",
							trigger: "change"
						}
					],
					expirationTime: [
						{
							message: "请选择过期日期",
							trigger: "change"
						}
					],
					position: [
						{required: true,message: "请输入工作地点", trigger: "blur" }
					],
					companyName: [
						{ required: true, message: "请输入公司名称", trigger: "blur" }
					],
					companyNature: [
						{message: "请选择公司性质", trigger: "change" }
					],
					jobName: [
						{message: "请输入职务名称", trigger: "blur" }
					],
					jobNature: [
						{ message: "请选择职务性质", trigger: "change" }
					],
					eduBackground: [
						{ message: "请选择教育背景", trigger: "change" }
					],
					url: [
						{ required: true, message: "请输入链接地址", trigger: "blur" }
					]
				},
				ExcelData: []
			};
		},
		methods: {
			submitForm(formName) {
				this.$refs[formName].validate(valid => {
					if (valid) {
						this.ruleForm.creatTime =new Date(this.ruleForm.creatTime).toJSON().split("T")[0]
						this.ruleForm.expirationTime =new Date(this.ruleForm.expirationTime).toJSON().split("T")[0];
						this.ruleForm.isRecommend=this.ruleForm.isRecommend===false?"否":"是";
						updateInfo(this.ruleForm).then(()=>{
							this.$message({
								message: '修改成功！',
								type: 'success'
							});
							this.$emit("back");
						}).catch(err=>{
							throw err;
                        })
					} else {
						return false;
					}
				});
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			},
			back(){
				this.$emit("back");
            },
		}
	};
</script>

<style scoped>
    .out {
        width: 80%;
        margin: 0 auto;
    }
</style>
