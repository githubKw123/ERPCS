<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>生产管理</el-breadcrumb-item>
      <el-breadcrumb-item>生产指令新增</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <el-form :model="saveForm"
               :rules="saveFormRules"
               ref="saveFormRef"
               label-width="100px">
        <div style="text-align: center;">
          <h1 style="font-size: 25px;font-weight: bolder;color: black;margin-bottom: 0px">生产指令单</h1>
          <el-divider></el-divider>
        </div>
        <el-row>
          <el-col :span="8">
            <el-form-item label="生产车间"
                          prop="customerId">
              <el-select v-model="saveForm.customerId"
                         placeholder="请选择车间">
                <el-option v-for="customer in customerList"
                           :key="customer.id"
                           :label="customer.name"
                           :value="customer.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="单据日期"
                          prop="issueDate">
              <el-date-picker v-model="saveForm.issueDate"
                              type="date"
                              value-format='yyyy-MM-dd'
                              placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="单据编号"
                          prop="code">
              <el-input v-model="saveForm.code"
                        disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="生产产品"
                          prop="categoryId">
              <el-select v-model="saveForm.categoryId"
                         placeholder="请选择产品"
                         filterable
                         clearable>
                <el-option v-for="category in categoryList"
                           :key="category.id"
                           :label="category.name"
                           :value="category.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产数量"
                          prop="totalAmount">
              <el-input v-model="saveForm.totalAmount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="制单人"
                          prop="listerId">
              <el-input v-model="saveForm.listerId"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 商品表格 -->
          <el-card>
            <!-- 搜索与添加区域 -->
            <el-row :gutter="20">
              <el-col :span="4">
                <el-button type="primary"
                           @click="showProductAddDialog()">新增物料</el-button>
              </el-col>
            </el-row>
            <!-- 商品列表区域 -->
            <el-table :data="saveForm.productList"
                      border
                      stripe>
              <el-table-column label="操作"
                               width="60px">
                <template slot-scope="scope">
                  <el-tooltip effect="dark"
                              content="新增行"
                              placement="top"
                              :enterable="false">
                    <i class="el-icon-plus"
                       @click="addProductRow(scope.$index, scope.row)"></i>
                  </el-tooltip>
                  <el-tooltip effect="dark"
                              content="删除行"
                              placement="top"
                              :enterable="false">
                    <i class="el-icon-delete"
                       @click="deleteProductRow(scope.$index, scope.row)"></i>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column label="零部件编号"
                               width="120"
                               prop="sccode">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.sccode"
                            disabled></el-input>
                </template>
              </el-table-column>
              <el-table-column label="零部件名称"
                               width="180"
                               prop="productName">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.productName"
                            disabled></el-input>
                </template>
              </el-table-column>
              <el-table-column label="零部件型号"
                               width="200"
                               prop="spec">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.spec"
                            disabled></el-input>
                </template>
              </el-table-column>
              <el-table-column label="零部件单位"
                               width="200"
                               prop="unitName">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.unitName"
                            disabled></el-input>
                </template>
              </el-table-column>
              <el-table-column label="需求数量"
                               width="80px"
                               prop="amount">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.amount"
                            @change="handleproductEdit(scope.$index, scope.row)"></el-input>
                </template>
              </el-table-column>
              <el-table-column label="备注"
                               prop="remark">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.remark"
                            @change="handleproductEdit(scope.$index, scope.row)"></el-input>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-row>
      </el-form>
      <el-row>
        <el-col :offset="20"
        style="margin-top: 20px">
          <el-button @click="saveOrder">保存</el-button>
          <el-button type="primary"
                     @click="saveOrderThenNew">保存并新增</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 保存商品对话框 -->
    <el-dialog title='选择源单'
               :visible.sync="selectSourceIssueDialogVisible"
               width="70%"
               @close="selectSourceIssueDialogClosed">
      <!-- 列表区域 -->
      <el-table :data="purchaseList"
                border
                stripe
                @selection-change="handlePurchaseSelectionChange">
        <el-table-column type="selection"
                         width="55">
        </el-table-column>
        <el-table-column label="零部件编号"
                         prop="code"></el-table-column>
        <el-table-column label="零部件名称"
                         prop="productName"></el-table-column>
        <el-table-column label="零部件型号"
                         prop="spec"></el-table-column>
        <el-table-column label="零部件单位"
                         prop="unitName"></el-table-column>
      </el-table>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="selectSourceIssueDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="confirmSelectSourceIssueDialog">确 定</el-button>
      </span>
    </el-dialog>
    <select-product-dialog :visible.sync="selectProductDialogVisible"
                           @selectProduct="handleSelectProduct($event)"></select-product-dialog>
  </div>
</template>

<script>
import Vue from 'vue'
import SelectProductDialog from '../common/SelectProductDialog'
Vue.component('select-product-dialog', SelectProductDialog)

export default {
  data() {
    return {
      // 客户
      saleList: [],
      categoryList: [],
      customerList: [],
      orderCode: '',
      saveForm: {
        discountRate: 0,
        discountedAmount: 0,
        totalAmount: 0,
        listerId: 'admin',
        productList: [{}, {}, {}],
        businessType: 10
      },
      saveFormRules: {},
      // 是否新增商品
      isProductAdd: true,
      modifyProductIndex: 0,
      saveProductDialogVisible: false,
      saveProductForm: {
        quantity: 1.0,
        price: 0.0,
        discountRate: 0,
        discountAmount: 0.0,
        amount: 0.0
      },
      // 添加表单的验证规则对象
      saveProductFormRules: {
        productName: [{ required: true, message: '请选择商品', trigger: 'blur' }],
        warehouseId: [{ required: true, message: '请选择仓库', trigger: 'blur' }]
      },
      selectProductDialogVisible: false,
      productParams: {
        query: {
          categoryId: ''
        },
        current: 1,
        size: 5
      },
      warehouseList: []
    }
  },
  created() {
    let orderId = this.$route.query.orderId
    if (orderId !== undefined) {
      console.log(orderId)
      this.getOrderDetail(orderId)
    } else {
      this.getOrderCode()
    }
    this.getCategoryList()
    this.getCustomerList()
    this.getWarehouseList()
  },
  methods: {
    // 获取分类列表
    async getCategoryList () {
      const { data: result } = await this.$http.post('/category/list', {
        type: 30
      })
      if (!result.success) return this.$message.error(result.message)
      this.categoryList = result.data.categoryList
    },
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
    async getOrderCode() {
      const { data: result } = await this.$http.post('/order/createCode')
      if (!result.success) return this.$message.error(result.message)

      this.saveForm = {
        discountRate: 0,
        discountedAmount: 0,
        totalAmount: 0,
        listerId: 'admin',
        productList: [{}, {}, {}],
        businessType: 10
      }

      console.log(result.data.code)
      this.orderCode = result.data.code
      this.saveForm.code = this.orderCode
    },
    // 获取详情
    async getOrderDetail(orderId) {
      const { data: result } = await this.$http.post('/order/detail', {
        orderId
      })
      if (!result.success) return this.$message.error(result.message)

      console.log(result.data)
      this.saveForm = result.data.order
    },
    // 点击按钮，保存客户订单
    async saveOrder() {
      this.$refs.saveFormRef.validate(async (valid) => {
        if (!valid) return
        let persistProductList = []
        this.saveForm.productList.forEach((product, index) => {
          if (product.sccode !== undefined) {
            persistProductList.push(product)
          }
        })
        // 可以发起新增客户订单的网络请求
        const { data: result } = await this.$http.post('/order/save', {
          order: this.saveForm,
          productList: persistProductList
        })
        if (!result.success) {
          return this.$message.error(result.message)
        }

        this.$message.success('保存生产指令单成功！')
      })
    },
    // 保存并新增
    async saveOrderThenNew() {
      this.saveOrder()
      this.getOrderCode()
    },
    // 显示保存商品对话框
    async showProductAddDialog() {
      let categoryId = this.saveForm.categoryId
      if (categoryId === undefined) {
        return this.$message.warning('请先选择生产产品')
      }
      const { data: result } = await this.$http.post('/product/findbyid', {
        categoryId
      })
      if (!result.success) {
        return this.$message.error(result.message)
      }
      this.saleList = result.data.productList
      console.log(this.saleList)
      let emptyIssueIndexList = []
      this.saveForm.productList.forEach((product, index) => {
        if (product.code === undefined) {
          emptyIssueIndexList.push(index)
        }
      })
      this.saleList.forEach((sale) => {
        let newProduct = {
          productId: sale.id,
          warehouseId: sale.primaryWarehouseId,
          sccode: sale.code,
          productName: sale.name,
          spec: sale.spec,
          unitName: sale.unitName
        }
        if (emptyIssueIndexList.length === 0) {
          this.saveForm.productList.push(newProduct)
        } else {
          let index = emptyIssueIndexList[0]
          this.saveForm.productList.splice(index, 1, newProduct)
          emptyIssueIndexList.shift()
        }
      })
      console.log(this.saveForm.productList)
      this.saleList = []
    },
    // 显示修改商品对话框
    showProductEditDialog(contact, index) {
      this.isProductAdd = false
      this.saveProductForm = this.$_.cloneDeep(contact)
      this.modifyProductIndex = index
      this.saveProductDialogVisible = true
    },

    // 监听新增商品对话框的关闭事件
    saveProductDialogClosed() {
      if (this.isProductAdd) {
        this.$refs.saveProductFormRef.resetFields()
      }
    },
    // 点击按钮，添加商品
    saveProduct() {
      this.$refs.saveProductFormRef.validate(async (valid) => {
        if (!valid) return

        var product = this.$_.cloneDeep(this.saveProductForm)

        // 修改
        if (!this.isProductAdd) {
          this.saveForm.productList.splice(this.modifyProductIndex, 1, product)

          this.$message.success('修改商品成功！')
        } else {
          // 新增
          this.saveForm.productList.push(product)
          this.$message.success('添加商品成功！')
        }

        // 隐藏添加商品的对话框
        this.saveProductDialogVisible = false
      })
    },
    // 删除商品
    async deleteProduct(product) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm('删除该商品，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch((err) => err)

      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消了删除，则返回值为字符串 cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }

      this.saveForm.productList.splice(
        this.saveForm.productList.findIndex((existedProduct) => existedProduct === product),
        1
      )

      this.$message.success('删除商品成功！')
    },
    // 显示选择商品对话框
    showSelectProductDialog() {
      this.selectProductDialogVisible = true
    },
    // 选择好商品
    handleSelectProduct(product) {
      if (product != null) {
        this.saveProductForm.productName = product.name
        this.saveProductForm.productId = product.id
        this.saveProductForm.unitName = product.unitName
      }
    },
    // 获取仓库列表
    async getWarehouseList() {
      const { data: result } = await this.$http.post('/warehouse/page', {
        current: 1,
        size: 10000
      })
      if (!result.success) return this.$message.error(result.message)

      this.warehouseList = result.data.warehousePage.records
    },
    // 选择了仓库
    selectWarehouseChanged(warehouseId) {
      var warehouse = this.$_.find(this.warehouseList, { id: warehouseId })
      if (warehouse !== undefined) {
        this.saveProductForm.warehouseName = warehouse.name
      }
    },
    handleproductEdit(index, row) {
      this.saveForm.productList[index] = row
      console.log(this.saveForm.productList)
    },
    addProductRow(index, row) {
      console.log(index)
      console.log(row)
      this.saveForm.productList.splice(index + 1, 0, {})
    },
    // 删除账户行
    deleteProductRow(index) {
      if (this.saveForm.productList.length <= 1) {
        return this.$message.warning('至少保留一条记录')
      }
      this.saveForm.productList.splice(index, 1)
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
