<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>学情测评管理</span>
        <el-button style="float: right;" type="primary" size="small" @click="handleAdd">新增测评</el-button>
      </div>
      
      <el-form :inline="true" :model="queryParams" class="search-form">
        <el-form-item label="学生ID">
          <el-input-number v-model="queryParams.studentId" placeholder="学生ID" clearable />
        </el-form-item>
        <el-form-item label="学期">
          <el-input v-model="queryParams.semester" placeholder="如：2024-2025-1" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="studentId" label="学生ID" width="100" />
        <el-table-column prop="semester" label="学期" />
        <el-table-column prop="evaluationType" label="测评类型" />
        <el-table-column prop="moralScore" label="德育分" />
        <el-table-column prop="intellectualScore" label="智育分" />
        <el-table-column prop="physicalScore" label="体育分" />
        <el-table-column prop="totalScore" label="总分" />
        <el-table-column prop="level" label="等级" />
        <el-table-column label="操作" width="150" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="text" style="color: #F56C6C;" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        style="margin-top: 20px; text-align: right;"
        :current-page="page.current"
        :page-size="page.size"
        :total="page.total"
        layout="total, sizes, prev, pager, next, jumper"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </el-card>

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px">
        <el-form-item label="学生ID" prop="studentId">
          <el-input-number v-model="form.studentId" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="学期">
          <el-input v-model="form.semester" placeholder="如：2024-2025-1" />
        </el-form-item>
        <el-form-item label="测评类型">
          <el-select v-model="form.evaluationType" style="width: 100%;">
            <el-option label="综合测评" value="综合测评" />
            <el-option label="学期测评" value="学期测评" />
            <el-option label="年度测评" value="年度测评" />
          </el-select>
        </el-form-item>
        <el-form-item label="德育分">
          <el-input-number v-model="form.moralScore" :min="0" :max="100" :precision="2" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="智育分">
          <el-input-number v-model="form.intellectualScore" :min="0" :max="100" :precision="2" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="体育分">
          <el-input-number v-model="form.physicalScore" :min="0" :max="100" :precision="2" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="总分">
          <el-input-number v-model="form.totalScore" :min="0" :max="100" :precision="2" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="等级">
          <el-select v-model="form.level" style="width: 100%;">
            <el-option label="优秀" value="优秀" />
            <el-option label="良好" value="良好" />
            <el-option label="中等" value="中等" />
            <el-option label="及格" value="及格" />
            <el-option label="不及格" value="不及格" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" v-model="form.remark" rows="3" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'Evaluation',
  data() {
    return {
      loading: false,
      tableData: [],
      queryParams: {
        studentId: null,
        semester: ''
      },
      page: {
        current: 1,
        size: 10,
        total: 0
      },
      dialogVisible: false,
      dialogTitle: '',
      isEdit: false,
      form: {},
      rules: {
        studentId: [{ required: true, message: '请输入学生ID', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      this.loading = true
      request.get('/student-work/evaluation/list', {
        params: {
          ...this.queryParams,
          current: this.page.current,
          size: this.page.size
        }
      }).then(res => {
        this.tableData = res.data.records
        this.page.total = res.data.total
      }).finally(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.page.current = 1
      this.loadData()
    },
    resetQuery() {
      this.queryParams = { studentId: null, semester: '' }
      this.handleQuery()
    },
    handleAdd() {
      this.dialogTitle = '新增测评'
      this.isEdit = false
      this.form = {}
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑测评'
      this.isEdit = true
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该测评记录？', '提示', { type: 'warning' }).then(() => {
        request.delete(`/student-work/evaluation/${row.id}`).then(() => {
          this.$message.success('删除成功')
          this.loadData()
        })
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const api = this.isEdit ? request.put : request.post
          api('/student-work/evaluation', this.form).then(() => {
            this.$message.success(this.isEdit ? '更新成功' : '添加成功')
            this.dialogVisible = false
            this.loadData()
          })
        }
      })
    },
    handleCurrentChange(val) {
      this.page.current = val
      this.loadData()
    },
    handleSizeChange(val) {
      this.page.size = val
      this.loadData()
    }
  }
}
</script>
