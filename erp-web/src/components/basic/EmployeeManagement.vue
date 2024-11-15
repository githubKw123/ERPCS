<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础管理</el-breadcrumb-item>
      <el-breadcrumb-item>职员管理</el-breadcrumb-item>
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

      <!-- 职员列表区域 -->
      <el-table :data="employeeList"
                border
                stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="职员编号"
                         prop="code"></el-table-column>
        <el-table-column label="职员名称"
                         prop="name"></el-table-column>
        <el-table-column label="是否启用"
                         width="70px">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.active"
                       @change="employeeStateChanged(scope.row)">
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
                         @click="deleteEmployee(scope.row.id)"></el-button>
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

    <!-- 添加职员的对话框 -->
    <el-dialog title="添加职员"
               :visible.sync="addDialogVisible"
               width="30%"
               @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm"
               :rules="addFormRules"
               ref="addFormRef"
               label-width="80px">
        <el-form-item label="职员编号"
                      prop="code">
          <el-input v-model="addForm.code"></el-input>
        </el-form-item>
        <el-form-item label="职员名称"
                      prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="addEmployee">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改职员的对话框 -->
    <el-dialog title="修改职员"
               :visible.sync="editDialogVisible"
               width="30%"
               @close="editDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="editForm"
               :rules="editFormRules"
               ref="editFormRef"
               label-width="80px">
        <el-form-item label="职员编号"
                      prop="code">
          <el-input v-model="editForm.code"></el-input>
        </el-form-item>
        <el-form-item label="职员名称"
                      prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="editEmployee">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 获取职员列表的参数对象
      params: {
        query: {
        },
        // 当前的页数
        current: 1,
        // 当前每页显示多少条数据
        size: 5
      },
      total: 0,
      employeeList: [],
      // 控制添加账户对话框的显示与隐藏
      addDialogVisible: false,
      // 添加职员的表单数据
      addForm: {
      },
      // 添加表单的验证规则对象
      addFormRules: {
        code: [
          { required: true, message: '请输入编码', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      },
      // 控制修改职员对话框的显示与隐藏
      editDialogVisible: false,
      // 查询到的职员信息对象
      editForm: {},
      // 修改表单的验证规则对象
      editFormRules: {
        code: [
          { required: true, message: '请输入编码', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getEmployeePage()
  },
  methods: {
    async getEmployeePage () {
      const { data: result } = await this.$http.post('/employee/page', this.params)
      if (!result.success) return this.$message.error(result.message)

      this.employeeList = result.data.employeePage.records
      this.total = result.data.employeePage.total
    },
    // 监听 pagesize 改变的事件
    handleSizeChange (size) {
      this.params.size = size
      this.getEmployeePage()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (current) {
      this.params.current = current
      this.getEmployeePage()
    },
    async employeeStateChanged (employee) {
      const { data: result } = await this.$http.post(
        '/employee/switchActive',
        {
          'employeeId': employee.id
        }
      )
      if (!result.success) {
        employee.active = !employee.active
        return this.$message.error('更新职员状态失败！')
      }
      employee.active = result.data.employee.active
      this.$message.success('更新职员状态成功！')
    },
    // 监听添加对话框的关闭事件
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 点击按钮，添加新职员
    addEmployee () {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return
        // 可以发起新增职员的网络请求
        const { data: result } = await this.$http.post('/employee/save', {
          employee: this.addForm
        })
        if (!result.success) {
          return this.$message.error(result.message)
        }

        this.$message.success('添加职员成功！')
        // 隐藏添加职员的对话框
        this.addDialogVisible = false
        // 重新获取职员列表数据
        this.getEmployeePage()
      })
    },
    // 展示修改职员的对话框
    async showEditDialog (employeeId) {
      const { data: result } = await this.$http.post('/employee/detail', {
        employeeId
      })

      if (!result.success) return this.$message.error(result.message)

      this.editForm = result.data.employee
      this.editDialogVisible = true
    },
    // 监听修改职员对话框的关闭事件
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 修改职员信息并提交
    async editEmployee () {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return

        // 发起修改职员信息的数据请求
        const { data: result } = await this.$http.post('/employee/save', {
          employee: this.editForm
        })

        if (!result.success) {
          return this.$message.error(result.message)
        }

        // 关闭对话框
        this.editDialogVisible = false
        // 刷新数据列表
        this.getEmployeePage()
        // 提示修改成功
        this.$message.success('修改职员信息成功！')
      })
    },
    // 删除职员
    async deleteEmployee (employeeId) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
        '此操作将永久删除该职员 是否继续?',
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

      const { data: result } = await this.$http.post('/employee/delete', { employeeId })

      if (!result.success) {
        return this.$message.error('删除职员失败！')
      }

      this.$message.success('删除职员成功！')
      this.getEmployeePage()
    }
  }
}
</script>

<style lang="less" scoped>
</style>
