<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>销货管理</el-breadcrumb-item>
      <el-breadcrumb-item>采购计划单列表</el-breadcrumb-item>
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
        <el-col :span="8">
          <el-input placeholder="请输入单据号/客户名称/序列号查询"
                    v-model="params.query.name"
                    clearable
                    @clear="getPage">
          </el-input>
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
      <el-row :gutter="20">
        <el-col :span="1.5">
          <el-button type="primary"
                     @click="goAddPage()">新增</el-button>
        </el-col>
      </el-row>

      <!-- 列表区域 -->
      <el-table :data="list"
                border
                stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="单据日期"
                         prop="issueDate"></el-table-column>
        <el-table-column label="单据编号"
                         prop="code"></el-table-column>
        <el-table-column label="销售人员"
                         prop="sellerName"></el-table-column>
        <el-table-column label="客户"
                         prop="customerName"></el-table-column>
        <el-table-column label="销售金额"
                         prop="amount"></el-table-column>
        <el-table-column label="优惠后金额"
                         prop="preferredAmount"></el-table-column>
        <el-table-column label="数量"
                         prop="quantity"></el-table-column>
        <el-table-column label="已收款"
                         prop="currentAmount"></el-table-column>
        <el-table-column label="收款状态"
                         prop="status">
          <template slot-scope="scope">
            <span v-if="scope.row.status = 10">
              未退款
            </span>
            <span v-else-if="scope.row.status = 20">
              部分退款
            </span>
            <span v-else-if="scope.row.status = 30">
              全部收款
            </span>
          </template>
        </el-table-column>
        <el-table-column label="制单人"
                         prop="listerName">
        </el-table-column>
        <el-table-column label="审核人"
                         prop="auditorName">
        </el-table-column>
        <el-table-column label="审核状态"
                         width="70px">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.checked"
                       @change="checkedSwitch(scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="备注"
                         prop="remark">
        </el-table-column>
        <el-table-column label="操作"
                         width="120px">
          <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-tooltip effect="dark"
                        content="修改"
                        placement="top"
                        :enterable="false">
              <el-button type="primary"
                         icon="el-icon-edit"
                         size="mini"
                         @click="goEditPage(scope.row.id)"></el-button>
            </el-tooltip>
            <!-- 删除按钮 -->
            <el-tooltip effect="dark"
                        content="删除"
                        placement="top"
                        :enterable="false">
              <el-button type="danger"
                         icon="el-icon-delete"
                         size="mini"
                         @click="deleteSale(scope.row.id)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="params.current"
                     :page-sizes="[1, 2, 5, 10]"
                     :page-size="params.size"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total">
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 获取职员列表的参数对象
      params: {
        query: {
          type: 'returned'
        },
        // 当前的页数
        current: 1,
        // 当前每页显示多少条数据
        size: 5
      },
      total: 0,
      list: [],
      rangedDate: null
    }
  },
  watch: {},
  created() {
    this.getPage()
  },
  methods: {
    // 搜索
    search() {
      if (this.rangedDate != null) {
        this.params.query.startDate = this.rangedDate[0]
        this.params.query.endDate = this.rangedDate[1]
      } else {
        this.params.query.startDate = ''
        this.params.query.endDate = ''
      }
      this.getPage()
    },
    // 清空
    clear() {
      this.rangedDate = null
      this.params.query = {}
      this.getPage()
    },
    // 获取销货退货单分页列表
    async getPage() {
      const { data: result } = await this.$http.post('/sale/page', this.params)
      if (!result.success) return this.$message.error(result.message)

      this.list = result.data.salePage.records

      this.total = result.data.salePage.total
    },
    // 监听 pagesize 改变的事件
    handleSizeChange(size) {
      this.params.size = size
      this.getPage()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange(current) {
      this.params.current = current
      this.getPage()
    },
    // 新增按钮
    goAddPage() {
      this.$router.push('/returned/save')
    },
    // 修改按钮
    goEditPage(saleId) {
      this.$router.push({
        path: '/returned/save',
        query: { saleId }
      })
    },
    // 销货退货单审核状态改变
    async checkedSwitch(sale) {
      const { data: result } = await this.$http.post('/sale/switchCheck', {
        saleId: sale.id
      })
      if (!result.success) {
        sale.checked = !sale.checked
        return this.$message.error('更新审核状态失败！')
      }
      sale.checked = result.data.sale.checked
      this.$message.success('更新审核状态成功！')
    },
    // 删除销货退货单
    async deleteSale(saleId) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm('此操作将永久删除该销货退货单，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch((err) => err)

      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消了删除，则返回值为字符串 cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }

      const { data: result } = await this.$http.post('/sale/delete', { saleId })

      if (!result.success) {
        return this.$message.error('删除销货退货单失败！')
      }

      this.$message.success('删除销货退货单成功！')
      this.getPage()
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
</style>
