<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统设置</el-breadcrumb-item>
      <el-breadcrumb-item>日志管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20">
        <el-col :span="7">
          <el-date-picker v-model="rangedDate"
                          type="daterange"
                          value-format='yyyy-MM-dd'
                          range-separator="至"
                          start-placeholder="开始日期"
                          end-placeholder="结束日期">
          </el-date-picker>
        </el-col>
        <el-col :span="4">
          <el-select v-model="params.query.userId"
                     placeholder="请选择用户">
            <el-option v-for="user in userList"
                       :key="user.id"
                       :label="user.name"
                       :value="user.id">
              <span style="float: left">{{ user.name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ user.username }}</span>
            </el-option>
          </el-select>
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

      <!-- 用户列表区域 -->
      <el-table :data="logList"
                border
                stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="日期"
                         prop="createdTime"></el-table-column>
        <el-table-column label="用户名"
                         prop="username"></el-table-column>
        <el-table-column label="姓名"
                         prop="name"></el-table-column>
        <el-table-column label="日志"
                         prop="description"></el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="params.current"
                     :page-sizes="[10, 15, 20, 50]"
                     :page-size="params.size"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total">
      </el-pagination>
    </el-card>

  </div>
</template>

<script>
export default {
  data () {
    return {
      params: {
        query: {
          startTime: '',
          endTime: '',
          userId: ''
        },
        current: 1,
        size: 10
      },
      userList: [],
      rangedDate: null,
      logList: [],
      total: 0
    }
  },
  created () {
    this.getLogPage()
    this.getUserList()
  },
  methods: {
    async getLogPage () {
      const { data: result } = await this.$http.post('/log/page', this.params)
      if (!result.success) return this.$message.error(result.message)

      this.logList = result.data.logPage.records
      this.total = result.data.logPage.total
    },
    async getUserList () {
      const { data: result } = await this.$http.post('/user/page', {
        current: 1,
        size: 100
      })
      if (!result.success) return this.$message.error(result.message)

      this.userList = result.data.userPage.records
    },
    // 搜索
    search () {
      if (this.rangedDate != null) {
        this.params.query.startTime = this.rangedDate[0]
        this.params.query.endTime = this.rangedDate[1]
      } else {
        this.params.query.startTime = ''
        this.params.query.endTime = ''
      }
      this.getLogPage()
    },
    // 清空
    clear () {
      this.rangedDate = null
      this.params.query.startTime = ''
      this.params.query.endTime = ''
      this.params.query.userId = ''
      this.getLogPage()
    },
    // 监听 pagesize 改变的事件
    handleSizeChange (size) {
      this.params.size = size
      this.getLogPage()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (current) {
      this.params.current = current
      this.getLogPage()
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
