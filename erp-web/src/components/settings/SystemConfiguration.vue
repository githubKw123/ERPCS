<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统设置</el-breadcrumb-item>
      <el-breadcrumb-item>系统参数</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <div slot="header"
           class="clearfix">
        <span>系统参数配置</span>
      </div>

      <el-form :model="configurtionForm"
               :rules="configurtionFormRules"
               ref="configurtionFormRef"
               label-width="70px">
        <el-form-item label="公司名称"
                      prop="companyName"
                      label-width="120px">
          <el-col :span="8">
            <el-input v-model="configurtionForm.companyName"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="公司地址"
                      prop="companyAddress"
                      label-width="120px">
          <el-col :span="8">
            <el-input v-model="configurtionForm.companyAddress"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="公司电话"
                      prop="companyPhone"
                      label-width="120px">
          <el-col :span="8">
            <el-input v-model="configurtionForm.companyPhone"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="公司传真"
                      prop="companyFax"
                      label-width="120px">
          <el-col :span="8">
            <el-input v-model="configurtionForm.companyFax"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="公司邮编"
                      prop="companyPostCode"
                      label-width="120px">
          <el-col :span="8">
            <el-input v-model="configurtionForm.companyPostCode"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="启用时间"
                      prop="startTime"
                      label-width="120px">
          <el-col :span="8">
            <el-input v-model="configurtionForm.startTime"
                      disabled></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="本位币"
                      prop="currency"
                      label-width="120px">
          <el-col :span="4">
            <el-input v-model="configurtionForm.currency"
                      disabled></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="数量小数位"
                      prop="quantityPrecision"
                      label-width="120px">
          <el-col :span="8">
            <el-input-number v-model="configurtionForm.quantityPrecision"
                             :min="0"
                             :max="5"
                             label="数量小数位"></el-input-number>
          </el-col>
        </el-form-item>

        <el-form-item label="单位小数位"
                      prop="pricePrecision"
                      label-width="120px">
          <el-col :span="8">
            <el-input-number v-model="configurtionForm.pricePrecision"
                             :min="0"
                             :max="5"
                             label="单位小数位"></el-input-number>
          </el-col>
        </el-form-item>

        <el-form-item label="存货计价方法"
                      prop="inventoryValuationMethod"
                      label-width="120px">
          <el-col :span="8">
            <el-select v-model="configurtionForm.inventoryValuationMethod"
                       disabled
                       placeholder="">
              <el-option v-for="inventoryValuationMethodOption in inventoryValuationMethodOptions"
                         :key="inventoryValuationMethodOption.value"
                         :label="inventoryValuationMethodOption.label"
                         :value="inventoryValuationMethodOption.value">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="是否检查负库存"
                      prop="checkNegativeStock"
                      label-width="120px">
          <el-col :span="8">
            <el-switch v-model="configurtionForm.checkNegativeStock"></el-switch>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-button type="primary"
                     @click="onSave">保存</el-button>
          <el-button @click="onClear">还原</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      params: {

      },
      configurtionForm: {
        companyName: '',
        companyAddress: '',
        companyPhone: '',
        companyFax: '',
        companyPostCode: '',
        startTime: '',
        currency: '',
        quantityPrecision: 0,
        pricePrecision: 0,
        inventoryValuationMethod: '',
        checkNegativeStock: true
      },
      configurtionFormRules: {
        companyName: [
          { required: true, message: '请输入公司名称', triggger: 'blur' }
        ],
        companyAddress: [
          { required: true, message: '请输入公司地址', triggger: 'blur' }
        ]
      },
      inventoryValuationMethodOptions: [
        { value: 10, label: '移动平均法' }
      ]
    }
  },
  created () {
    this.getSystemConfiguration()
  },
  methods: {
    // 获取系统配置
    async getSystemConfiguration () {
      const { data: result } = await this.$http.post('/getSystemConfiguration')
      if (!result.success) return this.$message.error(result.message)

      this.configurtionForm = result.data.configuration
    },
    // 保存系统配置
    async onSave () {
      this.$refs.configurtionFormRef.validate(async valid => {
        if (!valid) return
        // 可以发起添加用户的网络请求
        const { data: result } = await this.$http.post('/setSystemConfiguration', {
          configuration: this.configurtionForm
        })
        if (!result.success) {
          return this.$message.error(result.message)
        }

        this.$message.success('保存成功！')
      })
    },
    // 还原
    onClear () {
      this.getSystemConfiguration()
    }
  }
}
</script>

<style lang="less" scoped>
</style>
