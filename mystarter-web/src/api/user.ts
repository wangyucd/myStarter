import request from '@/utils/request'

/** 登录 */
export function login(data: { username: string; password: string }) {
  return request.post('/user/auth/login', data)
}

/** 登出 */
export function logout() {
  return request.post('/user/auth/logout')
}

/** 获取当前用户信息 */
export function getUserInfo() {
  return request.get('/user/auth/userInfo')
}

/** 获取当前用户菜单 */
export function getUserMenus() {
  return request.get('/user/auth/menus')
}

/** 分页查询用户 */
export function getUserPage(params: any) {
  return request.get('/user/sysUser/page', { params })
}

/** 保存用户 */
export function saveUser(data: any) {
  return request.post('/user/sysUser/save', data)
}

/** 删除用户 */
export function deleteUser(id: number) {
  return request.delete(`/user/sysUser/${id}`)
}

/** 查询角色列表 */
export function getRoleList() {
  return request.get('/user/sysRole/list')
}

/** 保存角色 */
export function saveRole(data: any) {
  return request.post('/user/sysRole/save', data)
}

/** 删除角色 */
export function deleteRole(id: number) {
  return request.delete(`/user/sysRole/${id}`)
}

/** 查询菜单树 */
export function getMenuTree() {
  return request.get('/user/sysMenu/tree')
}

/** 保存菜单 */
export function saveMenu(data: any) {
  return request.post('/user/sysMenu/save', data)
}

/** 删除菜单 */
export function deleteMenu(id: number) {
  return request.delete(`/user/sysMenu/${id}`)
}
