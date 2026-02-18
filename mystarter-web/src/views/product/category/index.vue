<template>
  <div class="page-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>商品分类</span>
          <el-button type="primary" @click="handleAdd()" v-if="userStore.hasPermission('product:category:add')">新增分类</el-button>
        </div>
      </template>

      <el-table :data="tableData" v-loading="loading" row-key="id" :tree-props="{ children: 'children' }" default-expand-all>
        <el-table-column prop="categoryName" label="分类名称" min-width="200" />
        <el-table-column prop="icon" label="图标" width="60">
          <template #default="{ row }">
            <el-icon v-if="row.icon"><component :is="row.icon" /></el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="sortOrder" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleAdd(row.id)" v-if="userStore.hasPermission('product:category:add')">新增</el-button>
            <el-button link type="primary" @click="handleEdit(row)" v-if="userStore.hasPermission('product:category:edit')">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row.id)" v-if="userStore.hasPermission('product:category:delete')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="formRules" label-width="80px">
        <el-form-item label="上级分类">
          <el-tree-select v-model="form.parentId" :data="categoryTreeOptions" :props="{ label: 'categoryName', value: 'id', children: 'children' }" check-strictly placeholder="顶级分类" clearable style="width: 100%" />
        </el-form-item>
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="form.icon" placeholder="Element Plus 图标名称" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sortOrder" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { getCategoryTree, saveCategory, deleteCategory } from '@/api/product'
import { useUserStore } from '@/store/user'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'

const userStore = useUserStore()

const loading = ref(false)
const submitLoading = ref(false)
const tableData = ref<any[]>([])
const dialogVisible = ref(false)
const dialogTitle = ref('新增分类')

const categoryTreeOptions = computed(() => [{ id: 0, categoryName: '顶级分类', children: tableData.value }])

const formRef = ref<FormInstance>()
const form = reactive<any>({ id: undefined, parentId: 0, categoryName: '', icon: '', sortOrder: 0, status: 1 })

const formRules: FormRules = {
  categoryName: [{ required: true, message: '请输入分类名称', trigger: 'blur' }]
}

onMounted(() => fetchData())

async function fetchData() {
  loading.value = true
  try {
    const data: any = await getCategoryTree()
    tableData.value = data || []
  } finally {
    loading.value = false
  }
}

function handleAdd(parentId?: number) {
  dialogTitle.value = '新增分类'
  Object.assign(form, { id: undefined, parentId: parentId || 0, categoryName: '', icon: '', sortOrder: 0, status: 1 })
  dialogVisible.value = true
}

function handleEdit(row: any) {
  dialogTitle.value = '编辑分类'
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value?.validate()
  if (!valid) return
  submitLoading.value = true
  try {
    await saveCategory(form)
    ElMessage.success('操作成功')
    dialogVisible.value = false
    await fetchData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(id: number) {
  await ElMessageBox.confirm('确认删除该分类及其子分类？', '提示', { type: 'warning' })
  await deleteCategory(id)
  ElMessage.success('删除成功')
  await fetchData()
}
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
