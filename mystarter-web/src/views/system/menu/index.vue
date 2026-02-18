<template>
  <div class="page-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>菜单管理</span>
          <el-button type="primary" @click="handleAdd()" v-if="userStore.hasPermission('system:menu:add')">新增菜单</el-button>
        </div>
      </template>

      <el-table :data="tableData" v-loading="loading" row-key="id" :tree-props="{ children: 'children' }" default-expand-all>
        <el-table-column prop="menuName" label="菜单名称" min-width="180" />
        <el-table-column prop="menuType" label="类型" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.menuType === 1">目录</el-tag>
            <el-tag v-else-if="row.menuType === 2" type="success">菜单</el-tag>
            <el-tag v-else type="warning">按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="icon" label="图标" width="60">
          <template #default="{ row }">
            <el-icon v-if="row.icon"><component :is="row.icon" /></el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="路由路径" />
        <el-table-column prop="permission" label="权限标识" />
        <el-table-column prop="sortOrder" label="排序" width="60" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleAdd(row.id)" v-if="row.menuType !== 3 && userStore.hasPermission('system:menu:add')">新增</el-button>
            <el-button link type="primary" @click="handleEdit(row)" v-if="userStore.hasPermission('system:menu:edit')">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row.id)" v-if="userStore.hasPermission('system:menu:delete')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="formRules" label-width="100px">
        <el-form-item label="上级菜单">
          <el-tree-select v-model="form.parentId" :data="menuTreeOptions" :props="{ label: 'menuName', value: 'id', children: 'children' }" check-strictly placeholder="选择上级菜单（为空则为顶级）" clearable style="width: 100%" />
        </el-form-item>
        <el-form-item label="菜单类型" prop="menuType">
          <el-radio-group v-model="form.menuType">
            <el-radio :value="1">目录</el-radio>
            <el-radio :value="2">菜单</el-radio>
            <el-radio :value="3">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item label="路由路径" v-if="form.menuType !== 3">
          <el-input v-model="form.path" placeholder="请输入路由路径" />
        </el-form-item>
        <el-form-item label="组件路径" v-if="form.menuType === 2">
          <el-input v-model="form.component" placeholder="如：system/user/index" />
        </el-form-item>
        <el-form-item label="权限标识">
          <el-input v-model="form.permission" placeholder="如：system:user:list" />
        </el-form-item>
        <el-form-item label="图标" v-if="form.menuType !== 3">
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
import { getMenuTree, saveMenu, deleteMenu } from '@/api/user'
import { useUserStore } from '@/store/user'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'

const userStore = useUserStore()

const loading = ref(false)
const submitLoading = ref(false)
const tableData = ref<any[]>([])
const dialogVisible = ref(false)
const dialogTitle = ref('新增菜单')

const menuTreeOptions = computed(() => [{ id: 0, menuName: '顶级菜单', children: tableData.value }])

const formRef = ref<FormInstance>()
const form = reactive<any>({ id: undefined, parentId: 0, menuName: '', menuType: 1, path: '', component: '', permission: '', icon: '', sortOrder: 0, status: 1 })

const formRules: FormRules = {
  menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  menuType: [{ required: true, message: '请选择菜单类型', trigger: 'change' }]
}

onMounted(() => fetchData())

async function fetchData() {
  loading.value = true
  try {
    const data: any = await getMenuTree()
    tableData.value = data || []
  } finally {
    loading.value = false
  }
}

function handleAdd(parentId?: number) {
  dialogTitle.value = '新增菜单'
  Object.assign(form, { id: undefined, parentId: parentId || 0, menuName: '', menuType: 1, path: '', component: '', permission: '', icon: '', sortOrder: 0, status: 1 })
  dialogVisible.value = true
}

function handleEdit(row: any) {
  dialogTitle.value = '编辑菜单'
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value?.validate()
  if (!valid) return
  submitLoading.value = true
  try {
    await saveMenu(form)
    ElMessage.success('操作成功')
    dialogVisible.value = false
    await fetchData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(id: number) {
  await ElMessageBox.confirm('确认删除该菜单及其子菜单？', '提示', { type: 'warning' })
  await deleteMenu(id)
  ElMessage.success('删除成功')
  await fetchData()
}
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
