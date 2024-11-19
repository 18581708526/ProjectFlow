<template>
  <div>
    <BpmnCanvan :modelId="modelId" />
  </div>
</template>

<script>

import BpmnCanvan from '@/components/BpmnCanvan';
export default {
  components: {
    BpmnCanvan
  },
  computed: {
    modelId() {
      return this.$route.params.modelId;
    }
  }
  // props: {
  //   modelId: {
  //     type: String,
  //     required: true
  //   }
  // }
}
</script>





<!--<template>-->
<!--  <div ref="canvas" class="canvas"></div>-->
<!--</template>-->

<!--<script>-->
<!-- import BpmnModeler from 'bpmn-js/lib/Modeler';-->
<!-- import 'bpmn-js/dist/assets/diagram-js.css';-->
<!-- import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css';-->

<!--export default {-->
<!--  name: 'BpmnModeler',-->
<!--  data() {-->
<!--    return {-->
<!--      bpmnModeler: null,-->
<!--    };-->
<!--  },-->
<!--  mounted() {-->
<!--    this.bpmnModeler = new BpmnModeler({-->
<!--      container: this.$refs.canvas,-->
<!--    });-->

<!--    // 可以在这里加载一个默认的BPMN XML文件-->
<!--    //this.loadDefaultDiagram();-->
<!--  },-->
<!--  methods: {-->


<!--    // loadDefaultDiagram() {-->
<!--    //   const xml = `<?xml version="1.0" encoding="UTF-8"?>-->
<!--    //      <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"-->
<!--    //                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"-->
<!--    //                   xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd"-->
<!--    //                   id="Definitions_1"-->
<!--    //                   targetNamespace="http://bpmn.io/schema/bpmn">-->
<!--    //        <process id="Process_1" isExecutable="false">-->
<!--    //          <startEvent id="StartEvent_1"/>-->
<!--    //        </process>-->
<!--    //      </definitions>`;-->
<!--    //-->
<!--    //   this.bpmnModeler.importXML(xml, (err) => {-->
<!--    //     if (err) {-->
<!--    //       console.error('Failed to import BPMN diagram', err);-->
<!--    //     } else {-->
<!--    //       console.log('BPMN diagram imported successfully');-->
<!--    //     }-->
<!--    //   });-->
<!--    // },-->
<!--    //-->

<!--  },-->
<!--};-->
<!--</script>-->

<!--<style scoped>-->
<!--.canvas {-->
<!--  width: 100%;-->
<!--  height: 600px;-->
<!--}-->
<!--</style>-->
