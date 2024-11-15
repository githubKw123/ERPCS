<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>报表</el-breadcrumb-item>
      <el-breadcrumb-item>商品收发汇总表</el-breadcrumb-item>
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
                height="600"
                border
                ref="table"
                show-summary
                :summary-method="getSummaries">
        <el-table-column label="商品编号"
                         prop="productCode"></el-table-column>
        <el-table-column label="商品名称"
                         prop="productName"></el-table-column>
        <el-table-column label="规格型号"
                         prop="spec"></el-table-column>
        <el-table-column label="单位"
                         prop="unitName"
                         width="50"></el-table-column>
        <el-table-column label="仓库"
                         prop="warehouseName">
        </el-table-column>
        <el-table-column label="期初">
          <el-table-column label="数量"
                           width="70"
                           prop="startQuantity"></el-table-column>
          <el-table-column label="成本"
                           width="70"
                           prop="startAmount">
          </el-table-column>
        </el-table-column>
        <el-table-column label="调拨入库">
          <el-table-column label="数量"
                           width="70"
                           prop="transferInQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="普通采购">
          <el-table-column label="数量"
                           width="70"
                           prop="buyQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="销售退货">
          <el-table-column label="数量"
                           width="70"
                           prop="returnedQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="盘盈">
          <el-table-column label="数量"
                           width="70"
                           prop="storeProfitQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="其他入库">
          <el-table-column label="数量"
                           width="70"
                           prop="storeOtherQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="成本调整">
          <el-table-column label="数量"
                           width="70"
                           prop="storeAdjustQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="入库合计">
          <el-table-column label="数量"
                           width="70"
                           prop="storeTotalQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="调拨出库">
          <el-table-column label="数量"
                           width="70"
                           prop="transferOutQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="采购退回">
          <el-table-column label="数量"
                           width="70"
                           prop="refundQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="普通销售">
          <el-table-column label="数量"
                           width="70"
                           prop="sellQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="盘亏">
          <el-table-column label="数量"
                           width="70"
                           prop="checkoutLossQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="其他出库">
          <el-table-column label="数量"
                           width="70"
                           prop="checkoutOtherQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="出库合计">
          <el-table-column label="数量"
                           width="70"
                           prop="checkoutTotalQuantity"></el-table-column>
        </el-table-column>
        <el-table-column label="结存">
          <el-table-column label="数量"
                           width="70"
                           prop="endQuantity"></el-table-column>
          <el-table-column label="数量"
                           width="70"
                           prop="endAmount"></el-table-column>
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
        productIdList: [],
        warehouseIdList: []
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
      if (params.warehouseId !== undefined) {
        this.params.warehouseIdList.push(params.warehouseId)
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
      const { data: result } = await this.$http.post('/analysis/stock/summaryList', this.params)
      if (!result.success) return this.$message.error(result.message)

      let stockList = result.data.stockList
      stockList.forEach((stock) => {
        stock.startQuantity = stock.startQuantity === undefined ? 0 : stock.startQuantity
        stock.startAmount = stock.startAmount === undefined ? 0 : stock.startAmount
        stock.transferInQuantity = stock.transferInQuantity === undefined ? 0 : stock.transferInQuantity
        stock.buyQuantity = stock.buyQuantity === undefined ? 0 : stock.buyQuantity
        stock.returnedQuantity = stock.returnedQuantity === undefined ? 0 : stock.returnedQuantity
        stock.storeProfitQuantity = stock.storeProfitQuantity === undefined ? 0 : stock.storeProfitQuantity
        stock.storeOtherQuantity = stock.storeOtherQuantity === undefined ? 0 : stock.storeOtherQuantity
        stock.storeAdjustQuantity = stock.storeAdjustQuantity === undefined ? 0 : stock.storeAdjustQuantity
        stock.storeTotalQuantity = stock.storeTotalQuantity === undefined ? 0 : stock.storeTotalQuantity
        stock.transferOutQuantity = stock.transferOutQuantity === undefined ? 0 : stock.transferOutQuantity
        stock.refundQuantity = stock.refundQuantity === undefined ? 0 : stock.refundQuantity
        stock.sellQuantity = stock.sellQuantity === undefined ? 0 : stock.sellQuantity
        stock.checkoutLossQuantity = stock.checkoutLossQuantity === undefined ? 0 : stock.checkoutLossQuantity
        stock.checkoutOtherQuantity = stock.checkoutOtherQuantity === undefined ? 0 : stock.checkoutOtherQuantity
        stock.checkoutTotalQuantity = stock.checkoutTotalQuantity === undefined ? 0 : stock.checkoutTotalQuantity
        stock.endQuantity = stock.endQuantity === undefined ? 0 : stock.endQuantity
        stock.endAmount = stock.endAmount === undefined ? 0 : stock.endAmount
      })

      this.list = stockList
    },
    // 计算合计
    getSummaries(param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计'
          return
        } else if (column.label !== '数量' && column.label !== '成本') {
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
</style>
