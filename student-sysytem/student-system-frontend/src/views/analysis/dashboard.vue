<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <div slot="header">综合数据报表</div>
          <el-table :data="statsData" border>
            <el-table-column prop="category" label="统计类别" />
            <el-table-column prop="item" label="项目" />
            <el-table-column prop="count" label="数量" />
            <el-table-column prop="percentage" label="占比" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <div slot="header">学院分布</div>
          <div ref="collegeChart" style="height: 300px;"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header">年级分布</div>
          <div ref="gradeChart" style="height: 300px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import request from '@/utils/request'
import * as echarts from 'echarts'

export default {
  name: 'AnalysisDashboard',
  data() {
    return {
      statsData: [],
      collegeData: [],
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
        this.gradeData = res.data.gradeStats || []
        this.processStatsData()
        this.initCharts()
      })
    },
    processStatsData() {
      const data = []
      let total = 0
      this.collegeData.forEach(item => {
        total += item.value
      })
      this.collegeData.forEach(item => {
        data.push({
          category: '学院分布',
          item: item.name,
          count: item.value,
          percentage: ((item.value / total) * 100).toFixed(2) + '%'
        })
      })
      this.statsData = data
    },
    initCharts() {
      this.initCollegeChart()
      this.initGradeChart()
    },
    initCollegeChart() {
      const chart = echarts.init(this.$refs.collegeChart)
      const option = {
        tooltip: { trigger: 'item' },
        series: [{
          type: 'pie',
          radius: '60%',
          data: this.collegeData.map(item => ({
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
          type: 'bar',
          itemStyle: { color: '#409EFF' }
        }]
      }
      chart.setOption(option)
    }
  }
}
</script>
