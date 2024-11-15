<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>资金管理</el-breadcrumb-item>
      <el-breadcrumb-item>收款单新增</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <el-form :model="saveForm"
               :rules="saveFormRules"
               ref="saveFormRef"
               label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="销货单位"
                          prop="customerId">
              <el-select v-model="saveForm.customerId"
                         placeholder="请选择销货单位">
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
            <el-table :data="saveForm.accountList"
                      border
                      show-summary
                      :summary-method="getAccountSummaries"
                      stripe>
              <el-table-column label="操作"
                               width="60px">
                <template slot-scope="scope">
                  <el-tooltip effect="dark"
                              content="新增行"
                              placement="top"
                              :enterable="false">
                    <i class="el-icon-plus"
                       @click="addAccountRow(scope.$index, scope.row)"></i>
                  </el-tooltip>
                  <el-tooltip effect="dark"
                              content="删除行"
                              placement="top"
                              :enterable="false">
                    <i class="el-icon-delete"
                       @click="deleteAccountRow(scope.$index, scope.row)"></i>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="结算账户"
                               width="200"
                               prop="accountId">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.accountId"
                             placeholder=""
                             @change="handleAccountEdit(scope.$index, scope.row)">
                    <el-option v-for="account in accountList"
                               :key="account.id"
                               :label="account.name"
                               :value="account.id">
                    </el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="收款金额"
                               width="180"
                               prop="amount">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.amount"
                            @change="handleAccountEdit(scope.$index, scope.row)"></el-input>
                </template>
              </el-table-column>
              <el-table-column label="结算方式"
                               width="200"
                               prop="settlementType">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.settlementType"
                             placeholder=""
                             @change="handleAccountEdit(scope.$index, scope.row)">
                    <el-option v-for="settlementType in settlementTypeList"
                               :key="settlementType.id"
                               :label="settlementType.name"
                               :value="settlementType.id">
                    </el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="结算号"
                               width="200"
                               prop="settlementCode">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.settlementCode"
                            @change="handleAccountEdit(scope.$index, scope.row)"></el-input>
                </template>
              </el-table-column>
              <el-table-column label="备注"
                               prop="remark">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.remark"
                            @change="handleAccountEdit(scope.$index, scope.row)"></el-input>
                </template>
              </el-table-column>
            </el-table>
            <el-row style="float:right;margin:10px 0;">
              <el-col>
                <el-button type="primary"
                           plain
                           @click="showSelectSourceIssueDialog()">选择源单</el-button>
              </el-col>
            </el-row>
            <!-- 单据列表区域 -->
            <el-table :data="saveForm.issueList"
                      border
                      show-summary
                      :summary-method="getIssueSummaries"
                      stripe>
              <el-table-column label="操作"
                               width="60px">
                <template slot-scope="scope">
                  <el-tooltip effect="dark"
                              content="新增行"
                              placement="top"
                              :enterable="false">
                    <i class="el-icon-plus"
                       @click="addIssueRow(scope.$index, scope.row)"></i>
                  </el-tooltip>
                  <el-tooltip effect="dark"
                              content="删除行"
                              placement="top"
                              :enterable="false">
                    <i class="el-icon-delete"
                       @click="deleteIssueRow(scope.$index, scope.row)"></i>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="源单编号"
                               width="220"
                               prop="sourceCode">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.sourceCode"
                            disabled></el-input>
                </template>
              </el-table-column>
              <el-table-column label="业务类别"
                               width="180"
                               prop="type">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.type"
                            disabled></el-input>
                </template>
              </el-table-column>
              <el-table-column label="单据日期"
                               width="200"
                               prop="issueDate">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.issueDate"
                            disabled></el-input>
                </template>
              </el-table-column>
              <el-table-column label="单据金额"
                               width="200"
                               prop="issueAmount">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.issueAmount"
                            disabled></el-input>
                </template>
              </el-table-column>
              <el-table-column label="已核销金额"
                               prop="verifiedAmount">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.verifiedAmount"
                            disabled></el-input>
                </template>
              </el-table-column>
              <el-table-column label="未核销金额"
                               prop="unverifiedAmount">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.unverifiedAmount"
                            disabled
                            @change="handleIssueEdit(scope.$index, scope.row)"></el-input>
                </template>
              </el-table-column>
              <el-table-column label="本次核销金额"
                               prop="currentVerifiedAmount">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.currentVerifiedAmount"
                            @change="handleIssueEdit(scope.$index, scope.row)"></el-input>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-row>
        <el-divider content-position="left">结算信息</el-divider>
        <el-row>
          <el-col :span="6">
            <el-form-item label="整单折扣"
                          prop="discountAmount">
              <el-input v-model="saveForm.discountAmount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="本次预收款"
                          prop="advanceCollectAmount">
              <el-input v-model="saveForm.advanceCollectAmount"></el-input>
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

    <!-- 选择对话框 -->
    <el-dialog title='选择源单'
               :visible.sync="selectSourceIssueDialogVisible"
               width="70%"
               @close="selectSourceIssueDialogClosed">
      <!-- 列表区域 -->
      <el-table :data="saleList"
                border
                stripe
                @selection-change="handleSaleSelectionChange">
        <el-table-column type="selection"
                         width="55">
        </el-table-column>
        <el-table-column label="源单编号"
                         prop="code"></el-table-column>
        <el-table-column label="业务类别"
                         prop="type">
          <template slot-scope="scope">
            <span v-if="scope.row.type = 10">
              销货
            </span>
            <span v-else-if="scope.row.type = 20">
              退货
            </span>
          </template>
        </el-table-column>
        <el-table-column label="单据日期"
                         prop="issueDate"></el-table-column>
        <el-table-column label="已核销金额"
                         prop="verifiedAmount"></el-table-column>
        <el-table-column label="未核销金额"
                         prop="unverifiedAmount"></el-table-column>
      </el-table>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="selectSourceIssueDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="confirmSelectSourceIssueDialog">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 客户列表
      customerList: [],
      accountList: [],
      settlementTypeList: [],
      selectSourceIssueDialogVisible: false,
      saveForm: {
        discountAmount: 0,
        advanceCollectAmount: 0.0,
        debtAmount: 0.0,
        accountList: [{}, {}, {}],
        issueList: [{}, {}, {}]
      },
      saveFormRules: {},

      // 选择源单相关
      saleList: [],
      selectedSaleList: []
    }
  },
  created() {
    let collectionId = this.$route.query.collectionId
    if (collectionId !== undefined) {
      console.log(collectionId)
      this.getDetail(collectionId)
    } else {
      this.getCode()
    }

    this.getCustomerList()
    this.getAccountList()
    this.getSettlementTypeList()
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
      const { data: result } = await this.$http.post('/collection/createCode')
      if (!result.success) return this.$message.error(result.message)

      this.saveForm = {
        id: '',
        discountAmount: 0,
        advanceCollectAmount: 0.0,
        debtAmount: 0.0,
        accountList: [{}, {}, {}],
        issueList: [{}, {}, {}]
      }

      this.saveForm.code = result.data.code
    },
    // 获取结算账户列表
    async getAccountList() {
      const { data: result } = await this.$http.post('/settlementAccount/list')
      if (!result.success) return this.$message.error(result.message)

      this.accountList = result.data.accountList
    },
    // 获取结算方式列表
    async getSettlementTypeList() {
      const { data: result } = await this.$http.post('/dict/itemList', {
        dictCode: 'settlement'
      })
      if (!result.success) return this.$message.error(result.message)

      this.settlementTypeList = result.data.itemList
    },
    // 获取详情
    async getDetail(id) {
      const { data: result } = await this.$http.post('/collection/detail', {
        collectionId: id
      })
      if (!result.success) return this.$message.error(result.message)

      console.log(result.data)
      this.saveForm = result.data.collection
    },

    // 新增账户行
    addAccountRow(index, row) {
      console.log(index)
      console.log(row)
      this.saveForm.accountList.splice(index + 1, 0, {})
    },
    // 新增单据行
    addIssueRow(index, row) {
      console.log(index)
      console.log(row)
      this.saveForm.issueList.splice(index + 1, 0, {})
    },
    // 删除账户行
    deleteAccountRow(index) {
      if (this.saveForm.accountList.length <= 1) {
        return this.$message.warning('至少保留一条记录')
      }
      this.saveForm.accountList.splice(index, 1)
    },
    // 删除单据行
    deleteIssueRow(index) {
      if (this.saveForm.issueList.length <= 1) {
        return this.$message.warning('至少保留一条记录')
      }
      this.saveForm.issueList.splice(index, 1)
    },

    // 处理编辑单元格
    handleAccountEdit(index, row) {
      this.saveForm.accountList[index] = row
      console.log(this.saveForm.accountList)
    },
    handleIssueEdit(index, row) {
      this.saveForm.issueList[index] = row
      console.log(this.saveForm.issueList)
    },

    // 计算合计
    getAccountSummaries(param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (column.label === '结算账户') {
          sums[index] = '合计'
          return
        } else if (column.label !== '收款金额') {
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
    getIssueSummaries(param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 1) {
          sums[index] = '合计'
          return
        } else if (column.label !== '本次核销金额') {
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
    // 选择了结算账户
    selectAccountChanged(accountId) {
      this.saveForm.accountList = []
      var account = {
        accountId
      }
      this.saveForm.accountList.push(account)
    },
    // 点击按钮，保存收款单
    async save() {
      this.$refs.saveFormRef.validate(async (valid) => {
        if (!valid) return
        // 处理数据
        let persistAccountList = []
        this.saveForm.accountList.forEach((account) => {
          console.log(account.accountId)
          console.log(account.accountId !== undefined)
          if (account.accountId !== undefined) {
            persistAccountList.push(account)
          }
        })
        let persistIssueList = []
        this.saveForm.issueList.forEach((issue, index) => {
          if (issue.sourceCode !== undefined) {
            persistIssueList.push(issue)
          }
        })

        // 可以发起新增收款单的网络请求
        const { data: result } = await this.$http.post('/collection/save', {
          collection: this.saveForm,
          accountList: persistAccountList,
          issueList: persistIssueList
        })
        if (!result.success) {
          return this.$message.error(result.message)
        }

        this.$message.success('保存收款单成功！')
        console.log(result)
        this.saveForm.id = result.data.collection.id
      })
    },
    // 保存并新增
    async saveThenNew() {
      this.save()
      this.getCode()
    },
    // 显示选择源单对话框
    async showSelectSourceIssueDialog() {
      let customerId = this.saveForm.customerId
      if (customerId === undefined) {
        return this.$message.warning('请先选择销货单位')
      }
      const { data: result } = await this.$http.post('/sale/findCheckedListByCustomer', {
        customerId
      })
      if (!result.success) {
        return this.$message.error(result.message)
      }

      this.saleList = result.data.saleList
      this.selectSourceIssueDialogVisible = true
    },
    // 选择源单对话框关闭
    selectSourceIssueDialogClosed() {},
    // 点击选择源单弹框的确定
    confirmSelectSourceIssueDialog() {
      console.log(this.saveForm.issueList)
      console.log(this.saveForm)

      let emptyIssueIndexList = []
      this.saveForm.issueList.forEach((issue, index) => {
        if (issue.sourceCode === undefined) {
          emptyIssueIndexList.push(index)
        }
      })
      this.selectedSaleList.forEach((sale) => {
        let newIssue = {
          sourceCode: sale.code,
          type: sale.type,
          issueDate: sale.issueDate,
          issueAmount: sale.amount,
          verifiedAmount: sale.verifiedAmount,
          unverifiedAmount: sale.unverifiedAmount
        }
        if (emptyIssueIndexList.length === 0) {
          this.saveForm.issueList.push(newIssue)
        } else {
          let index = emptyIssueIndexList[0]
          this.saveForm.issueList.splice(index, 1, newIssue)
          emptyIssueIndexList.shift()
        }
      })

      this.selectedSaleList = []
      this.selectSourceIssueDialogVisible = false
    },
    // 处理选择销售单
    handleSaleSelectionChange(saleList) {
      this.selectedSaleList = saleList
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
