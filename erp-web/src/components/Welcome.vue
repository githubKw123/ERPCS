<template>
  <div style="text-align: center;height: 100%;padding: 0px;margin: 0px;">
    <h1 style="font-size: 50px;font-family: 华文行楷">博 理 精 工　　创 新 笃 行</h1>
    <div style="text-align: center">
      <el-table
        :data="tableData"
        border
        style="width: 541px;text-align: center;margin-left: 340px;">
        <el-table-column
          prop="username"
          label="用户名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="address"
          label="电话"
          width="180">
        </el-table-column>
        <el-table-column
          prop="role"
          label="职务"
          width="120">
        </el-table-column>
      </el-table>
    </div>

    <div style="float: left;text-align: left;margin-left: 200px;margin-top: 20px">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>今日工作计划</span>
          <el-button style="float: right; padding: 3px 0" type="text">前往工作</el-button>
        </div>
        <div class="text item">
          {{'RA5008型十字交叉滚子轴承需求163套'}}
        </div>
        <div class="text item">
          {{'RNAV4002型十滚针轴承需求378套'}}
        </div>
        <div class="text item">
          {{'14批次产品未进行质量检测'}}
        </div>
        <div class="text item">
          {{'LZ111型平滚针库存水平不足'}}
        </div>
        <div class="text item">
          {{'村头厕所没纸了'}}
        </div>
      </el-card>
    </div>
    <div style="text-align:center;margin-left: 620px;margin-top: 20px">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>本季度工作总进度</span>
        </div>
        <div>
          <img src="percent.png" height="180px" border="边框">
        </div>
      </el-card>
    </div>

    <DateUtils></DateUtils>
  </div>
</template>

<script>
import DateUtils from './DateUtils'
export default {
  name: 'Home',
  components: { DateUtils },
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      tableData: [{
        username: 'admin',
        name: '丁真',
        address: '18810975567',
        role: '经理'
      }]
    }
  },
  computed: {

  },
  methods: {}
}
</script>

<style scoped>
.el-descriptions{
  width:70%;
  margin: 0 auto;
  text-align: center;
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 380px;
}
</style>
