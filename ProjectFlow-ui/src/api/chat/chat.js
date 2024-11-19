import request from "@/utils/request";

export function aichatreq(data) {
  return request({
    url: '/ai/ask',
    method: 'post',
    data: data
  })
}
