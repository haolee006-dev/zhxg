<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <div slot="header">学院分布统计</div>
          <div ref="collegeChart" style="height: 400px;"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header">性别比例统计</div>
          <div ref="genderChart" style="height: 400px;"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <div slot="header">年级分布统计</div>
          <div ref="gradeChart" style="height: 400px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import request from '@/utils/request'
import * as echarts from 'echarts'

export default {
  name: 'StudentAnalysis',
  data() {
    return {
      collegeData: [],
      genderData: [],
      gradeData: []
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    loadData() {
      request.get('/analysis/dashboard').then(res => {
        this.collegeData = res.data.collegeStats || []
        this.genderData = res.data.genderStats || []
        this.gradeData = res.data.gradeStats || []
        this.initCharts()
      })
    },
    initCharts() {
      this.initCollegeChart()
      this.initGenderChart()
      this.initGradeChart()
    },
    initCollegeChart() {
      const chart = echarts.init(this.$refs.collegeChart)
      const option = {
        tooltip: { trigger: 'axis' },
        xAxis: {
          type: 'category',
          data: this.collegeData.map(item => item.name)
        },
        yAxis: { type: 'value' },
        series: [{
          data: this.collegeData.map(item => item.value),
          type: 'bar',
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#83bff6' },
              { offset: 0.5, color: '#188df0' },
              { offset: 1, color: '#188df0' }
            ])
          }
        }]
      }
      chart.setOption(option)
    },
    initGenderChart() {
      const chart = echarts.init(this.$refs.genderChart)
      const option = {
        tooltip: { trigger: 'item' },
        legend: { top: '5%', left: 'center' },
        series: [{
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: { show: false, position: 'center' },
          emphasis: {
            label: { show: true, fontSize: 20, fontWeight: 'bold' }
          },
          data: this.genderData.map(item => ({
            name: item.name,
            value: item.value
          }))
        }]
      }
      chart.setOption(option)
    },
    initGradeChart() {
      const chart = echarts.init(this.$refs.gradeChart)
      const option = {
        tooltip: { trigger: 'axis' },
        xAxis: {
          type: 'category',
          data: this.gradeData.map(item => item.name + '级')
        },
        yAxis: { type: 'value' },
        series: [{
          data: this.gradeData.map(item => item.value),
          type: 'line',
          smooth: true,
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: 'rgba(128, 255, 165, 0.5)' },
              { offset: 1, color: 'rgba(1, 191, 236, 0.1)' }
            ])
          },
          itemStyle: { color: '#67C23A' }
        }]
      }
      chart.setOption(option)
    }
  }
}
</script>
