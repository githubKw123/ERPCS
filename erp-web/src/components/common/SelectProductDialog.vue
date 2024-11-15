<template>
  <div>
    <!-- 选择商品对话框 -->
    <el-dialog title="选择商品"
               :visible.sync="open"
               width="70%">
      <el-container>
        <el-aside width="200px">
          <el-card>
            <el-tree :data="categoryList"
                     :props="defaultProps"
                     default-expand-all
                     node-key="id"
                     ref="tree"
                     highlight-current
                     :expand-on-click-node="false"
                     @node-click="handleNodeClick"></el-tree>
          </el-card>
        </el-aside>
        <el-main>
          <!-- 卡片视图区域 -->
          <el-card>
            <!-- 搜索与添加区域 -->
            <el-row :gutter="20"
                    class="query">
              <el-col :span="10">
                <el-input placeholder="请输入商品编号/名称/规格型号查询"
                          v-model="params.query.name"
                          clearable
                          @clear="getProductPage">
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

            <!-- 列表区域 -->
            <el-table :data="productList"
                      ref="productTable"
                      highlight-current-row
                      border
                      stripe
                      @current-change="handleCurrentProductChange">
              <el-table-column label="商品编号"
                               prop="code"></el-table-column>
              <el-table-column label="商品名称"
                               prop="name"></el-table-column>
              <el-table-column label="规格型号"
                               prop="spec"></el-table-column>
              <el-table-column label="单位"
                               prop="unitName"></el-table-column>
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
        </el-main>
      </el-container>

      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary"
                   @click="confirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      open: false,
      defaultProps: {
        children: 'childList',
        id: 'id',
        label: 'name'
      },
      params: {
        query: {
          categoryId: ''
        },
        current: 1,
        size: 5
      },
      total: 0,
      categoryList: [
        {
          id: '',
          name: '全部'
        }
      ],
      productList: [],
      currentRow: null
    }
  },
  props: ['visible'],
  watch: {
    visible (val) {
      if (val) {
        this.open = true
      } else {
        this.open = false
      }
    }
  },
  created () {
    this.getCategoryList()
    this.getProductPage()
  },
  methods: {
    // 选择了树节点
    handleNodeClick (data) {
      this.params.query.categoryId = data.id
      this.getProductPage()
    },
    // 获取分类列表
    async getCategoryList () {
      const { data: result } = await this.$http.post('/category/list', {
        type: 30
      })
      if (!result.success) return this.$message.error(result.message)

      this.categoryList = [
        {
          id: '',
          name: '全部',
          childList: result.data.categoryList
        }
      ]
    },
    // 搜索
    search () {
      this.getProductPage()
    },
    // 清空
    clear () {
      this.params.query = {}
      this.$refs.tree.setCurrentNode({
        id: ''
      })
      this.$refs.productTable.setCurrentRow()
      this.getProductPage()
    },
    // 获取商品分页列表
    async getProductPage () {
      const { data: result } = await this.$http.post('/product/page', this.params)
      if (!result.success) return this.$message.error(result.message)

      this.productList = result.data.productPage.records

      this.total = result.data.productPage.total
    },
    // 监听 pagesize 改变的事件
    handleSizeChange (size) {
      this.params.size = size
      this.getProductPage()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (current) {
      this.params.current = current
      this.getProductPage()
    },
    // 处理选择商品
    handleCurrentProductChange (val) {
      console.log(val)
      this.currentRow = val
    },
    // 取消
    cancel () {
      this.$emit('update:visible', false)
    },
    // 保存已选择的商品
    confirm () {
      this.$emit('update:visible', false)
      this.$emit('selectProduct', this.currentRow)
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
.el-main {
  padding-top: 0;
}
</style>
