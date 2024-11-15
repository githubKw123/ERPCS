<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础设定</el-breadcrumb-item>
      <el-breadcrumb-item>车间管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20"
              class="query">
        <el-col :span="4">
          <el-select v-model="params.query.categoryId"
                     placeholder="请选择车间类别"
                     filterable
                     clearable>
            <el-option v-for="category in categoryList"
                       :key="category.id"
                       :label="category.name"
                       :value="category.id">
              <span style="float: left">{{ category.name }}</span>
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-input placeholder="请输入车间编号/名称/联系人/电话查询"
                    v-model="params.query.name"
                    clearable
                    @clear="getCustomerPage">
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
                     @click="showAddDialog()">新增</el-button>
        </el-col>
      </el-row>

      <!-- 列表区域 -->
      <el-table :data="customerList"
                border
                stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="车间类别"
                         prop="categoryName"></el-table-column>
        <el-table-column label="车间编号"
                         prop="code"></el-table-column>
        <el-table-column label="车间名称"
                         prop="name"></el-table-column>
        <el-table-column label="车间负责人"
                         prop="contactName"></el-table-column>
        <el-table-column label="手机"
                         prop="mobile"></el-table-column>
        <el-table-column label="座机"
                         prop="phone"></el-table-column>
        <el-table-column label="职位"
                         prop="position"></el-table-column>
        <el-table-column label="QQ/微信"
                         prop="qq"></el-table-column>
        <el-table-column label="是否启用"
                         width="70px">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.active"
                       @change="customerStateChanged(scope.row)">
            </el-switch>
          </template>
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
                         @click="showEditDialog(scope.row.id)"></el-button>
            </el-tooltip>
            <!-- 删除按钮 -->
            <el-tooltip effect="dark"
                        content="删除"
                        placement="top"
                        :enterable="false">
              <el-button type="danger"
                         icon="el-icon-delete"
                         size="mini"
                         @click="deleteCustomer(scope.row.id)"></el-button>
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

    <!-- 保存客户对话框 -->
    <el-dialog :title="isAdd ? '新增车间' : '修改车间'"
               :visible.sync="saveDialogVisible"
               width="70%"
               @close="saveDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="saveForm"
               :rules="saveFormRules"
               ref="saveFormRef"
               label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="车间编号"
                          prop="code">
              <el-input v-model="saveForm.code"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="车间名称"
                          prop="name">
              <el-input v-model="saveForm.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="车间类别"
                          prop="categoryId">
              <el-select v-model="saveForm.categoryId"
                         placeholder="请选择"
                         clearable>
                <el-option v-for="category in categoryList"
                           :key="category.id"
                           :label="category.name"
                           :value="category.id">
                </el-option>
              </el-select>
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
                        v-model="saveForm.remark">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 联系人表格 -->
          <el-card>
            <!-- 搜索与添加区域 -->
            <el-row :gutter="20">
              <el-col :span="4">
                <el-button type="primary"
                           @click="showContactAddDialog()">新增负责人</el-button>
              </el-col>
            </el-row>
            <!-- 联系人列表区域 -->
            <el-table :data="saveForm.contactList"
                      border
                      stripe
                      height="200">
              <el-table-column label="车间负责人"
                               prop="name"></el-table-column>
              <el-table-column label="手机"
                               prop="mobile"></el-table-column>
              <el-table-column label="座机"
                               prop="phone"></el-table-column>
              <el-table-column label="职位"
                               prop="position"></el-table-column>
              <el-table-column label="QQ/微信"
                               prop="qq"></el-table-column>
              <el-table-column label="联系地址"
                               prop="address"></el-table-column>
              <el-table-column label="首要联系人"
                               width="100px">
                <template slot-scope="scope">
                  <el-switch v-model="scope.row.primary"
                             @change="contactPrimaryChanged(scope.row)">
                  </el-switch>
                </template>
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
                               @click="showContactEditDialog(scope.row, scope.$index)"></el-button>
                  </el-tooltip>
                  <!-- 删除按钮 -->
                  <el-tooltip effect="dark"
                              content="删除"
                              placement="top"
                              :enterable="false">
                    <el-button type="danger"
                               icon="el-icon-delete"
                               size="mini"
                               @click="deleteContact(scope.row)"></el-button>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-row>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="saveDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="saveCustomer">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 保存联系人对话框 -->
    <el-dialog :title="isContactAdd ? '新增负责人' : '修改负责人'"
               :visible.sync="saveContactDialogVisible"
               width="70%"
               @close="saveContactDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="saveContactForm"
               :rules="saveContactFormRules"
               ref="saveContactFormRef"
               label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="负责人"
                          prop="name">
              <el-input v-model="saveContactForm.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机"
                          prop="mobile">
              <el-input v-model="saveContactForm.mobile"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="座机"
                          prop="phone">
              <el-input v-model="saveContactForm.phone"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="职位"
                          prop="position">
              <el-input v-model="saveContactForm.position"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="QQ/微信"
                          prop="qq">
              <el-input v-model="saveContactForm.qq"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系地址"
                          prop="address">
              <el-input v-model="saveContactForm.address"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="首要负责人"
                          prop="primary">
              <el-switch v-model="saveContactForm.primary">
              </el-switch>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="saveContactDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="saveContact">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 获取客户列表的参数对象
      params: {
        query: {},
        // 当前的页数
        current: 1,
        // 当前每页显示多少条数据
        size: 5
      },
      total: 0,
      categoryList: [],
      levelList: [],
      customerList: [],

      isAdd: true,
      // 控制添加账户对话框的显示与隐藏
      saveDialogVisible: false,
      // 添加客户的表单数据
      saveForm: {
        contactList: [],
        level: '1294753669664813058'
      },

      // 添加表单的验证规则对象
      saveFormRules: {
        code: [{ required: true, message: '请输入编码', trigger: 'blur' }],
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        categoryId: [{ required: true, message: '请选择分类', trigger: 'blur' }]
      },
      // 是否新增联系人
      isContactAdd: true,
      modifyContactIndex: 0,
      saveContactDialogVisible: false,
      saveContactForm: {},
      // 添加表单的验证规则对象
      saveContactFormRules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }]
      }
    }
  },
  watch: {
    'saveForm.beginReceivableAmountDisplay'(newVal, oldVal) {
      if (newVal === undefined || newVal === null || newVal === '') {
        this.saveForm.beginReceivableAmount = 0
        return
      }
      this.saveForm.beginReceivableAmount = parseInt(parseFloat(newVal) * 100)
      if (isNaN(this.saveForm.beginReceivableAmount)) {
        this.$message.error('期初应收款不正确！')
      }
    },
    'saveForm.beginPrepaidAmountDisplay'(newVal, oldVal) {
      if (newVal === undefined || newVal === null || newVal === '') {
        this.saveForm.beginPrepaidAmount = 0
        return
      }
      this.saveForm.beginPrepaidAmount = parseInt(parseFloat(newVal) * 100)
      if (isNaN(this.saveForm.beginPrepaidAmount)) {
        this.$message.error('期初预收款不正确！')
      }
    }
  },
  created() {
    this.getCategoryList()
    this.getLevelList()

    this.getCustomerPage()
  },
  methods: {
    // 获取分类列表
    async getCategoryList() {
      const { data: result } = await this.$http.post('/category/list', {
        type: 10
      })
      if (!result.success) return this.$message.error(result.message)
      this.categoryList = result.data.categoryList
    },
    // 获取客户等级列表
    async getLevelList() {
      const { data: result } = await this.$http.post('/dict/itemList', {
        dictCode: 'customer_level'
      })
      if (!result.success) return this.$message.error(result.message)

      this.levelList = result.data.itemList
    },
    // 搜索
    search() {
      this.getCustomerPage()
    },
    // 清空
    clear() {
      this.params.query = {}
      this.getCustomerPage()
    },
    // 获取客户分页列表
    async getCustomerPage() {
      const { data: result } = await this.$http.post('/customer/page', this.params)
      if (!result.success) return this.$message.error(result.message)

      this.customerList = result.data.customerPage.records

      this.total = result.data.customerPage.total
    },
    // 监听 pagesize 改变的事件
    handleSizeChange(size) {
      this.params.size = size
      this.getCustomerPage()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange(current) {
      this.params.current = current
      this.getCustomerPage()
    },
    // 客户状态改变
    async customerStateChanged(customer) {
      const { data: result } = await this.$http.post('/customer/switchActive', {
        customerId: customer.id
      })
      if (!result.success) {
        customer.active = !customer.active
        return this.$message.error('更新车间状态失败！')
      }
      customer.active = result.data.customer.active
      this.$message.success('更新车间状态成功！')
    },
    // 显示新增客户对话框
    showAddDialog() {
      this.isAdd = true
      this.saveDialogVisible = true
    },
    // 显示修改客户对话框
    async showEditDialog(customerId) {
      const { data: result } = await this.$http.post('/customer/detail', {
        customerId
      })
      if (!result.success) return this.$message.error(result.message)

      // 先给customer对象添加属性，再绑定到表单上
      let customer = result.data.customer

      this.saveForm = customer

      this.isAdd = false
      this.saveDialogVisible = true
    },
    // 监听新增对话框的关闭事件
    saveDialogClosed() {
      this.$refs.saveFormRef.resetFields()
      this.saveForm = {
        contactList: []
      }
    },
    // 点击按钮，添加客户
    async saveCustomer() {
      this.$refs.saveFormRef.validate(async (valid) => {
        if (!valid) return
        // 可以发起新增客户的网络请求
        const { data: result } = await this.$http.post('/customer/save', {
          customer: this.saveForm,
          contactList: this.saveForm.contactList
        })
        if (!result.success) {
          return this.$message.error(result.message)
        }

        this.$message.success('保存车间成功！')
        // 隐藏添加客户的对话框
        this.saveDialogVisible = false
        // 重新获取客户列表数据
        this.getCustomerPage()
      })
    },
    // 删除客户
    async deleteCustomer(customerId) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm('此操作将永久删除该车间，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch((err) => err)

      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消了删除，则返回值为字符串 cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }

      const { data: result } = await this.$http.post('/customer/delete', { customerId })

      if (!result.success) {
        return this.$message.error('删除车间失败！')
      }

      this.$message.success('删除车间成功！')
      this.getCustomerPage()
    },

    // 显示保存联系人对话框
    showContactAddDialog() {
      this.isContactAdd = true
      this.saveContactForm = {}
      this.saveContactDialogVisible = true
    },
    // 显示修改联系人对话框
    showContactEditDialog(contact, index) {
      this.isContactAdd = false
      this.saveContactForm = this.$_.cloneDeep(contact)
      this.modifyContactIndex = index
      this.saveContactDialogVisible = true
    },

    // 监听新增联系人对话框的关闭事件
    saveContactDialogClosed() {
      if (this.isContactAdd) {
        this.$refs.saveContactFormRef.resetFields()
      }
    },
    // 点击按钮，添加联系人
    saveContact() {
      this.$refs.saveContactFormRef.validate(async (valid) => {
        if (!valid) return

        var contact = this.$_.cloneDeep(this.saveContactForm)

        // 处理首要联系人
        if (this.saveForm.contactList == null || this.saveForm.contactList.length === 0) {
          contact.primary = true
        } else if (contact.primary) {
          this.saveForm.contactList.forEach((existedContact) => {
            existedContact.primary = false
          })
        }

        // 修改
        if (!this.isContactAdd) {
          this.saveForm.contactList.splice(this.modifyContactIndex, 1, contact)

          this.$message.success('修改负责人成功！')
        } else {
          // 新增
          this.saveForm.contactList.push(contact)
          this.$message.success('添加负责人成功！')
        }

        // 隐藏添加联系人的对话框
        this.saveContactDialogVisible = false
      })
    },
    // 主要联系人改变
    async contactPrimaryChanged(contact) {
      if (contact.primary) {
        this.saveForm.contactList.forEach((existedContact) => {
          existedContact.primary = false
        })

        contact.primary = true
      }

      this.$message.success('更新车间状态成功！')
    },
    // 删除联系人
    async deleteContact(contact) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm('删除该负责人，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch((err) => err)

      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消了删除，则返回值为字符串 cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }

      this.saveForm.contactList.splice(
        this.saveForm.contactList.findIndex((existedContact) => existedContact === contact),
        1
      )

      this.$message.success('删除负责人成功！')
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
