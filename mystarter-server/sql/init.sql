-- ============================================
-- MyStarter 数据库初始化脚本
-- 数据库：mystarter_db
-- ============================================

CREATE DATABASE IF NOT EXISTS mystarter_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE mystarter_db;

-- ============================================
-- 用户模块
-- ============================================

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    username VARCHAR(64) NOT NULL COMMENT '用户名',
    password VARCHAR(128) NOT NULL COMMENT '密码（BCrypt加密）',
    nickname VARCHAR(64) COMMENT '昵称',
    email VARCHAR(128) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(256) COMMENT '头像URL',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    role_name VARCHAR(64) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(64) NOT NULL COMMENT '角色编码',
    description VARCHAR(256) COMMENT '描述',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    sort_order INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 菜单权限表
CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    menu_name VARCHAR(64) NOT NULL COMMENT '菜单名称',
    menu_type TINYINT NOT NULL COMMENT '类型：1-目录 2-菜单 3-按钮',
    path VARCHAR(256) COMMENT '路由路径',
    component VARCHAR(256) COMMENT '组件路径',
    permission VARCHAR(128) COMMENT '权限标识',
    icon VARCHAR(64) COMMENT '图标',
    sort_order INT DEFAULT 0 COMMENT '排序',
    visible TINYINT DEFAULT 1 COMMENT '是否可见：0-隐藏 1-显示',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    UNIQUE KEY uk_role_menu (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- ============================================
-- 商品模块
-- ============================================

-- 商品分类表
CREATE TABLE IF NOT EXISTS pms_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    category_name VARCHAR(64) NOT NULL COMMENT '分类名称',
    icon VARCHAR(256) COMMENT '图标',
    sort_order INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- 商品表
CREATE TABLE IF NOT EXISTS pms_product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    category_id BIGINT COMMENT '分类ID',
    product_name VARCHAR(128) NOT NULL COMMENT '商品名称',
    product_code VARCHAR(64) COMMENT '商品编码',
    description TEXT COMMENT '商品描述',
    price DECIMAL(10,2) DEFAULT 0 COMMENT '价格',
    stock INT DEFAULT 0 COMMENT '库存',
    cover_image VARCHAR(256) COMMENT '封面图',
    status TINYINT DEFAULT 1 COMMENT '状态：0-下架 1-上架',
    sort_order INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ============================================
-- 初始化数据
-- ============================================

-- 初始化管理员账号（密码：123456，BCrypt加密）
INSERT INTO sys_user (username, password, nickname, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '超级管理员', 1);

-- 初始化超级管理员角色
INSERT INTO sys_role (role_name, role_code, description, status, sort_order) VALUES
('超级管理员', 'admin', '拥有所有权限', 1, 0);

-- 绑定管理员用户和角色
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 初始化菜单数据
-- 一级目录：系统管理
INSERT INTO sys_menu (id, parent_id, menu_name, menu_type, path, component, permission, icon, sort_order, visible, status) VALUES
(1, 0, '系统管理', 1, '/system', NULL, NULL, 'Setting', 1, 1, 1),
(2, 1, '用户管理', 2, 'user', 'system/user/index', 'system:user:list', 'User', 1, 1, 1),
(3, 1, '角色管理', 2, 'role', 'system/role/index', 'system:role:list', 'UserFilled', 2, 1, 1),
(4, 1, '菜单管理', 2, 'menu', 'system/menu/index', 'system:menu:list', 'Menu', 3, 1, 1),
-- 用户管理按钮
(5, 2, '新增用户', 3, NULL, NULL, 'system:user:add', NULL, 1, 1, 1),
(6, 2, '编辑用户', 3, NULL, NULL, 'system:user:edit', NULL, 2, 1, 1),
(7, 2, '删除用户', 3, NULL, NULL, 'system:user:delete', NULL, 3, 1, 1),
-- 角色管理按钮
(8, 3, '新增角色', 3, NULL, NULL, 'system:role:add', NULL, 1, 1, 1),
(9, 3, '编辑角色', 3, NULL, NULL, 'system:role:edit', NULL, 2, 1, 1),
(10, 3, '删除角色', 3, NULL, NULL, 'system:role:delete', NULL, 3, 1, 1),
-- 菜单管理按钮
(11, 4, '新增菜单', 3, NULL, NULL, 'system:menu:add', NULL, 1, 1, 1),
(12, 4, '编辑菜单', 3, NULL, NULL, 'system:menu:edit', NULL, 2, 1, 1),
(13, 4, '删除菜单', 3, NULL, NULL, 'system:menu:delete', NULL, 3, 1, 1),
-- 一级目录：商品管理
(14, 0, '商品管理', 1, '/product', NULL, NULL, 'Goods', 2, 1, 1),
(15, 14, '商品列表', 2, 'list', 'product/product/index', 'product:product:list', 'List', 1, 1, 1),
(16, 14, '商品分类', 2, 'category', 'product/category/index', 'product:category:list', 'Files', 2, 1, 1),
-- 商品管理按钮
(17, 15, '新增商品', 3, NULL, NULL, 'product:product:add', NULL, 1, 1, 1),
(18, 15, '编辑商品', 3, NULL, NULL, 'product:product:edit', NULL, 2, 1, 1),
(19, 15, '删除商品', 3, NULL, NULL, 'product:product:delete', NULL, 3, 1, 1),
-- 分类管理按钮
(20, 16, '新增分类', 3, NULL, NULL, 'product:category:add', NULL, 1, 1, 1),
(21, 16, '编辑分类', 3, NULL, NULL, 'product:category:edit', NULL, 2, 1, 1),
(22, 16, '删除分类', 3, NULL, NULL, 'product:category:delete', NULL, 3, 1, 1);

-- 超级管理员拥有所有菜单权限
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7),
(1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13),
(1, 14), (1, 15), (1, 16), (1, 17), (1, 18), (1, 19),
(1, 20), (1, 21), (1, 22);
