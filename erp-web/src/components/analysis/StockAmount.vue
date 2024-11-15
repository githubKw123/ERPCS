<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>报表</el-breadcrumb-item>
      <el-breadcrumb-item>商品库存余额表</el-breadcrumb-item>
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
                         width="100"
                         prop="productCode"></el-table-column>
        <el-table-column label="商品名称"
                         width="200"
                         prop="productName"></el-table-column>
        <el-table-column label="规格型号"
                         width="100"
                         prop="spec"></el-table-column>
        <el-table-column label="单位"
                         prop="unitName"
                         width="50"></el-table-column>
        <el-table-column label="所有仓库"
                         width="160">
          <el-table-column label="数量"
                           width="80"
                           prop="warehouseAmountMapping.total.totalQuantity">
          </el-table-column>
          <el-table-column label="成本"
                           width="80"
                           prop="warehouseAmountMapping.total.totalAmount">
          </el-table-column>
        </el-table-column>
        <el-table-column :label="warehouse.name"
                         :key="warehouse.id"
                         v-for="(warehouse) in warehouseList">
          <el-table-column label="数量"
                           width="80">
            <template slot-scope="scope">
              {{scope.row.warehouseAmountMapping[warehouse.id] ? scope.row.warehouseAmountMapping[warehouse.id].currentQuantity : 0}}
            </template>
          </el-table-column>
          <el-table-column label="成本"
                           width="80">
            <template slot-scope="scope">
              {{scope.row.warehouseAmountMapping[warehouse.id] ? scope.row.warehouseAmountMapping[warehouse.id].currentAmount : 0}}
            </template>
          </el-table-column>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      warehouseList: [],
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
      const { data: result } = await this.$http.post('/analysis/stock/amountList', this.params)
      if (!result.success) return this.$message.error(result.message)

      this.warehouseList = result.data.warehouseList
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
        }
        if (column.label !== '数量' && column.label !== '成本') {
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
