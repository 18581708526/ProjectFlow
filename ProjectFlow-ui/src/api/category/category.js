import request from '@/utils/request'

// 查询类别管理列表
export function listCategory(query) {
  return request({
    url: '/category/category/list',
    method: 'get',
    params: query
  })
}

// 查询类别管理详细
export function getCategory(id) {
  return request({
    url: '/category/category/' + id,
    method: 'get'
  })
}

// 新增类别管理
export function addCategory(data) {
  return request({
    url: '/category/category',
    method: 'post',
    data: data
  })
}

// 修改类别管理
export function updateCategory(data) {
  return request({
    url: '/category/category',
    method: 'put',
    data: data
  })
}

// 删除类别管理
export function delCategory(id) {
  return request({
    url: '/category/category/' + id,
    method: 'delete'
  })
}

// 获取类别数
export function categoryTreeSelect() {
  return request({
    url: '/category/category/getcategoryTree',
    method: 'get'
  })
}

export function getcategoryexcludechrild(categoryid) {
  return request({
    url: '/category/category/exclude/' + categoryid,
    method: 'get'
  })
}
