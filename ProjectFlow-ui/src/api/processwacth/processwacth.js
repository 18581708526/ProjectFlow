import request from '@/utils/request'

// 查询流程监控列表
export function listProcesswacth(query) {
  return request({
    url: '/myinitprocess/myinitiprocess/list',
    method: 'get',
    params: query
  })
}

// 查询流程监控详细
export function getProcesswacth(wfWfid) {
  return request({
    url: '/processwacth/processwacth/' + wfWfid,
    method: 'get'
  })
}

// 新增流程监控
export function addProcesswacth(data) {
  return request({
    url: '/processwacth/processwacth',
    method: 'post',
    data: data
  })
}

// 修改流程监控
export function updateProcesswacth(data) {
  return request({
    url: '/processwacth/processwacth',
    method: 'put',
    data: data
  })
}

// 删除流程监控
export function delProcesswacth(wfWfid) {
  return request({
    url: '/processwacth/processwacth/' + wfWfid,
    method: 'delete'
  })
}
export function fetchFlowInstanceInfo(wfTaskid) {
  return request({
    url: '/leaveProcess/flowInstanceInfo/' + wfTaskid,
    method: 'get'
  })
}


export function genProcessDiagramTest(wfTaskid) {
  return request({
    url: '/leaveProcess/genProcessDiagramTest/' + wfTaskid,
    method: 'get'
  })
}
