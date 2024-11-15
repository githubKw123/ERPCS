<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>销货管理</el-breadcrumb-item>
      <el-breadcrumb-item>销货单新增</el-breadcrumb-item>
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
                         placeholder="请选择客户"
                         @change="selectCustomerChanged">
                <el-option v-for="customer in customerList"
                           :key="customer.id"
                           :label="customer.name"
                           :value="customer.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="销售人员"
                          prop="sellerId">
              <el-select v-model="saveForm.sellerId"
                         placeholder="请选择销售人员">
                <el-option v-for="seller in sellerList"
                           :key="seller.id"
                           :label="seller.name"
                           :value="seller.id">
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
          <el-col :span="6">
            <el-form-item label="联系人"
                          prop="contactName">
              <el-select v-model="saveForm.contactName"
                         placeholder="请选择联系人"
                         @change="selectContactChanged">
                <el-option v-for="contact in contactList"
                           :key="contact.id"
                           :label="contact.name"
                           :value="contact.name">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="地址"
                          prop="address">
              <el-input v-model="saveForm.address"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="电话"
                          prop="phone">
              <el-input v-model="saveForm.phone"></el-input>
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
                           @click="showProductAddDialog()">新增商品</el-button>
              </el-col>
            </el-row>
            <!-- 商品列表区域 -->
            <el-table :data="saveForm.productList"
                      border
                      stripe
                      height="300">
              <el-table-column label="商品"
                               prop="productName"></el-table-column>
              <el-table-column label="单位"
                               prop="unitName"></el-table-column>
              <el-table-column label="仓库"
                               prop="warehouseName"></el-table-column>
              <el-table-column label="数量"
                               prop="quantity"></el-table-column>
              <el-table-column label="销售单价"
                               prop="price"></el-table-column>
              <el-table-column label="折扣率(%)"
                               prop="discountRate"></el-table-column>
              <el-table-column label="折扣额"
                               prop="discountAmount"></el-table-column>
              <el-table-column label="销售金额"
                               prop="amount"></el-table-column>
              <el-table-column label="序列号"
                               prop="code"></el-table-column>
              <el-table-column label="备注"
                               prop="remark"></el-table-column>
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
                               @click="showProductEditDialog(scope.row, scope.$index)"></el-button>
                  </el-tooltip>
                  <!-- 删除按钮 -->
                  <el-tooltip effect="dark"
                              content="删除"
                              placement="top"
                              :enterable="false">
                    <el-button type="danger"
                               icon="el-icon-delete"
                               size="mini"
                               @click="deleteProduct(scope.row)"></el-button>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-row>
        <el-divider content-position="left">结算信息</el-divider>
        <el-row>
          <el-col :span="6">
            <el-form-item label="优惠率"
                          prop="preferentialRate">
              <el-input v-model.number="saveForm.preferentialRate">
                <template slot="append">%</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="优惠金额"
                          prop="preferentialAmount">
              <el-input v-model="saveForm.preferentialAmount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="优惠后金额"
                          prop="preferredAmount">
              <el-input v-model="saveForm.preferredAmount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户承担费用"
                          prop="customerFee">
              <el-input v-model="saveForm.customerFee"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="本次收款"
                          prop="currentAmount">
              <el-input v-model="saveForm.currentAmount"></el-input>
            </el-form-item>
          </el-col>

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
            <el-form-item label="本次欠款"
                          prop="debtAmount">
              <el-input v-model="saveForm.debtAmount"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="销售附件"
                          prop="attachments">
              <el-input v-model="saveForm.attachments"
                        disabled></el-input>
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
      <el-row>
        <el-col :offset="20">
          <el-button @click="save">保存</el-button>
          <el-button type="primary"
                     @click="saveThenNew">保存并新增</el-button>
        </el-col>

      </el-row>
    </el-card>

    <!-- 保存商品对话框 -->
    <el-dialog :title="isProductAdd ? '新增商品' : '修改商品'"
               :visible.sync="saveProductDialogVisible"
               width="70%"
               @close="saveProductDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="saveProductForm"
               :rules="saveProductFormRules"
               ref="saveProductFormRef"
               label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="商品"
                          prop="productName">
              <el-input placeholder="请选择商品"
                        v-model="saveProductForm.productName"
                        disabled>
                <el-button slot="append"
                           icon="el-icon-search"
                           @click="showSelectProductDialog()"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="单位"
                          prop="unitName">
              <el-input v-model="saveProductForm.unitName"
                        disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="仓库"
                          prop="warehouseId">
              <el-select v-model="saveProductForm.warehouseId"
                         placeholder="请选择仓库"
                         @change="selectWarehouseChanged">
                <el-option v-for="warehouse in warehouseList"
                           :key="warehouse.id"
                           :label="warehouse.name"
                           :value="warehouse.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="数量"
                          prop="quantity">
              <el-input v-model="saveProductForm.quantity"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="销售单价"
                          prop="price">
              <el-input v-model="saveProductForm.price"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="折扣率(%)"
                          prop="discountRate">
              <el-input v-model="saveProductForm.discountRate"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="折扣额"
                          prop="discountAmount">
              <el-input v-model="saveProductForm.discountAmount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="销售金额"
                          prop="amount">
              <el-input v-model="saveProductForm.amount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="序列号"
                          prop="code">
              <el-input v-model="saveProductForm.code"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="备注信息"
                          prop="remark">
              <el-input type="textarea"
                        :rows="2"
                        placeholder="添加备注信息"
                        v-model="saveProductForm.remark">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="saveProductDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="saveProduct">确 定</el-button>
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
      // 客户列表
      customerList: [],
      sellerList: [],
      contactList: [],
      accountList: [],
      saveForm: {
        type: 'sell',
        preferentialRate: 0,
        preferentialAmount: 0.0,
        preferredAmount: 0,
        customerFee: 0.0,
        currentAmount: 0.0,
        debtAmount: 0.0,
        productList: [],
        accountList: []
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
    let saleId = this.$route.query.saleId
    if (saleId !== undefined) {
      console.log(saleId)
      this.getDetail(saleId)
    } else {
      this.getCode()
    }

    this.getCustomerList()
    this.getSellerList()
    this.getAccountList()
    this.getWarehouseList()
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
    // 获取销售人员列表
    async getSellerList() {
      const { data: result } = await this.$http.post('/employee/page', {
        current: 1,
        size: 10000
      })
      if (!result.success) return this.$message.error(result.message)

      this.sellerList = result.data.employeePage.records
    },
    // 获取单据编号
    async getCode() {
      const { data: result } = await this.$http.post('/sale/createCode', {
        type: 'sell'
      })
      if (!result.success) return this.$message.error(result.message)

      this.saveForm = {
        id: '',
        type: 'sell',
        preferentialRate: 0,
        preferentialAmount: 0.0,
        preferredAmount: 0,
        customerFee: 0.0,
        currentAmount: 0.0,
        debtAmount: 0.0,
        productList: [],
        accountList: []
      }

      this.saveForm.code = result.data.code
    },
    // 获取结算账户列表
    async getAccountList() {
      const { data: result } = await this.$http.post('/settlementAccount/list')
      if (!result.success) return this.$message.error(result.message)

      this.accountList = result.data.accountList
    },
    // 获取详情
    async getDetail(id) {
      const { data: result } = await this.$http.post('/sale/detail', {
        saleId: id
      })
      if (!result.success) return this.$message.error(result.message)

      console.log(result.data)
      this.saveForm = result.data.sale
    },

    // 获取客户联系人列表
    async getContactList(customerId) {
      const { data: result } = await this.$http.post('/customer/contactList', {
        customerId: customerId
      })
      if (!result.success) return this.$message.error(result.message)

      this.contactList = result.data.contactList
    },

    // 选择了客户
    selectCustomerChanged(customerId) {
      this.getContactList(customerId)
    },

    // 选择了联系人
    selectContactChanged(contactName) {
      console.log('contactName', contactName)
      let index = this.$_.findIndex(this.contactList, { name: contactName })
      // this.saveForm.address = contact.address
      // this.saveForm.phone = contact.mobile
      console.log(this.contactList[index])
    },

    // 选择了结算账户
    selectAccountChanged(accountId) {
      this.saveForm.accountList = []
      var account = {
        accountId
      }
      this.saveForm.accountList.push(account)
    },
    // 点击按钮，保存购货单
    async save() {
      this.$refs.saveFormRef.validate(async (valid) => {
        if (!valid) return
        // 可以发起新增购货单的网络请求
        const { data: result } = await this.$http.post('/sale/save', {
          sale: this.saveForm,
          productList: this.saveForm.productList,
          accountList: this.saveForm.accountList
        })
        if (!result.success) {
          return this.$message.error(result.message)
        }

        this.$message.success('保存销货单成功！')
        console.log(result)
        this.saveForm.id = result.data.sale.id
      })
    },
    // 保存并新增
    async saveThenNew() {
      this.save()
      this.getCode()
    },
    // 显示保存商品对话框
    showProductAddDialog() {
      this.isProductAdd = true
      this.saveProductForm = {
        quantity: 1.0,
        price: 0.0,
        discountRate: 0,
        discountAmount: 0.0,
        amount: 0.0
      }
      this.saveProductDialogVisible = true
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
