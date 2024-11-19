
<template>
  <div class="chat-container">

    <!-- 模型切换 -->
    <div class="model-switch">
      <label for="modelSelect">选择模型：</label>
      <select v-model="selectedModel">
        <option value="local">本地模型</option>
        <option value="deepseek">DeepSeek 在线模型</option>
      </select>
    </div>

    <!-- 消息展示区域 -->
    <div class="message-area" ref="messageArea">
      <div v-for="(message, index) in messages" :key="index" :class="['message-bubble', message.sender]">
        <div class="message-content">
          {{ message.content }}
        </div>
        <div class="message-time">
          {{ message.time }}
        </div>
      </div>
    </div>

    <!-- 输入区域 -->
    <div class="input-area">
      <textarea
        v-model="inputMessage"
        @keyup.enter.exact.prevent="sendMessage"
        placeholder="输入消息..."
        rows="3"
      ></textarea>
      <button @click="sendMessage" :disabled="isSending">
        {{ isSending ? '发送中...' : '发送' }}
      </button>
    </div>
  </div>
</template>

<script>
import { aichatreq } from "@/api/chat/chat"; // 假设这个函数是你的API请求方法

export default {
  data() {
    return {
      inputMessage: '',
      messages: [],
      isSending: false,
      botMessage: '', // 存储机器人的逐步消息
      isBotTyping: false, // 标记机器人是否正在输入
      selectedModel: 'local' // 默认使用local本地模型 DeepSeek
    };
  },
  methods: {
    async sendMessage() {
      if (!this.inputMessage.trim() || this.isSending) return;

      this.isSending = true;

      // 添加用户的消息
      const userMessage = {
        content: this.inputMessage,
        sender: 'user',
        time: this.getCurrentTime(),
      };
      this.messages.push(userMessage);

      // 发送请求并处理机器人消息
      try {
        this.isBotTyping = true; // 设置机器人正在输入
        this.botMessage = ''; // 清空当前机器人的消息
        const botMessage = {
          content: this.botMessage,
          sender: 'ai',
          time: this.getCurrentTime(),
        };
        this.messages.push(botMessage);

        // 请求获取机器人的响应，流式输出
        await this.getResponse(this.inputMessage);

      } catch (error) {
        console.error('发送失败:', error);
        alert('发送失败，请检查控制台');
      } finally {
        this.inputMessage = '';
        this.isSending = false;
        this.isBotTyping = false;
        this.scrollToBottom();
      }
    },

    // 获取当前时间
    getCurrentTime() {
      const now = new Date();
      return `${now.getHours()}:${now.getMinutes().toString().padStart(2, '0')}`;
    },

    // 获取机器人响应并流式输出
    async getResponse(message) {
      try {
        const response = await fetch(`http://localhost:8088/ai/chat?type=${this.selectedModel}&message=${encodeURIComponent(message)}`);
        if (!response.ok) {
          throw new Error('网络响应不正常');
        }
        const reader = response.body.getReader();
        const decoder = new TextDecoder('utf-8');
        let done = false;

        // 使用 setInterval 逐步更新机器人的消息
        const intervalId = setInterval(async () => {
          const { value, done: doneReading } = await reader.read();
          done = doneReading;
          const chunk = decoder.decode(value, { stream: true });

          this.botMessage += this.cleanData(chunk); // 逐步添加机器人的消息

          // 更新最后一个消息框的内容
          this.messages[this.messages.length - 1].content = this.botMessage;

          // 更新滚动条
          this.$nextTick(() => {
            this.scrollToBottom();
          });

          if (done) {
            clearInterval(intervalId);
            this.isBotTyping = false; // 机器人输入结束
          }
        }, 20); // 每20毫秒更新一次

      } catch (error) {
        console.error('Error while fetching data:', error);
      }
    },

    // 清理数据，去掉多余的部分
    cleanData(data) {
      return data.replace(/data:/g, '').replace(/<\/?think>/g, '').replace(/\s+/g, ' ').trim();
    },

    // 滚动到最后一条消息
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.messageArea;
        container.scrollTop = container.scrollHeight;
      });
    }
  }
};
</script>

<style scoped>
.chat-container {
  max-width: 800px;
  margin: 20px auto;
  border-radius: 12px;
  overflow: hidden;
  background: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

/* 优化滚动条 */
.message-area::-webkit-scrollbar {
  width: 6px;
}
.message-area::-webkit-scrollbar-thumb {
  background-color: #bbb;
  border-radius: 3px;
}
.message-area::-webkit-scrollbar-track {
  background-color: transparent;
}

.message-area {
  display: flex;
  flex-direction: column; /* 让消息从上到下排列 */
  gap: 10px; /* 添加间距 */
  padding: 20px;
  background: linear-gradient(to bottom, #f9f9f9, #eaeaea);
  border-bottom: 1px solid #ddd;
  height: 500px;
  overflow-y: auto;
}

/* 消息气泡 */
.message-bubble {
  max-width: 70%;
  min-width: 50px;
  padding: 10px 15px;
  border-radius: 16px;
  word-break: break-word;
  animation: fadeIn 0.2s ease-in-out;
  display: flex;
  flex-direction: column;
}

/* 用户消息 */
.user {
  align-self: flex-end; /* 靠右 */
  background: #007bff;
  color: white;
  border-radius: 16px 16px 0 16px;
  box-shadow: 0 2px 5px rgba(0, 123, 255, 0.3);
}

/* AI 消息 */
.ai {
  align-self: flex-start; /* 靠左 */
  background: #e3f2fd;
  color: #333;
  border: 1px solid #ddd;
  border-radius: 16px 16px 16px 0;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.message-time {
  font-size: 12px;
  color: #666;
  margin-top: 4px;
  text-align: right;
}

.input-area {
  display: flex;
  padding: 15px;
  background: white;
  border-top: 1px solid #eee;
}

textarea {
  flex: 1;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 20px;
  resize: none;
  margin-right: 10px;
  font-size: 14px;
  box-shadow: inset 0 2px 5px rgba(0, 0, 0, 0.1);
}

button {
  padding: 10px 20px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  height: 40px;
  transition: background 0.2s;
}

button:hover {
  background: #0056b3;
}

button:disabled {
  background: #ccc;
  cursor: not-allowed;
}

/* 消息淡入动画 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.model-switch {
  display: flex;
  align-items: center;
  padding: 10px;
  background: white;
  border-bottom: 1px solid #ddd;
}
.model-switch label {
  margin-right: 10px;
  font-weight: bold;
}
select {
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ddd;
  background: white;
}
</style>
