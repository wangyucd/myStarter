<template>
  <div class="page-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>角色管理</span>
          <el-button type="primary" @click="handleAdd" v-if="userStore.hasPermission('system:role:add')">新增角色</el-button>
        </div>
      </template>

      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="roleName" label="角色名称" />
        <el-table-column prop="roleCode" label="角色编码" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)" v-if="userStore.hasPermission('system:role:edit')">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row.id)" v-if="userStore.hasPermission('system:role:delete')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="formRules" label-width="80px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色编码" prop="roleCode">
          <el-input v-model="form.roleCode" placeholder="请输入角色编码" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-tree
            ref="menuTreeRef"
            :data="menuTree"
            show-checkbox
            node-key="id"
            :default-checked-keys="form.menuIds"
            :props="{ label: 'menuName', children: 'children' }"
          />
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
import { ref, reactive, onMounted } from 'vue'
import { getRoleList, saveRole, deleteRole, getMenuTree } from '@/api/user'
import { useUserStore } from '@/store/user'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import type { ElTree } from 'element-plus'

const userStore = useUserStore()

const loading = ref(false)
const submitLoading = ref(false)
const tableData = ref<any[]>([])
const dialogVisible = ref(false)
const dialogTitle = ref('新增角色')
const menuTree = ref<any[]>([])
const menuTreeRef = ref<InstanceType<typeof ElTree>>()

const formRef = ref<FormInstance>()
const form = reactive<any>({ id: undefined, roleName: '', roleCode: '', description: '', status: 1, menuIds: [] })

const formRules: FormRules = {
  roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  roleCode: [{ required: true, message: '请输入角色编码', trigger: 'blur' }]
}

onMounted(async () => {
  await fetchData()
  const tree: any = await getMenuTree()
  menuTree.value = tree || []
})

async function fetchData() {
  loading.value = true
  try {
    const data: any = await getRoleList()
    tableData.value = data || []
  } finally {
    loading.value = false
  }
}

function handleAdd() {
  dialogTitle.value = '新增角色'
  Object.assign(form, { id: undefined, roleName: '', roleCode: '', description: '', status: 1, menuIds: [] })
  dialogVisible.value = true
}

function handleEdit(row: any) {
  dialogTitle.value = '编辑角色'
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value?.validate()
  if (!valid) return
  submitLoading.value = true
  try {
    const checkedKeys = menuTreeRef.value?.getCheckedKeys(false) || []
    const halfCheckedKeys = menuTreeRef.value?.getHalfCheckedKeys() || []
    form.menuIds = [...checkedKeys, ...halfCheckedKeys]
    await saveRole(form)
    ElMessage.success('操作成功')
    dialogVisible.value = false
    await fetchData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(id: number) {
  await ElMessageBox.confirm('确认删除该角色？', '提示', { type: 'warning' })
  await deleteRole(id)
  ElMessage.success('删除成功')
  await fetchData()
}
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
