import request from '@/utils/request'

// 查询我的待办列表
export function listMytodoprocess(query) {
  return request({
    url: '/mytodoprocess/mytodoprocess/list',
    method: 'get',
    params: query
  })
}

// 查询我的待办详细
export function getMytodoprocess(wfFwid) {
  return request({
    url: '/mytodoprocess/mytodoprocess/' + wfFwid,
    method: 'get'
  })
}

// 新增我的待办
export function addMytodoprocess(data) {
  return request({
    url: '/mytodoprocess/mytodoprocess',
    method: 'post',
    data: data
  })
}

// 修改我的待办
export function updateMytodoprocess(data) {
  return request({
    url: '/mytodoprocess/mytodoprocess',
    method: 'put',
    data: data
  })
}

// 删除我的待办
export function delMytodoprocess(wfFwid) {
  return request({
    url: '/mytodoprocess/mytodoprocess/' + wfFwid,
    method: 'delete'
  })
}

export function getProcessVaryies(wfTaskid) {
  return request({
    url: '/leaveProcess/getvariable/' + wfTaskid,
    method: 'get'
  })
}


