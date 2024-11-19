import request from '@/utils/request'

// 查询模型管理列表
export function listModel(query) {
  return request({
    url: '/model/model/list',
    method: 'get',
    params: query
  })
}

// 查询模型管理详细
export function getModel(id) {
  return request({
    url: '/model/model/' + id,
    method: 'get'
  })
}

// 新增模型管理
export function addModel(data) {
  return request({
    url: '/model/model',
    method: 'post',
    data: data
  })
}

// 修改模型管理
export function updateModel(data) {
  return request({
    url: '/model/model',
    method: 'put',
    data: data
  })
}

// 删除模型管理
export function delModel(id) {
  return request({
    url: '/model/model/' + id,
    method: 'delete'
  })
}
export function designProcess() {
  return request({
    url: '/model/model/designProcess',
    method: 'get'
  })
}
export function updateModelNew(data) {
  return request({
    url: '/model/model/updateModel',
    method: 'post',
    data: data
  })
}

export function deployePro(modeId) {
  return request({
    url: '/process/deployPro/'+modeId,
    method: 'get'
  })
}
export function getModelResource(modeId) {
  return request({
    url: '/process/getModelResource/' + modeId,
    method: 'get'
  })
}
