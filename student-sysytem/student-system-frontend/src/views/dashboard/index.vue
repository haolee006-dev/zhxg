<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="data-card">
          <div class="card-icon" style="background: #409EFF;">
            <i class="el-icon-user"></i>
          </div>
          <div class="card-info">
            <div class="card-title">学生总数</div>
            <div class="card-number">{{ stats.studentCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="data-card">
          <div class="card-icon" style="background: #67C23A;">
            <i class="el-icon-s-home"></i>
          </div>
          <div class="card-info">
            <div class="card-title">宿舍数量</div>
            <div class="card-number">{{ stats.dormitoryCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="data-card">
          <div class="card-icon" style="background: #E6A23C;">
            <i class="el-icon-s-order"></i>
          </div>
          <div class="card-info">
            <div class="card-title">今日报到</div>
            <div class="card-number">{{ stats.todayCheckin || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="data-card">
          <div class="card-icon" style="background: #F56C6C;">
            <i class="el-icon-s-marketing"></i>
          </div>
          <div class="card-info">
            <div class="card-title">待处理事项</div>
            <div class="card-number">{{ stats.pendingCount || 0 }}</div>
          </div>
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
          <div slot="header">性别比例</div>
          <div ref="genderChart" style="height: 300px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
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
  name: 'Dashboard',
  data() {
    return {
      stats: {},
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
          itemStyle: { color: '#409EFF' }
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
          itemStyle: { color: '#67C23A' }
        }]
      }
      chart.setOption(option)
    }
  }
}
</script>

<style scoped>
.data-card {
  display: flex;
  align-items: center;
  padding: 20px;
}

.card-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 24px;
  margin-right: 15px;
}

.card-info {
  flex: 1;
}

.card-title {
  color: #909399;
  font-size: 14px;
  margin-bottom: 8px;
}

.card-number {
  color: #303133;
  font-size: 24px;
  font-weight: bold;
}
</style>
