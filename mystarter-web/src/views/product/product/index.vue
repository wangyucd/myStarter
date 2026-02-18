<template>
  <div class="page-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>商品管理</span>
          <el-button type="primary" @click="handleAdd" v-if="userStore.hasPermission('product:product:add')">新增商品</el-button>
        </div>
      </template>

      <el-form :inline="true" :model="queryForm" class="search-form">
        <el-form-item label="商品名称">
          <el-input v-model="queryForm.productName" placeholder="输入商品名称" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryForm.status" placeholder="选择状态" clearable>
            <el-option label="上架" :value="1" />
            <el-option label="下架" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchData">搜索</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column prop="productCode" label="商品编码" width="120" />
        <el-table-column prop="price" label="价格" width="100">
          <template #default="{ row }">
            ¥{{ row.price }}
          </template>
        </el-table-column>
        <el-table-column prop="stock" label="库存" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '上架' : '下架' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)" v-if="userStore.hasPermission('product:product:edit')">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row.id)" v-if="userStore.hasPermission('product:product:delete')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrapper">
        <el-pagination
          v-model:current-page="queryForm.pageNum"
          v-model:page-size="queryForm.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="fetchData"
          @current-change="fetchData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="formRules" label-width="80px">
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="商品编码">
          <el-input v-model="form.productCode" placeholder="请输入商品编码" />
        </el-form-item>
        <el-form-item label="分类">
          <el-tree-select v-model="form.categoryId" :data="categoryTree" :props="{ label: 'categoryName', value: 'id', children: 'children' }" check-strictly placeholder="选择分类" clearable style="width: 100%" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input-number v-model="form.price" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="库存">
          <el-input-number v-model="form.stock" :min="0" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入商品描述" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" active-text="上架" inactive-text="下架" />
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
import { getProductPage, saveProduct, deleteProduct, getCategoryTree } from '@/api/product'
import { useUserStore } from '@/store/user'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'

const userStore = useUserStore()

const loading = ref(false)
const submitLoading = ref(false)
const tableData = ref<any[]>([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('新增商品')
const categoryTree = ref<any[]>([])

const queryForm = reactive({ productName: '', status: undefined as number | undefined, pageNum: 1, pageSize: 10 })

const formRef = ref<FormInstance>()
const form = reactive<any>({ id: undefined, productName: '', productCode: '', categoryId: undefined, price: 0, stock: 0, description: '', status: 1 })

const formRules: FormRules = {
  productName: [{ required: true, message: '请输入商品名称', trigger: 'blur' }]
}

onMounted(async () => {
  await fetchData()
  const tree: any = await getCategoryTree()
  categoryTree.value = tree || []
})

async function fetchData() {
  loading.value = true
  try {
    const data: any = await getProductPage(queryForm)
    tableData.value = data.list || []
    total.value = data.total || 0
  } finally {
    loading.value = false
  }
}

function resetQuery() {
  queryForm.productName = ''
  queryForm.status = undefined
  queryForm.pageNum = 1
  fetchData()
}

function handleAdd() {
  dialogTitle.value = '新增商品'
  Object.assign(form, { id: undefined, productName: '', productCode: '', categoryId: undefined, price: 0, stock: 0, description: '', status: 1 })
  dialogVisible.value = true
}

function handleEdit(row: any) {
  dialogTitle.value = '编辑商品'
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value?.validate()
  if (!valid) return
  submitLoading.value = true
  try {
    await saveProduct(form)
    ElMessage.success('操作成功')
    dialogVisible.value = false
    await fetchData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(id: number) {
  await ElMessageBox.confirm('确认删除该商品？', '提示', { type: 'warning' })
  await deleteProduct(id)
  ElMessage.success('删除成功')
  await fetchData()
}
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 16px; }
.pagination-wrapper { display: flex; justify-content: flex-end; margin-top: 16px; }
</style>
