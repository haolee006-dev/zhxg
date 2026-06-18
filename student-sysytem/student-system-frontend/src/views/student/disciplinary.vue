<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>违纪处分管理</span>
        <el-button style="float: right;" type="primary" size="small" @click="handleAdd">新增处分</el-button>
      </div>
      
      <el-form :inline="true" :model="queryParams" class="search-form">
        <el-form-item label="学生ID">
          <el-input-number v-model="queryParams.studentId" placeholder="学生ID" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="选择状态" clearable>
            <el-option label="生效中" value="生效中" />
            <el-option label="已撤销" value="已撤销" />
            <el-option label="待审批" value="待审批" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="studentId" label="学生ID" width="100" />
        <el-table-column prop="type" label="处分类型" />
        <el-table-column prop="reason" label="处分原因" show-overflow-tooltip />
        <el-table-column prop="level" label="处分等级" />
        <el-table-column prop="actionDate" label="处分日期" />
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === '生效中' ? 'danger' : 'info'">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
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
        <el-form-item label="处分类型" prop="type">
          <el-select v-model="form.type" style="width: 100%;">
            <el-option label="警告" value="警告" />
            <el-option label="严重警告" value="严重警告" />
            <el-option label="记过" value="记过" />
            <el-option label="留校察看" value="留校察看" />
            <el-option label="开除学籍" value="开除学籍" />
          </el-select>
        </el-form-item>
        <el-form-item label="处分原因" prop="reason">
          <el-input type="textarea" v-model="form.reason" rows="3" />
        </el-form-item>
        <el-form-item label="处分等级" prop="level">
          <el-select v-model="form.level" style="width: 100%;">
            <el-option label="一级" value="一级" />
            <el-option label="二级" value="二级" />
            <el-option label="三级" value="三级" />
          </el-select>
        </el-form-item>
        <el-form-item label="处分日期">
          <el-date-picker v-model="form.actionDate" type="date" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%;">
            <el-option label="生效中" value="生效中" />
            <el-option label="已撤销" value="已撤销" />
            <el-option label="待审批" value="待审批" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" />
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
  name: 'Disciplinary',
  data() {
    return {
      loading: false,
      tableData: [],
      queryParams: {
        studentId: null,
        status: ''
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
        studentId: [{ required: true, message: '请输入学生ID', trigger: 'blur' }],
        type: [{ required: true, message: '请选择处分类型', trigger: 'change' }],
        reason: [{ required: true, message: '请输入处分原因', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      this.loading = true
      request.get('/student-work/disciplinary/list', {
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
      this.queryParams = { studentId: null, status: '' }
      this.handleQuery()
    },
    handleAdd() {
      this.dialogTitle = '新增处分'
      this.isEdit = false
      this.form = { status: '生效中' }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑处分'
      this.isEdit = true
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该处分记录？', '提示', { type: 'warning' }).then(() => {
        request.delete(`/student-work/disciplinary/${row.id}`).then(() => {
          this.$message.success('删除成功')
          this.loadData()
        })
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const api = this.isEdit ? request.put : request.post
          api('/student-work/disciplinary', this.form).then(() => {
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
