import request from '@/utils/request'

// 查询凑数功能列表
export function listMakenum(query) {
  return request({
    url: '/makenum/makenum/list',
    method: 'get',
    params: query
  })
}

// 查询凑数功能详细
export function getMakenum(id) {
  return request({
    url: '/makenum/makenum/' + id,
    method: 'get'
  })
}

// 新增凑数功能
export function addMakenum(data) {
  return request({
    url: '/makenum/makenum',
    method: 'post',
    data: data
  })
}

// 修改凑数功能
export function updateMakenum(data) {
  return request({
    url: '/makenum/makenum',
    method: 'put',
    data: data
  })
}

// 删除凑数功能
export function delMakenum(id) {
  return request({
    url: '/makenum/makenum/' + id,
    method: 'delete'
  })
}
