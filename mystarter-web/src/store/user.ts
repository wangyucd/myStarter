import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login as loginApi, logout as logoutApi, getUserInfo as getUserInfoApi, getUserMenus } from '@/api/user'
import type { RouteRecordRaw } from 'vue-router'
import router from '@/router'

/** 菜单项 */
interface MenuItem {
  id: number
  parentId: number
  menuName: string
  menuType: number
  path: string
  component: string
  permission: string
  icon: string
  children?: MenuItem[]
}

/** 用户信息 */
interface UserInfo {
  id: number
  username: string
  nickname: string
  avatar: string
  token: string
  permissions: string[]
  roles: string[]
}

/**
 * 用户状态管理
 */
export const useUserStore = defineStore('user', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const userInfo = ref<Partial<UserInfo>>({})
  const permissions = ref<string[]>([])
  const roles = ref<string[]>([])
  const menuList = ref<MenuItem[]>([])
  const dynamicRoutes = ref<RouteRecordRaw[]>([])
  const routesAdded = ref(false)

  const isLoggedIn = computed(() => !!token.value)

  /** 登录 */
  async function loginAction(loginData: { username: string; password: string }) {
    const data: any = await loginApi(loginData)
    token.value = data.token
    localStorage.setItem('token', data.token)
    userInfo.value = data
    permissions.value = data.permissions || []
    roles.value = data.roles || []
  }

  /** 获取用户信息 */
  async function getUserInfoAction() {
    const data: any = await getUserInfoApi()
    userInfo.value = data
    permissions.value = data.permissions || []
    roles.value = data.roles || []
  }

  /** 获取动态菜单并生成路由 */
  async function generateRoutes() {
    const data: any = await getUserMenus()
    menuList.value = data
    // 将后端菜单转换为 vue-router 路由
    const routes = convertMenuToRoutes(data)
    dynamicRoutes.value = routes
    // 动态添加路由
    routes.forEach((route: RouteRecordRaw) => {
      router.addRoute('layout', route)
    })
    routesAdded.value = true
  }

  /** 登出 */
  async function logout() {
    try {
      await logoutApi()
    } catch {
      // 忽略登出失败
    }
    token.value = ''
    userInfo.value = {}
    permissions.value = []
    roles.value = []
    menuList.value = []
    dynamicRoutes.value = []
    routesAdded.value = false
    localStorage.removeItem('token')
  }

  /** 检查权限 */
  function hasPermission(perm: string): boolean {
    if (roles.value.includes('admin')) return true
    return permissions.value.includes(perm)
  }

  return {
    token, userInfo, permissions, roles, menuList, dynamicRoutes, routesAdded, isLoggedIn,
    loginAction, getUserInfoAction, generateRoutes, logout, hasPermission
  }
})

/**
 * 动态加载页面组件
 */
const modules = import.meta.glob('../views/**/*.vue')

/**
 * 将后端菜单数据转换为 vue-router 路由
 */
function convertMenuToRoutes(menus: any[]): RouteRecordRaw[] {
  const routes: RouteRecordRaw[] = []
  menus.forEach((menu: any) => {
    if (menu.menuType === 1) {
      // 目录类型 - 递归处理子菜单
      if (menu.children && menu.children.length > 0) {
        const childRoutes = convertMenuToRoutes(menu.children)
        routes.push(...childRoutes)
      }
    } else if (menu.menuType === 2) {
      // 菜单类型 - 生成路由
      const componentPath = `../views/${menu.component}.vue`
      const route: RouteRecordRaw = {
        path: menu.path,
        name: menu.path.replace(/\//g, '-'),
        component: modules[componentPath] || (() => import('../views/error/404.vue')),
        meta: {
          title: menu.menuName,
          icon: menu.icon,
          permission: menu.permission
        }
      }
      routes.push(route)
    }
  })
  return routes
}
