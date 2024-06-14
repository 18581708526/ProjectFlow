import request from '@/utils/request'

// 查询文件管理列表
export function listFiledetail(query) {
  return request({
    url: '/filedetail/filedetail/list',
    method: 'get',
    params: query
  })
}

// 查询文件管理详细
export function getFiledetail(id) {
  return request({
    url: '/filedetail/filedetail/' + id,
    method: 'get'
  })
}

// 新增文件管理
export function addFiledetail(data) {
  return request({
    url: '/filedetail/filedetail',
    method: 'post',
    data: data
  })
}

// 修改文件管理
export function updateFiledetail(data) {
  return request({
    url: '/filedetail/filedetail',
    method: 'put',
    data: data
  })
}

// 删除文件管理
export function delFiledetail(id) {
  return request({
    url: '/filedetail/filedetail/' + id,
    method: 'delete'
  })
}
