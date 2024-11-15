<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统设置</el-breadcrumb-item>
      <el-breadcrumb-item>结算方式</el-breadcrumb-item>
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

      <!-- 列表区域 -->
      <el-table :data="settlementList"
                border
                stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="名称"
                         prop="name"></el-table-column>
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
                         @click="deleteSettlement(scope.row.id)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增对话框 -->
    <el-dialog title="新增结算方式"
               :visible.sync="addDialogVisible"
               width="30%"
               @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm"
               :rules="addFormRules"
               ref="addFormRef"
               label-width="55px">
        <el-form-item label="名称"
                      prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="addSettlement">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改对话框 -->
    <el-dialog title="修改结算方式"
               :visible.sync="editDialogVisible"
               width="30%"
               @close="editDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="editForm"
               :rules="editFormRules"
               ref="editFormRef"
               label-width="55px">
        <el-form-item label="名称"
                      prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="editSettlement">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 获取列表的参数对象
      params: {
        query: {
        }
      },
      settlementList: [],
      // 控制新增对话框的显示与隐藏
      addDialogVisible: false,
      // 新增的表单数据
      addForm: {
        dictCode: 'settlement'
      },
      // 新增表单的验证规则对象
      addFormRules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      },
      // 控制修改对话框的显示与隐藏
      editDialogVisible: false,
      // 查询到的信息对象
      editForm: {},
      // 修改表单的验证规则对象
      editFormRules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getSettlementList()
  },
  methods: {
    async getSettlementList () {
      const { data: result } = await this.$http.post('/dict/itemList', {
        dictCode: 'settlement'
      })
      if (!result.success) return this.$message.error(result.message)

      this.settlementList = result.data.itemList
    },
    // 监听新增对话框的关闭事件
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 点击按钮，新增对象
    addSettlement () {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return
        // 可以发起新增的网络请求
        const { data: result } = await this.$http.post('/dict/itemSave', {
          item: this.addForm
        })
        if (!result.success) {
          return this.$message.error(result.message)
        }

        this.$message.success('新增结算方式成功！')
        // 隐藏新增的对话框
        this.addDialogVisible = false
        // 重新获取列表数据
        this.getSettlementList()
      })
    },
    // 展示修改的对话框
    async showEditDialog (settlementId) {
      const { data: result } = await this.$http.post('/dict/itemDetail', {
        itemId: settlementId
      })

      if (!result.success) return this.$message.error(result.message)

      this.editForm = result.data.item
      this.editDialogVisible = true
    },
    // 监听修改对话框的关闭事件
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 修改信息并提交
    async editSettlement () {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return

        // 发起修改信息的数据请求
        const { data: result } = await this.$http.post('/dict/itemSave', {
          item: this.editForm
        })

        if (!result.success) {
          return this.$message.error(result.message)
        }

        // 关闭对话框
        this.editDialogVisible = false
        // 刷新数据列表
        this.getSettlementList()
        // 提示修改成功
        this.$message.success('修改结算方式成功！')
      })
    },
    // 删除对象
    async deleteSettlement (settlementId) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
        '此操作将永久删除该结算方式，是否继续?',
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

      const { data: result } = await this.$http.post('/dict/itemDelete', {
        itemId: settlementId
      })

      if (!result.success) {
        return this.$message.error('删除结算方式失败！')
      }

      this.$message.success('删除结算方式成功！')
      this.getSettlementList()
    }
  }
}
</script>

<style lang="less" scoped>
</style>
