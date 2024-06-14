import request from '@/utils/request'
// 新增filedetail
export function addFilefromMinio(data) {
  return request({
    url: '/minio/file/upload',
    method: 'post',
    data: data
  })
}
