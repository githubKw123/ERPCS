<template>
  <div v-bind:style="{backgroundImage:'url(' + bg + ')',
        backgroundRepeat:'no-repeat',
        backgroundSize:'100% 100%'}">
  <el-container class="home-container" style="height: 100vh">
    <el-aside :width="isCollapse ? '64px' : '200px'" >
      <div class="toggle-button"
           @click="toggleCollapse"><i class="el-icon-more"></i></div>
      <!-- 侧边栏菜单区域 -->
      <el-menu background-color="#333744"
               text-color="#fff"
               active-text-color="#ffd04b"
               unique-opened
               :collapse="isCollapse"
               :collapse-transition="false"
               router
               :default-active="$route.path">
        <!-- 一级菜单 -->
        <el-submenu :index="item.id + ''"
                    v-for="item in menulist"
                    :key="item.id">
          <!-- 一级菜单的模板区域 -->
          <template slot="title">
            <!-- 图标 -->
            <i :class="item.icon"></i>
            <!-- 文本 -->
            <span>{{ item.title }}</span>
          </template>

          <!-- 二级菜单 -->
          <el-menu-item :index="subItem.path"
                        v-for="subItem in item.childList"
                        :key="subItem.id"
                        @click="saveNavState(subItem.path)">
            <template slot="title">
              <!-- 图标 -->
              <i :class="subItem.icon"></i>
              <!-- 文本 -->
              <span>{{ subItem.title }}</span>
            </template>
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <!-- 头部区域 -->

    <!-- 页面主体区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-header style="text-align: center; font-size: 18px;width:100%;border-bottom: rgba(23,23,23,0.57) 2px solid;background-color:  rgba(23,23,0,0);">
        <div style="display: flex;line-height: 60px;width:100%;text-align: center">
          <div style="flex: 1;text-align: center;margin-left:200px;font-size: 25px;">
            <span style="font-weight: bolder;color: black">潍柴机械生产总厂仓库管理系统</span>
          </div>
          <div>
            <i class="el-icon-user-solid" style="margin-left: 10px;color: black"></i><span style="color: black">{{user.username}}</span>
            <el-button type="success" @click="toWelcome" style="margin-left: 10px">个人中心</el-button>
            <el-button type="info" @click="logout">退出</el-button>
          </div>
        </div>
      </el-header>
      <!-- 右侧内容主体 -->
      <el-main style="background-color:  rgba(23,23,0,0);">
        <!-- 路由占位符 -->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 左侧菜单数据
      bg: require('E:\\IEDAcode\\ERPCS\\erp-web\\src\\assets\\lgbk4.jpg'),
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      menulist: [],
      iconsObj: {
        125: 'iconfont icon-user',
        103: 'iconfont icon-tijikongjian',
        101: 'iconfont icon-shangpin',
        102: 'iconfont icon-danju',
        145: 'iconfont icon-baobiao'
      },
      // 是否折叠
      isCollapse: false,
      // 被激活的链接地址
      activePath: ''
    }
  },
  created() {
    this.getMenuList()
    this.activePath = window.sessionStorage.getItem('activePath')
  },
  methods: {
    logout() {
      window.sessionStorage.clear()
      this.$router.push('/login')
    },
    toUser() {
      window.sessionStorage.clear()
      this.$router.push('/home')
    },
    // 获取所有的菜单
    async getMenuList() {
      const { data: result } = await this.$http.post('/menu/list')
      if (!result.success) {
        this.$message.error(result.message)
        if (result.code === 99998 || result.code === 99999) {
          // 如果未登录，则退出
          return this.logout()
        }
      }
      console.log(result.data)
      this.menulist = result.data.menuList
    },
    toWelcome() {
      this.$router.push('/welcome')
    },
    // 点击按钮，切换菜单的折叠与展开
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    },
    // 保存链接的激活状态
    saveNavState(activePath) {
      window.sessionStorage.setItem('activePath', activePath)
      this.activePath = activePath
    }
  }
}
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
}
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #fff;
  font-size: 20px;
  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
}

.logo {
  cursor: pointer;
}

.el-aside {
  background-color: #333744;
  .el-menu {
    border-right: none;
  }
}

.el-main {
  background-color: #eaedf1;
}

.iconfont {
  margin-right: 10px;
}

.toggle-button {
  background-color: #333744;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
