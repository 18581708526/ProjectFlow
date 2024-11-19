<template>
  <div class="chart-container">
    <div id="main" style="width: 600px; height: 400px;"></div>
    <el-button icon="el-icon-refresh" size="mini" @click="toggleChartType">切换图表类型</el-button>
    <el-steps :active="active" align-center>
      <el-step v-for="(step, index) in steps" :key="index" :title="step.title" :description="step.description">
        <div slot="description" class="step-content">
          <img v-if="step.image" :src="step.image" alt="Step Image" />
          <p>{{ step.content }}</p>
        </div>
      </el-step>
    </el-steps>
    <el-button icon="el-icon-refresh" size="mini" @click="next">下一步</el-button>
  </div>


</template>

<script>
import logo from '@/assets/logo/logo.png'
import { deptAndUserSum } from "@/api/system/user";

export default {
  name: 'Index',

  data() {
    return {
      active:1,
      chartData: [],
      chartType: 'bar', // 初始图表类型为柱状图
      steps: [
        { title: "步骤1", description: "这是一段很长很长很长的描述性文字", content: "这是步骤1的详细信息", image: 'logo' },
        { title: "步骤2", description: "这是一段很长很长很长的描述性文字", content: "这是步骤2的详细信息", image: "" },
        { title: "步骤3", description: "这是一段很长很长很长的描述性文字", content: "这是步骤3的详细信息", image: "" },
        { title: "步骤4", description: "这是一段很长很长很长的描述性文字", content: "这是步骤4的详细信息", image: "" },
        { title: "步骤5", description: "这是一段很长很长很长的描述性文字", content: "这是步骤5的详细信息", image: "" }
      ]
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    next:function (){
      if(this.active===10){
        this.active=1;
      }//
      this.active=this.active+1;
    },
    prev() {
      if (this.active === 1) {
        this.active = this.steps.length;
      } else {
        this.active -= 1;
      }
    },
    async fetchData() {
      try {
        deptAndUserSum().then(response => {
          this.chartData = response.data;
          this.initChart();
        });
      } catch (error) {
        console.error('后台数据获取失败！！！', error);
      }
    },
    initChart() {
      // 基于准备好的dom，初始化echarts实例
      const chart = this.echarts.init(document.getElementById('main'));
      // 指定图表的配置项和数据
      let option = {
        title: {
          text: '部门人数'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}: {c} 人 ' // {b} 数据项名称, {c} 数据值, {d} 百分比
        },
        legend: {
          data: this.chartData.map(item => item.deptName)
        },
        series: [{
          name: '人数',
          type: this.chartType,
          data: this.chartData.map(item => ({ value: item.userNum, name: item.deptName })),
          label: {
            show: true,
            formatter: '{b}: {c} 人' // {b} 数据项名称 (部门名称), {c} 数据值 (人数)
          }
        }]
      };

      if (this.chartType === 'pie') {
        option.series[0].radius = '50%';
        delete option.xAxis;
        delete option.yAxis;
      } else {
        option.xAxis = {
          data: this.chartData.map(item => item.deptName)
        };
        option.yAxis = {};
        option.series[0].label.position = 'top';
      }

      // 使用刚指定的配置项和数据显示图表
      chart.setOption(option);
    },
    toggleChartType() {
      this.chartType = this.chartType === 'bar' ? 'pie' : 'bar';
      this.initChart();
    }
  }
};
</script>

<style scoped>.chart-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
}

button {
  margin-top: 10px;
}
</style>
