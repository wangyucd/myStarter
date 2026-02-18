<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #409eff, #66b1ff);">
            <el-icon :size="28"><User /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">128</div>
            <div class="stat-label">用户总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #67c23a, #95d475);">
            <el-icon :size="28"><Goods /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">256</div>
            <div class="stat-label">商品总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #e6a23c, #f0c78a);">
            <el-icon :size="28"><ShoppingCart /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">1,024</div>
            <div class="stat-label">订单总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #f56c6c, #fab6b6);">
            <el-icon :size="28"><TrendCharts /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">¥128,000</div>
            <div class="stat-label">销售总额</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="never" class="welcome-card" style="margin-top: 20px;">
      <template #header>
        <span>欢迎使用 MyStarter 管理系统</span>
      </template>
      <p>这是一个基于 Vue3 + Element Plus + Spring Boot 的前后端分离项目脚手架。</p>
      <p>当前系统包含以下功能模块：</p>
      <ul>
        <li>✅ 用户管理 - 用户增删改查、角色分配</li>
        <li>✅ 角色管理 - 角色增删改查、权限分配</li>
        <li>✅ 菜单管理 - 菜单增删改查、权限码配置</li>
        <li>✅ 商品管理 - 商品增删改查、分类管理</li>
      </ul>
    </el-card>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.dashboard-container {
  padding: 0;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 0;
}

.stat-card :deep(.el-card__body) {
  display: flex;
  align-items: center;
  gap: 16px;
  width: 100%;
}

.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  flex-shrink: 0;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 24px;
  font-weight: 700;
  color: #303133;
}

.stat-label {
  font-size: 13px;
  color: #909399;
  margin-top: 4px;
}

.welcome-card ul {
  padding-left: 20px;
  margin-top: 8px;
  line-height: 2;
}
</style>
