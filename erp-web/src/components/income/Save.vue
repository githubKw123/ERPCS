<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>资金管理</el-breadcrumb-item>
      <el-breadcrumb-item>其他收入单新增</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <el-form :model="saveForm"
               :rules="saveFormRules"
               ref="saveFormRef"
               label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="客户"
                          prop="customerId">
              <el-select v-model="saveForm.customerId"
                         placeholder="请选择客户">
                <el-option v-for="customer in customerList"
                           :key="customer.id"
                           :label="customer.name"
                           :value="customer.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="单据日期"
                          prop="issueDate">
              <el-date-picker v-model="saveForm.issueDate"
                              type="date"
                              value-format='yyyy-MM-dd'
                              placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="单据编号"
                          prop="code">
              <el-input v-model="saveForm.code"
                        disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 账户表格 -->
          <el-card>
            <!-- 账户列表区域 -->
            <el-table :data="saveForm.recordList"
                      border
                      show-summary
                      :summary-method="getRecordSummaries"
                      stripe>
              <el-table-column label="操作"
                               width="60px">
                <template slot-scope="scope">
                  <el-tooltip effect="dark"
                              content="新增行"
                              placement="top"
                              :enterable="false">
                    <i class="el-icon-plus"
                       @click="addRecordRow(scope.$index, scope.row)"></i>
                  </el-tooltip>
                  <el-tooltip effect="dark"
                              content="删除行"
                              placement="top"
                              :enterable="false">
                    <i class="el-icon-delete"
                       @click="deleteRecordRow(scope.$index, scope.row)"></i>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="收入类别"
                               width="200"
                               prop="categoryId">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.categoryId"
                             placeholder=""
                             @change="handleRecordEdit(scope.$index, scope.row)">
                    <el-option v-for="category in categoryList"
                               :key="category.id"
                               :label="category.name"
                               :value="category.id">
                    </el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="金额"
                               width="180"
                               prop="amount">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.amount"
                            @change="handleRecordEdit(scope.$index, scope.row)"></el-input>
                </template>
              </el-table-column>
              <el-table-column label="备注"
                               prop="remark">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.remark"
                            @change="handleRecordEdit(scope.$index, scope.row)"></el-input>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-row>
        <el-divider content-position="left">结算信息</el-divider>
        <el-row>
          <el-col :span="6">
            <el-form-item label="结算账户"
                          prop="accountId">
              <el-select v-model="saveForm.accountId"
                         placeholder="请选择结算账户"
                         @change="selectAccountChanged">
                <el-option v-for="account in accountList"
                           :key="account.id"
                           :label="account.name"
                           :value="account.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="收款金额"
                          prop="collectAmount">
              <el-input v-model="saveForm.collectAmount"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="制单人"
                          prop="listerName">
              <el-input v-model="saveForm.listName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-row style="float:right;margin: 10px 0;">
        <el-col>
          <el-button @click="save">保存</el-button>
          <el-button type="primary"
                     @click="saveThenNew">保存并新增</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 客户列表
      customerList: [],
      accountList: [],
      categoryList: [],
      saveForm: {
        collectAmount: 0.0,
        accountList: [],
        recordList: [{}, {}, {}]
      },
      saveFormRules: {}
    }
  },
  created() {
    let incomeId = this.$route.query.incomeId
    if (incomeId !== undefined) {
      console.log(incomeId)
      this.getDetail(incomeId)
    } else {
      this.getCode()
    }

    this.getCustomerList()
    this.getAccountList()
    this.getCategoryList()
  },
  methods: {
    // 获取客户列表
    async getCustomerList() {
      const { data: result } = await this.$http.post('/customer/page', {
        current: 1,
        size: 10000
      })
      if (!result.success) return this.$message.error(result.message)

      this.customerList = result.data.customerPage.records
    },
    // 获取单据编号
    async getCode() {
      const { data: result } = await this.$http.post('/income/createCode')
      if (!result.success) return this.$message.error(result.message)

      this.saveForm = {
        id: '',
        collectAmount: 0.0,
        accountList: [],
        recordList: [{}, {}, {}]
      }

      this.saveForm.code = result.data.code
    },
    // 获取分类列表
    async getCategoryList() {
      const { data: result } = await this.$http.post('/category/list', {
        type: 50
      })
      if (!result.success) return this.$message.error(result.message)

      this.categoryList = result.data.categoryList
    },
    // 获取结算账户列表
    async getAccountList() {
      const { data: result } = await this.$http.post('/settlementAccount/list')
      if (!result.success) return this.$message.error(result.message)

      this.accountList = result.data.accountList
    },
    // 选择了结算账户
    selectAccountChanged(accountId) {
      this.saveForm.accountList = []
      var account = {
        accountId
      }
      this.saveForm.accountList.push(account)
    },
    // 获取详情
    async getDetail(id) {
      const { data: result } = await this.$http.post('/income/detail', {
        incomeId: id
      })
      if (!result.success) return this.$message.error(result.message)

      console.log(result.data)
      this.saveForm = result.data.income
    },

    // 新增记录行
    addRecordRow(index, row) {
      console.log(index)
      console.log(row)
      this.saveForm.recordList.splice(index + 1, 0, {})
    },
    // 删除记录行
    deleteRecordRow(index) {
      if (this.saveForm.recordList.length <= 1) {
        return this.$message.warning('至少保留一条记录')
      }
      this.saveForm.recordList.splice(index, 1)
    },

    // 处理编辑单元格
    handleRecordEdit(index, row) {
      this.saveForm.recordList[index] = row
      console.log(this.saveForm.recordList)
    },

    // 计算合计
    getRecordSummaries(param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (column.label === '收入类别') {
          sums[index] = '合计'
          return
        } else if (column.label !== '金额') {
          sums[index] = ''
          return
        }

        const values = data.map((item) => Number(item[column.property]))
        if (!values.every((value) => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr)
            if (!isNaN(value)) {
              return prev + curr
            } else {
              return prev
            }
          }, 0)
        }
      })

      return sums
    },
    // 点击按钮，保存收入单
    async save() {
      this.$refs.saveFormRef.validate(async (valid) => {
        if (!valid) return
        // 处理数据
        let persistRecordList = []
        this.saveForm.recordList.forEach((record) => {
          console.log(record.categoryId)
          console.log(record.categoryId !== undefined)
          if (record.categoryId !== undefined) {
            persistRecordList.push(record)
          }
        })

        // 可以发起新增收入单的网络请求
        const { data: result } = await this.$http.post('/income/save', {
          income: this.saveForm,
          recordList: persistRecordList,
          accountList: this.saveForm.accountList
        })
        if (!result.success) {
          return this.$message.error(result.message)
        }

        this.$message.success('保存收入单成功！')
        console.log(result)
        this.saveForm.id = result.data.income.id
      })
    },
    // 保存并新增
    async saveThenNew() {
      this.save()
      this.getCode()
    }
  }
}
</script>

<style lang="less" scoped>
.query {
  margin-bottom: 20px;
}
.el-select {
  width: 100%;
}
.el-date-editor {
  width: 100%;
}
.el-icon-plus,
.el-icon-delete {
  cursor: pointer;
  margin: 0 3px;
}
</style>
