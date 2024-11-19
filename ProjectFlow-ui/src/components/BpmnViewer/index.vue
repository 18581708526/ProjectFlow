<template>
  <div ref="canvas" class="canvas"></div>
</template>

<script>
import {genProcessDiagramTest} from "@/api/processwacth/processwacth";
import BpmnModeler from 'bpmn-js/lib/Modeler';

export default {
  props: {
    taskId: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      bpmnModeler: null
    };
  },
  mounted() {
    this.initBpmnModeler();
    this.fetchBpmnXml();
  },
  methods: {
    initBpmnModeler() {
      this.bpmnModeler = new BpmnModeler({
        container: this.$refs.canvas
      });
    },
    async fetchBpmnXml() {
      try {
        genProcessDiagramTest(this.taskId).then(async response => {
          const bpmnXml = response.data.bpmn20Xml;
          await this.importBpmnXml(bpmnXml);
        });

      } catch (error) {
        console.error('返回流程配置文件失败！', error);
      }
    },
    async importBpmnXml(bpmnXml) {
      try {
        const result = await this.bpmnModeler.importXML(bpmnXml);
        if (result.warnings && result.warnings.length) {
          console.warn('流程图警告信息：', result.warnings);
        }
      } catch (err) {
        console.error('导出流程图失败', err);
      }
    }
  }
};
</script>

<style scoped>.canvas {
  height: 100vh;
  width: 100%;
  position: relative;
}

.canvas::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: linear-gradient(to right, #e0e0e0 1px, transparent 1px),
  linear-gradient(to bottom, #e0e0e0 1px, transparent 1px);
  background-size: 20px 20px;
  pointer-events: none; /* 确保网格不影响交互 */
  z-index: -1; /* 确保网格在内容下方 */
}
</style>
