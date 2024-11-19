<template>
  <div>
    <h1>流程图查看器</h1>
    <BpmnViewer :taskId="taskId" />
  </div>
</template>

<script>

import BpmnViewer from '@/components/BpmnViewer';
export default {
  components: {
    BpmnViewer
  },
  props: {
    taskId: {
      type: String,
      required: true
    }
  }
}
</script>
