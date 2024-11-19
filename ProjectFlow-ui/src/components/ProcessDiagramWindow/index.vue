<template>
  <div v-if="visible" class="modal">
    <div class="modal-content">
      <span class="close" @click="close">&times;</span>
      <img :src="url" alt="流程图" class="process-diagram" />
    </div>
  </div>
</template>

<script>export default {
  data() {
    return {
      visible: false,
      url: ''
    };
  },
  methods: {
    open(url) {
      this.url = url;
      this.visible = true;
    },
    close() {
      this.visible = false;
    }
  }
};
</script>

<style scoped>.modal {
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 1000px;
  max-height: 800px;
  overflow: auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.process-diagram {
  max-width: 100%;
  max-height: 100%;
}
</style>
