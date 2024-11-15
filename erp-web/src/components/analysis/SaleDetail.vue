<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>报表</el-breadcrumb-item>
      <el-breadcrumb-item>销售明细表</el-breadcrumb-item>
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
        <el-table-column label="销售日期"
                         prop="issueDate"
                         width="100"></el-table-column>
        <el-table-column label="销售单据号"
                         prop="saleCode"
                         width="180"></el-table-column>
        <el-table-column label="业务类别"
                         prop="type"
                         width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.type === 10">
              销货
            </span>
            <span v-else-if="scope.row.type === 20">
              销退
            </span>
          </template>
        </el-table-column>
        <el-table-column label="销售人员"
                         prop="sellerName"></el-table-column>
        <el-table-column label="客户"
                         prop="customerName"></el-table-column>
        <el-table-column label="商品编号"
                         prop="productCode"></el-table-column>
        <el-table-column label="商品名称"
                         prop="productName"></el-table-column>
        <el-table-column label="规格型号"
                         prop="spec"></el-table-column>
        <el-table-column label="单位"
                         prop="unitName"></el-table-column>
        <el-table-column label="仓库"
                         prop="warehouseName">
        </el-table-column>
        <el-table-column label="数量"
                         prop="quantity">
        </el-table-column>
        <el-table-column label="单价"
                         prop="price">
        </el-table-column>
        <el-table-column label="销售收入"
                         prop="amount">
        </el-table-column>
        <el-table-column label="备注"
                         prop="remark">
        </el-table-column>
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
        customerIdList: [],
        productIdList: [],
        warehouseIdList: [],
        sellerIdList: []
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
      if (params.productId !== undefined) {
        this.params.productIdList.push(params.productId)
      }
      if (params.customerId !== undefined) {
        this.params.customerIdList.push(params.customerId)
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
      const { data: result } = await this.$http.post('/analysis/sale/detailList', this.params)
      if (!result.success) return this.$message.error(result.message)

      this.list = result.data.productList
    },
    // 计算合计
    getSummaries(param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计'
          return
        } else if (column.label !== '数量' && column.label !== '销售收入') {
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
      if (row.type === 10) {
        this.$router.push({
          path: '/sell/save',
          query: { saleId: row.saleId }
        })
      } else if (row.type === 20) {
        this.$router.push({
          path: '/returned/save',
          query: { saleId: row.saleId }
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
