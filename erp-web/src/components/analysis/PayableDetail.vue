<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>报表</el-breadcrumb-item>
      <el-breadcrumb-item>应付账款明细表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20"
              class="query">
        <el-col :span="7">
          <el-date-picker v-model="rangedDate"
                          type="daterange"
                          value-format='yyyy-MM-dd'
                          range-separator="至"
                          start-placeholder="开始日期"
                          end-placeholder="结束日期">
          </el-date-picker>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary"
                     @click="search">查询</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="success"
                     @click="clear">重置</el-button>
        </el-col>
      </el-row>

      <!-- 列表区域 -->
      <el-table :data="list"
                @row-click="rowClick"
                height="600"
                border
                ref="table"
                show-summary
                :summary-method="getSummaries">
        <el-table-column label="供应商"
                         prop="supplierName"
                         width="100"></el-table-column>
        <el-table-column label="单据日期"
                         prop="issueDate"></el-table-column>
        <el-table-column label="单据编号"
                         width="200"
                         prop="issueCode"></el-table-column>
        <el-table-column label="业务类型"
                         prop="businessTypeName"></el-table-column>
        <el-table-column label="增加应付款"
                         prop="increasedAmount"></el-table-column>
        <el-table-column label="支付应付款"
                         prop="paidAmount"></el-table-column>
        <el-table-column label="应收款余额"
                         prop="currentAmount"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 参数对象
      params: {
        startDate: '',
        endDate: '',
        supplierIdList: []
      },
      list: [],
      rangedDate: []
    }
  },
  watch: {},
  created() {},
  mounted() {
    let params = this.$route.query.params
    if (params !== undefined) {
      if (params.customerId !== undefined) {
        this.params.supplierIdList.push(params.customerId)
      }
      this.rangedDate[0] = params.startDate
      this.rangedDate[1] = params.endDate
    }

    this.search()
  },
  updated() {
    this.$nextTick(() => {
      this.$refs['table'].doLayout()
    })
  },
  methods: {
    // 搜索
    search() {
      if (this.rangedDate.length !== 0) {
        this.params.startDate = this.rangedDate[0]
        this.params.endDate = this.rangedDate[1]
      } else {
        this.params.startDate = ''
        this.params.endDate = ''
      }
      this.getList()
    },
    // 清空
    clear() {
      this.rangedDate = []
      this.params = {}
      this.getList()
    },
    // 获取列表
    async getList() {
      const { data: result } = await this.$http.post('/analysis/payable/detailList', this.params)
      if (!result.success) return this.$message.error(result.message)

      this.list = result.data.payableList
    },
    // 计算合计
    getSummaries(param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计'
          return
        } else if (column.label !== '增加应付款' && column.label !== '支付应付款' && column.label !== '应付款余额') {
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
    rowClick(row) {
      if (row.businessType === 'buy') {
        this.$router.push({
          path: '/purchase/save',
          query: { purchaseId: row.businessId }
        })
      } else if (row.businessType === 'refund') {
        this.$router.push({
          path: '/refund/save',
          query: { purchaseId: row.businessId }
        })
      } else if (row.businessType === 'payment') {
        this.$router.push({
          path: '/payment/save',
          query: { paymentId: row.businessId }
        })
      }
    }
  }
}
</script>

<style lang="less">
.query {
  margin-bottom: 20px;
}
.el-select {
  width: 100%;
}
.el-date-editor.el-range-editor {
  width: 100%;
}
.el-table tr {
  cursor: pointer;
}
</style>
