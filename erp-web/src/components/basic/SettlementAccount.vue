<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础管理</el-breadcrumb-item>
      <el-breadcrumb-item>账户管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20">
        <el-col :span="4">
          <el-button type="primary"
                     @click="addDialogVisible = true">新增</el-button>
        </el-col>
      </el-row>

      <!-- 账户列表区域 -->
      <el-table :data="accountList"
                border
                stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="账户编号"
                         prop="code"></el-table-column>
        <el-table-column label="账户名称"
                         prop="name"></el-table-column>
        <el-table-column label="当前余额"
                         prop="currentBalance"></el-table-column>
        <el-table-column label="期初余额"
                         prop="beginBalance"></el-table-column>
        <el-table-column label="建账日期"
                         prop="balanceTime"
                         :formatter="dateFormatter"></el-table-column>
        <el-table-column label="账户类别"
                         prop="typeName"></el-table-column>
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
                         @click="deleteAccount(scope.row.id)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加账户的对话框 -->
    <el-dialog title="添加账户"
               :visible.sync="addDialogVisible"
               width="50%"
               @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm"
               :rules="addFormRules"
               ref="addFormRef"
               label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="账户编号"
                          prop="code">
              <el-input v-model="addForm.code"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账户名称"
                          prop="name">
              <el-input v-model="addForm.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="余额日期"
                          prop="balanceTime">
              <el-date-picker v-model="addForm.balanceTime"
                              type="date"
                              value-format='yyyy-MM-dd'
                              placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账户余额"
                          prop="beginBalance">
              <el-input v-model="addForm.beginBalance"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="账户类型"
                          prop="type">
              <el-select v-model="addForm.type"
                         placeholder="请选择类型">
                <el-option v-for="type in typeList"
                           :key="type.id"
                           :label="type.name"
                           :value="type.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="addAccount">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改账户的对话框 -->
    <el-dialog title="修改账户"
               :visible.sync="editDialogVisible"
               width="50%"
               @close="editDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="editForm"
               :rules="editFormRules"
               ref="editFormRef"
               label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="账户编号"
                          prop="code">
              <el-input v-model="editForm.code"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账户名称"
                          prop="name">
              <el-input v-model="editForm.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="余额日期"
                          prop="balanceTime">
              <el-date-picker v-model="editForm.balanceTime"
                              type="date"
                              value-format='yyyy-MM-dd'
                              placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账户余额"
                          prop="beginBalance">
              <el-input v-model="editForm.beginBalance"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="账户类型"
                          prop="type">
              <el-select v-model="editForm.type"
                         placeholder="请选择类型">
                <el-option v-for="type in typeList"
                           :key="type.id"
                           :label="type.name"
                           :value="type.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="editAccount">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 获取账户列表的参数对象
      params: {
        query: {}
      },
      typeList: [],
      accountList: [],
      // 控制添加账户对话框的显示与隐藏
      addDialogVisible: false,
      // 添加用户的表单数据
      addForm: {
        beginBalance: 0,
        balanceTime: new Date()
      },
      // 添加表单的验证规则对象
      addFormRules: {
        code: [{ required: true, message: '请输入编码', trigger: 'blur' }],
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        type: [{ required: true, message: '请选择类型', trigger: 'blur' }]
      },
      // 控制修改账户对话框的显示与隐藏
      editDialogVisible: false,
      // 查询到的账户信息对象
      editForm: {},
      // 修改表单的验证规则对象
      editFormRules: {
        code: [{ required: true, message: '请输入编码', trigger: 'blur' }],
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        type: [{ required: true, message: '请选择类型', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getTypeList()
    this.getAccountList()
  },
  methods: {
    async getTypeList() {
      const { data: result } = await this.$http.post('/dict/itemList', {
        dictCode: 'account_type'
      })
      if (!result.success) return this.$message.error(result.message)

      this.typeList = result.data.itemList
    },
    async getAccountList() {
      const { data: result } = await this.$http.post('/settlementAccount/list', this.params)
      if (!result.success) return this.$message.error(result.message)

      this.accountList = result.data.accountList
    },
    // 表格日期格式化
    dateFormatter(row, column) {
      var date = row[column.property]
      if (date === undefined) {
        return ''
      }
      return this.$moment(date).format('YYYY-MM-DD')
    },
    // 监听添加账户对话框的关闭事件
    addDialogClosed() {
      this.$refs.addFormRef.resetFields()
    },
    // 点击按钮，添加新账户
    addAccount() {
      this.$refs.addFormRef.validate(async (valid) => {
        if (!valid) return
        // 可以发起新增账户的网络请求
        var beginBalance = parseInt(parseFloat(this.addForm.beginBalance) * 100)
        if (isNaN(beginBalance)) {
          this.$message.error('账户余额不正确！')
          return
        }
        const { data: result } = await this.$http.post('/settlementAccount/save', {
          account: {
            code: this.addForm.code,
            name: this.addForm.name,
            balanceTime: this.addForm.balanceTime,
            type: this.addForm.type,
            beginBalance: beginBalance
          }
        })
        if (!result.success) {
          return this.$message.error(result.message)
        }

        this.$message.success('添加账户成功！')
        // 隐藏添加账户的对话框
        this.addDialogVisible = false
        // 重新获取账户列表数据
        this.getAccountList()
      })
    },
    // 展示修改账户的对话框
    async showEditDialog(accountId) {
      const { data: result } = await this.$http.post('/settlementAccount/detail', {
        accountId
      })

      if (!result.success) return this.$message.error(result.message)

      this.editForm = result.data.account
      this.editDialogVisible = true
    },
    // 监听修改账户对话框的关闭事件
    editDialogClosed() {
      this.$refs.editFormRef.resetFields()
    },
    // 修改账户信息并提交
    async editAccount() {
      this.$refs.editFormRef.validate(async (valid) => {
        if (!valid) return

        var beginBalance = parseInt(parseFloat(this.editForm.beginBalance) * 100)
        if (isNaN(beginBalance)) {
          this.$message.error('账户余额不正确！')
          return
        }

        // 发起修改账户信息的数据请求
        const { data: result } = await this.$http.post('/settlementAccount/save', {
          account: {
            id: this.editForm.id,
            code: this.editForm.code,
            name: this.editForm.name,
            balanceTime: this.editForm.balanceTime,
            type: this.editForm.type,
            beginBalance: beginBalance
          }
        })

        if (!result.success) {
          return this.$message.error(result.message)
        }

        // 关闭对话框
        this.editDialogVisible = false
        // 刷新数据列表
        this.getAccountList()
        // 提示修改成功
        this.$message.success('修改账户信息成功！')
      })
    },
    // 删除账户
    async deleteAccount(accountId) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm('此操作将永久删除该账户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch((err) => err)

      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消了删除，则返回值为字符串 cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }

      const { data: result } = await this.$http.post('/settlementAccount/delete', { accountId })

      if (!result.success) {
        return this.$message.error('删除账户失败！')
      }

      this.$message.success('删除账户成功！')
      this.getAccountList()
    }
  }
}
</script>

<style lang="less" scoped>
.el-date-editor {
  width: 100%;
}
.el-select {
  width: 100%;
}
</style>
