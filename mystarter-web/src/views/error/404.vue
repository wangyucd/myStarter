<template>
  <div class="error-container">
    <h1 class="error-code">404</h1>
    <p class="error-message">抱歉，您访问的页面不存在</p>
    <el-button type="primary" @click="$router.push('/')">返回首页</el-button>
  </div>
</template>

<style scoped>
.error-container {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: #f0f2f5;
}

.error-code {
  font-size: 120px;
  font-weight: 800;
  color: #409eff;
  margin: 0;
  line-height: 1;
}

.error-message {
  font-size: 18px;
  color: #909399;
  margin: 20px 0 40px;
}
</style>
