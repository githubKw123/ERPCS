<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统设置</el-breadcrumb-item>
      <el-breadcrumb-item>类别管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 顶部面板切换区域 -->
      <el-tabs type="card"
               v-model="activeName"
               @tab-click="handleTabClick">
        <el-tab-pane label="生产车间"
                     name="customer"></el-tab-pane>
        <el-tab-pane label="供应商"
                     name="supplier"></el-tab-pane>
        <el-tab-pane label="产品"
                     name="product"></el-tab-pane>
      </el-tabs>
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入类别名称查询"
                    v-model="params.query.name"
                    clearable
                    @clear="getCategoryList">
            <el-button slot="append"
                       icon="el-icon-search"
                       @click="getCategoryList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary"
                     @click="addDialogVisible = true">新增</el-button>
        </el-col>
      </el-row>
      <!-- 分类列表区域 -->
      <el-table :data="categoryList"
                row-key="id"
                border
                stripe
                :tree-props="{ children: 'childList' }">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="类别"
                         prop="name"></el-table-column>
        <el-table-column label="操作"
                         width="175px">
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
                         @click="deleteCategory(scope.row.id)"></el-button>
            </el-tooltip>
            <!-- 新增子分类 -->
            <el-tooltip effect="dark"
                        content="新增子类"
                        placement="top"
                        :enterable="false"
                        v-if="scope.row.type == 30">
              <el-button type="success"
                         icon="el-icon-plus"
                         size="mini"
                         @click="showAddDialog(scope.row.id)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加用户的对话框 -->
    <el-dialog title="新增分类"
               :visible.sync="addDialogVisible"
               width="30%"
               @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm"
               :rules="addFormRules"
               ref="addFormRef"
               label-width="55px">
        <el-form-item label="类别"
                      prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="addCategory">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改分类的对话框 -->
    <el-dialog title="修改类别"
               :visible.sync="editDialogVisible"
               width="30%"
               @close="editDialogClosed">
      <el-form :model="editForm"
               :rules="editFormRules"
               ref="editFormRef"
               label-width="55px">
        <el-form-item label="类别">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="editCategory">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      params: {
        query: {
          name: ''
        },
        type: 10
      },
      activeName: 'customer',
      categoryList: [],
      // 控制新增类别对话框的显示与隐藏
      addDialogVisible: false,
      // 新增类别的表单数据
      addForm: {
        parentId: '',
        name: ''
      },
      // 新增表单的验证规则对象
      addFormRules: {
        name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }]
      },
      // 控制修改用户对话框的显示与隐藏
      editDialogVisible: false,
      editForm: {},
      editFormRules: {
        name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getCategoryList()
  },
  methods: {
    // 获取分类列表
    async getCategoryList() {
      const { data: result } = await this.$http.post('/category/list', this.params)
      if (!result.success) return this.$message.error(result.message)
      console.log(result)
      this.categoryList = result.data.categoryList
    },
    // tab 页签点击事件的处理函数
    handleTabClick() {
      console.log(this.activeName)
      if (this.activeName === 'customer') {
        this.params.type = 10
      } else if (this.activeName === 'supplier') {
        this.params.type = 20
      } else if (this.activeName === 'product') {
        this.params.type = 30
      } else if (this.activeName === 'expense') {
        this.params.type = 40
      } else if (this.activeName === 'income') {
        this.params.type = 50
      }
      this.params.query.name = ''
      this.getCategoryList()
    },
    // 删除分类
    async deleteCategory(categoryId) {
      const confirmResult = await this.$confirm('此操作将永久删除该分类，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch((err) => err)

      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }

      const { data: result } = await this.$http.post('/category/delete', { categoryId: categoryId })
      if (!result.success) {
        return this.$message.error('删除分类失败！')
      }

      this.$message.success('删除分类成功！')
      this.getCategoryList()
    },
    // 新增分类
    async addCategory() {
      this.$refs.addFormRef.validate(async (valid) => {
        if (!valid) return

        const { data: result } = await this.$http.post('/category/save', {
          category: {
            parentId: this.addForm.parentId,
            type: this.params.type,
            name: this.addForm.name
          }
        })
        if (!result.success) return this.$message.error('新增分类失败！')

        this.$message.success('新增分类成功！')
        this.addDialogVisible = false
        this.getCategoryList()
      })
    },
    // 监听新增分类对话框的关闭事件
    addDialogClosed() {
      this.$refs.addFormRef.resetFields()
      this.addForm.parentId = ''
    },
    // 新增子类时，打开新增对话框
    showAddDialog(parentId) {
      this.addForm.parentId = parentId
      this.addDialogVisible = true
    },
    // 展示编辑分类的对话框
    async showEditDialog(categoryId) {
      const { data: result } = await this.$http.post('/category/detail', {
        categoryId: categoryId
      })

      if (!result.success) return this.$message.error(result.message)

      this.editForm = result.data.category
      this.editDialogVisible = true
    },
    // 监听修改分类对话框关闭事件
    editDialogClosed() {
      this.$refs.editFormRef.resetFields()
    },
    // 修改分类并提交
    editCategory() {
      this.$refs.editFormRef.validate(async (valid) => {
        if (!valid) return
        // 发起修改用户信息的数据请求
        const { data: result } = await this.$http.post('/category/save', {
          category: {
            type: this.editForm.type,
            id: this.editForm.id,
            name: this.editForm.name
          }
        })

        if (!result.success) {
          return this.$message.error(result.message)
        }

        // 关闭对话框
        this.editDialogVisible = false
        // 刷新数据列表
        this.getCategoryList()
        // 提示修改成功
        this.$message.success('修改分类成功！')
      })
    }
  }
}
</script>

<style lang="less" scoped>
</style>
