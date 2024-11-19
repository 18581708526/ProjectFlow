import request from '@/utils/request'

// 查询流程定义列表
export function listWorkflow(query) {
  return request({
    url: '/workflow/workflow/list',
    method: 'get',
    params: query
  })
}

// 查询流程定义详细
export function getWorkflow(id) {
  return request({
    url: '/workflow/workflow/' + id,
    method: 'get'
  })
}

// 新增流程定义
export function addWorkflow(data) {
  return request({
    url: '/workflow/workflow',
    method: 'post',
    data: data
  })
}

// 修改流程定义
export function updateWorkflow(data) {
  return request({
    url: '/workflow/workflow',
    method: 'put',
    data: data
  })
}

// 删除流程定义
export function delWorkflow(id) {
  return request({
    url: '/workflow/workflow/' + id,
    method: 'delete'
  })
}

//根据本次流程定义发起流程

export function iniProcessSubmit(data) {
  return request({
    url: '/leaveProcess/add/stu',
    method: 'post',
    data: data
  })
}

export function ableProcess(id) {
  return request({
    url: '/workflow/workflow/able/' + id,
    method: 'get'
  })
}
export function disableProcess(id) {
  return request({
    url: '/workflow/workflow/disable/' + id,
    method: 'get'
  })
}
