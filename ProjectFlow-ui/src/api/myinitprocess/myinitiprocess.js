import request from '@/utils/request'

// 查询我的发起列表
export function listMyinitiprocess(query) {
  return request({
    url: '/myinitprocess/myinitiprocess/list',
    method: 'get',
    params: query
  })
}

// 查询我的发起详细
export function getMyinitiprocess(wfWfid) {
  return request({
    url: '/myinitprocess/myinitiprocess/' + wfWfid,
    method: 'get'
  })
}

// 新增我的发起
export function addMyinitiprocess(data) {
  return request({
    url: '/myinitprocess/myinitiprocess',
    method: 'post',
    data: data
  })
}

// 修改我的发起
export function updateMyinitiprocess(data) {
  return request({
    url: '/myinitprocess/myinitiprocess',
    method: 'put',
    data: data
  })
}

// 删除我的发起
export function delMyinitiprocess(wfWfid) {
  return request({
    url: '/myinitprocess/myinitiprocess/' + wfWfid,
    method: 'delete'
  })
}
export function Toviewdiagram(taskid) {
  return request({
    url: '/leaveProcess/processDiagram/' + taskid,
    method: 'get'
  })
}

