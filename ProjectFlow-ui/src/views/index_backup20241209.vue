<template>
  <div class="step-tracker">
    <!-- 步骤条容器 -->
    <div class="steps">
      <transition-group name="fade" tag="div">
        <div
          v-for="(step, index) in steps"
          :key="index"
          :class="['step', step.status]"
        >
          <div class="step-label">{{ step.label }}</div>
          <div class="step-status">{{ step.status }}</div>
        </div>
      </transition-group>
    </div>

    <!-- 下一步按钮 -->
    <button @click="nextStep">下一步</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      steps: [
        { label: '步骤 1', status: 'completed' },
        { label: '步骤 2', status: 'current' },
        { label: '步骤 3', status: 'upcoming' },
        { label: '步骤 4', status: 'upcoming' }
      ]
    };
  },
  methods: {
    nextStep() {
      // 查找当前步骤
      let currentIndex = this.steps.findIndex(step => step.status === 'current');
      // 如果当前步骤存在并且不是最后一步，执行步骤切换
      if (currentIndex !== -1 && currentIndex < this.steps.length - 1) {
        // 更新当前步骤为完成状态
        this.steps[currentIndex].status = 'completed';
        // 更新下一个步骤为当前步骤
        this.steps[currentIndex + 1].status = 'current';
      }
    }
  }
};
</script>

<style scoped>
/* 步骤条的外部容器 */
.step-tracker {
  display: flex;
  flex-direction: column;
  align-items: center;
}

/* 步骤容器 */
.steps {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

/* 每个步骤的样式 */
.step {
  padding: 10px;
  text-align: center;
  width: 100px;
  border-radius: 8px;
  transition: background-color 0.3s, opacity 0.3s;
}

/* 完成的步骤样式 */
.step.completed {
  background-color: green;
  color: white;
  opacity: 1;
}

/* 当前步骤样式 */
.step.current {
  background-color: blue;
  color: white;
  opacity: 1;
}

/* 即将到来的步骤样式 */
.step.upcoming {
  background-color: gray;
  color: white;
  opacity: 0.6;
}

/* 按钮样式 */
button {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #45a049;
}

/* 动画效果 */
.fade-enter-active, .fade-leave-active {
  transition: opacity 1s;
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>
