<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
    </el-breadcrumb>

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
            <el-col :span="8">
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
          <el-row :gutter="20">
            <el-col :span="1.5">
              <el-button type="primary"
                         @click="showAddDialog()">新增</el-button>
            </el-col>
          </el-row>

          <!-- 列表区域 -->
          <el-table :data="productList"
                    border
                    stripe>
            <el-table-column type="index"></el-table-column>
            <el-table-column label="产品类别"
                             prop="categoryName"
            width="150px"></el-table-column>
            <el-table-column label="商品编号"
                             prop="code"></el-table-column>
            <el-table-column label="商品名称"
                             prop="name"></el-table-column>
            <el-table-column label="规格型号"
                             prop="spec"></el-table-column>
            <el-table-column label="单位"
                             prop="unitName"></el-table-column>
            <el-table-column label="预计零售价"
                             prop="retailPrice"></el-table-column>
            <el-table-column label="预计批发价"
                             prop="wholesalePrice"></el-table-column>
            <el-table-column label="备注"
                             prop="remark"></el-table-column>
            <el-table-column label="是否启用"
                             width="70px">
              <template slot-scope="scope">
                <el-switch v-model="scope.row.active"
                           @change="productStateChanged(scope.row)">
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
                             @click="deleteProduct(scope.row.id)"></el-button>
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
      </el-main>
    </el-container>

    <!-- 保存商品对话框 -->
    <el-dialog :title="isAdd ? '新增商品' : '修改商品'"
               :visible.sync="saveDialogVisible"
               width="70%"
               @close="saveDialogClosed">
      <el-form :model="saveForm"
               :rules="saveFormRules"
               ref="saveFormRef"
               label-width="100px">
        <el-tabs type="border-card">
          <el-tab-pane label="基础资料">
            <!-- 内容主体区域 -->
            <el-row>
              <el-col :span="8">
                <el-form-item label="商品编号"
                              prop="code">
                  <el-input v-model="saveForm.code"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="商品名称"
                              prop="name">
                  <el-input v-model="saveForm.name"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="商品条码"
                              prop="barcode">
                  <el-input v-model="saveForm.barcode"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="规格型号"
                              prop="spec">
                  <el-input v-model="saveForm.spec"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="产品类别"
                              prop="categoryId">
                  <el-select v-model="saveForm.categoryId"
                             placeholder="请选择产品类别"
                             filterable
                             clearable>
                    <el-option v-for="category in categoryList"
                               :key="category.id"
                               :label="category.name"
                               :value="category.id">
                      <span style="float: left">{{ category.name }}</span>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="首选仓库"
                              prop="primaryWarehouseId">
                  <el-select v-model="saveForm.primaryWarehouseId"
                             placeholder="(空)"
                             clearable>
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
                <el-form-item label="计量单位"
                              prop="unitId">
                  <el-select v-model="saveForm.unitId"
                             placeholder="(空)"
                             clearable>
                    <el-option v-for="unit in unitList"
                               :key="unit.id"
                               :label="unit.name"
                               :value="unit.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-divider content-position="left">价格策略</el-divider>
            <el-row>
              <el-col :span="8">
                <el-form-item label="预计零售价"
                              prop="retailPrice">
                  <el-input v-model="saveForm.retailPrice"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="预计批发价"
                              prop="wholesalePrice">
                  <el-input v-model="saveForm.wholesalePrice"></el-input>
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

          </el-tab-pane>
          <el-tab-pane label="库存预警">
            <el-row>
              <el-col :span="8">
                <el-form-item label="最低库存"
                              prop="minimumStock">
                  <el-input v-model="saveForm.minimumStock"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="最高库存"
                              prop="maximumStock">
                  <el-input v-model="saveForm.maximumStock"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="saveDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="saveProduct">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 保存联系人对话框 -->
    <el-dialog :title="isContactAdd ? '新增联系人' : '修改联系人'"
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
            <el-form-item label="联系人"
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
            <el-form-item label="首要联系人"
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
  data () {
    return {
      defaultProps: {
        children: 'childList',
        id: 'id',
        label: 'name'
      },
      // 获取职员列表的参数对象
      params: {
        query: {
          categoryId: ''
        },
        // 当前的页数
        current: 1,
        // 当前每页显示多少条数据
        size: 5
      },
      total: 0,
      categoryList: [
        {
          id: '',
          name: '全部'
        }
      ],
      warehouseList: [],
      unitList: [],
      levelList: [],
      productList: [],

      isAdd: true,
      // 控制添加账户对话框的显示与隐藏
      saveDialogVisible: false,
      // 添加供应商的表单数据
      saveForm: {
        categoryId: null,
        vatRate: 17,
        contactList: []
      },

      // 添加表单的验证规则对象
      saveFormRules: {
        code: [
          { required: true, message: '请输入编码', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      },
      // 是否新增联系人
      isContactAdd: true,
      modifyContactIndex: 0,
      saveContactDialogVisible: false,
      saveContactForm: {
      },
      // 添加表单的验证规则对象
      saveContactFormRules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    'saveForm.beginReceivableAmountDisplay' (newVal, oldVal) {
      if (newVal === undefined || newVal === null || newVal === '') {
        this.saveForm.beginReceivableAmount = 0
        return
      }
      this.saveForm.beginReceivableAmount = parseInt(parseFloat(newVal) * 100)
      if (isNaN(this.saveForm.beginReceivableAmount)) {
        this.$message.error('期初应收款不正确！')
      }
    },
    'saveForm.beginPrepaidAmountDisplay' (newVal, oldVal) {
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
  created () {
    this.getCategoryList()
    this.getWarehouseList()
    this.getUnitList()

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
      this.categoryList = result.data.categoryList
    },
    // 获取仓库列表
    async getWarehouseList () {
      const { data: result } = await this.$http.post('/warehouse/page', {
        current: 1,
        size: 10000
      })
      if (!result.success) return this.$message.error(result.message)

      this.warehouseList = result.data.warehousePage.records
    },
    // 获取计量单位列表
    async getUnitList () {
      const { data: result } = await this.$http.post('/dict/itemList', {
        dictCode: 'unit'
      })
      if (!result.success) return this.$message.error(result.message)

      this.unitList = result.data.itemList
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
    // 商品状态改变
    async productStateChanged (product) {
      const { data: result } = await this.$http.post(
        '/product/switchActive',
        {
          'productId': product.id
        }
      )
      if (!result.success) {
        product.active = !product.active
        return this.$message.error('更新商品状态失败！')
      }
      product.active = result.data.product.active
      this.$message.success('更新商品状态成功！')
    },
    // 处理类别树下拉框节点选中
    handleCategoryNodeClick (node) {
      this.saveForm.categoryId = node.id
      this.saveForm.categoryName = node.name
      this.$refs.selectCategory.blur()
    },
    // 处理类别选择清空
    handleCategorySelectClear () {
      this.saveForm.categoryId = null
      this.saveForm.categoryName = ''
    },

    // 显示新增商品对话框
    showAddDialog () {
      this.isAdd = true
      this.saveDialogVisible = true
    },
    // 显示修改商品对话框
    async showEditDialog (productId) {
      const { data: result } = await this.$http.post('/product/detail', {
        productId
      })
      if (!result.success) return this.$message.error(result.message)

      this.saveForm = result.data.product

      console.log(this.saveForm)

      this.isAdd = false
      this.saveDialogVisible = true
    },
    // 监听新增对话框的关闭事件
    saveDialogClosed () {
      this.$refs.saveFormRef.resetFields()
      this.saveForm = {
        categoryId: null,
        vatRate: 17,
        contactList: []
      }
    },
    // 点击按钮，添加商品
    async saveProduct () {
      this.$refs.saveFormRef.validate(async valid => {
        if (!valid) return
        // 可以发起保存商品的网络请求
        const { data: result } = await this.$http.post('/product/save', {
          product: this.saveForm
        })
        if (!result.success) {
          return this.$message.error(result.message)
        }

        this.$message.success('保存商品成功！')
        // 隐藏添加商品的对话框
        this.saveDialogVisible = false
        // 重新获取商品列表数据
        this.getProductPage()
      })
    },
    // 删除商品
    async deleteProduct (productId) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
        '此操作将永久删除该商品，是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)

      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消了删除，则返回值为字符串 cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }

      const { data: result } = await this.$http.post('/product/delete', { productId })

      if (!result.success) {
        return this.$message.error('删除商品失败！')
      }

      this.$message.success('删除商品成功！')
      this.getProductPage()
    },

    // 显示保存联系人对话框
    showContactAddDialog () {
      this.isContactAdd = true
      this.saveContactForm = {}
      this.saveContactDialogVisible = true
    },
    // 显示修改联系人对话框
    showContactEditDialog (contact, index) {
      this.isContactAdd = false
      this.saveContactForm = this.$_.cloneDeep(contact)
      this.modifyContactIndex = index
      this.saveContactDialogVisible = true
    },

    // 监听新增联系人对话框的关闭事件
    saveContactDialogClosed () {
      if (this.isContactAdd) {
        this.$refs.saveContactFormRef.resetFields()
      }
    },
    // 点击按钮，添加联系人
    saveContact () {
      this.$refs.saveContactFormRef.validate(async valid => {
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

          this.$message.success('修改联系人成功！')
        } else {
          // 新增
          this.saveForm.contactList.push(contact)
          this.$message.success('添加联系人成功！')
        }

        // 隐藏添加联系人的对话框
        this.saveContactDialogVisible = false
      })
    },
    // 主要联系人改变
    async contactPrimaryChanged (contact) {
      if (contact.primary) {
        this.saveForm.contactList.forEach((existedContact) => {
          existedContact.primary = false
        })

        contact.primary = true
      }

      this.$message.success('更新主要联系人成功！')
    },
    // 删除联系人
    async deleteContact (contact) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
        '删除该联系人，是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)

      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消了删除，则返回值为字符串 cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }

      this.saveForm.contactList.splice(
        this.saveForm.contactList.findIndex(existedContact => existedContact === contact),
        1
      )

      this.$message.success('删除联系人成功！')
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
.my-autocomplete {
  li {
    line-height: normal;
    padding: 7px;

    .name {
      text-overflow: ellipsis;
      overflow: hidden;
    }
    .addr {
      font-size: 12px;
      color: #b4b4b4;
    }

    .highlighted .addr {
      color: #ddd;
    }
  }
}
</style>
