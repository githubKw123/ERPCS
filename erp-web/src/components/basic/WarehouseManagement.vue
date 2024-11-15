<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础管理</el-breadcrumb-item>
      <el-breadcrumb-item>仓库管理</el-breadcrumb-item>
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

      <!-- 仓库列表区域 -->
      <el-table :data="warehouseList"
                border
                stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="仓库编号"
                         prop="code"></el-table-column>
        <el-table-column label="仓库名称"
                         prop="name"></el-table-column>
        <el-table-column label="是否启用"
                         width="70px">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.active"
                       @change="warehouseStateChanged(scope.row)">
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
                         @click="deleteWarehouse(scope.row.id)"></el-button>
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

    <!-- 添加仓库的对话框 -->
    <el-dialog title="添加仓库"
               :visible.sync="addDialogVisible"
               width="30%"
               @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm"
               :rules="addFormRules"
               ref="addFormRef"
               label-width="80px">
        <el-form-item label="仓库编号"
                      prop="code">
          <el-input v-model="addForm.code"></el-input>
        </el-form-item>
        <el-form-item label="仓库名称"
                      prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="addWarehouse">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改账户的对话框 -->
    <el-dialog title="修改仓库"
               :visible.sync="editDialogVisible"
               width="30%"
               @close="editDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="editForm"
               :rules="editFormRules"
               ref="editFormRef"
               label-width="80px">
        <el-form-item label="仓库编号"
                      prop="code">
          <el-input v-model="editForm.code"></el-input>
        </el-form-item>
        <el-form-item label="仓库名称"
                      prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="editWarehouse">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 获取仓库列表的参数对象
      params: {
        query: {
        },
        // 当前的页数
        current: 1,
        // 当前每页显示多少条数据
        size: 5
      },
      total: 0,
      warehouseList: [],
      // 控制添加仓库对话框的显示与隐藏
      addDialogVisible: false,
      // 添加仓库的表单数据
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
      // 控制修改仓库对话框的显示与隐藏
      editDialogVisible: false,
      // 查询到的仓库信息对象
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
    this.getWarehousePage()
  },
  methods: {
    async getWarehousePage () {
      const { data: result } = await this.$http.post('/warehouse/page', this.params)
      if (!result.success) return this.$message.error(result.message)

      this.warehouseList = result.data.warehousePage.records
      this.total = result.data.warehousePage.total
    },
    // 监听 pagesize 改变的事件
    handleSizeChange (size) {
      this.params.size = size
      this.getWarehousePage()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (current) {
      this.params.current = current
      this.getWarehousePage()
    },
    async warehouseStateChanged (warehouse) {
      const { data: result } = await this.$http.post(
        '/warehouse/switchActive',
        {
          'warehouseId': warehouse.id
        }
      )
      if (!result.success) {
        warehouse.active = !warehouse.active
        return this.$message.error('更新仓库状态失败！')
      }
      warehouse.active = result.data.warehouse.active
      this.$message.success('更新仓库状态成功！')
    },
    // 监听添加对话框的关闭事件
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 点击按钮，添加新仓库
    addWarehouse () {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return
        // 可以发起新增仓库的网络请求
        const { data: result } = await this.$http.post('/warehouse/save', {
          warehouse: this.addForm
        })
        if (!result.success) {
          return this.$message.error(result.message)
        }

        this.$message.success('添加仓库成功！')
        // 隐藏添加仓库的对话框
        this.addDialogVisible = false
        // 重新获取仓库列表数据
        this.getWarehousePage()
      })
    },
    // 展示修改仓库的对话框
    async showEditDialog (warehouseId) {
      const { data: result } = await this.$http.post('/warehouse/detail', {
        warehouseId
      })

      if (!result.success) return this.$message.error(result.message)

      this.editForm = result.data.warehouse
      this.editDialogVisible = true
    },
    // 监听修改仓库对话框的关闭事件
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 修改仓库信息并提交
    async editWarehouse () {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return

        // 发起修改仓库信息的数据请求
        const { data: result } = await this.$http.post('/warehouse/save', {
          warehouse: this.editForm
        })

        if (!result.success) {
          return this.$message.error(result.message)
        }

        // 关闭对话框
        this.editDialogVisible = false
        // 刷新数据列表
        this.getWarehousePage()
        // 提示修改成功
        this.$message.success('修改仓库信息成功！')
      })
    },
    // 删除仓库
    async deleteWarehouse (warehouseId) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
        '此操作将永久删除该仓库, 是否继续?',
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

      const { data: result } = await this.$http.post('/warehouse/delete', { warehouseId })

      if (!result.success) {
        return this.$message.error('删除仓库失败！')
      }

      this.$message.success('删除仓库成功！')
      this.getWarehousePage()
    }
  }
}
</script>

<style lang="less" scoped>
</style>
