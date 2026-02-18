import request from '@/utils/request'

/** 分页查询商品 */
export function getProductPage(params: any) {
  return request.get('/product/product/page', { params })
}

/** 保存商品 */
export function saveProduct(data: any) {
  return request.post('/product/product/save', data)
}

/** 删除商品 */
export function deleteProduct(id: number) {
  return request.delete(`/product/product/${id}`)
}

/** 查询分类树 */
export function getCategoryTree() {
  return request.get('/product/category/tree')
}

/** 保存分类 */
export function saveCategory(data: any) {
  return request.post('/product/category/save', data)
}

/** 删除分类 */
export function deleteCategory(id: number) {
  return request.delete(`/product/category/${id}`)
}
