<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>学生证办理</span>
        <el-button style="float: right;" type="primary" size="small" @click="handleAdd">新增办理</el-button>
      </div>
      
      <el-form :inline="true" :model="queryParams" class="search-form">
        <el-form-item label="学生ID">
          <el-input-number v-model="queryParams.studentId" placeholder="学生ID" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="选择状态" clearable>
            <el-option label="正常" value="正常" />
            <el-option label="挂失" value="挂失" />
            <el-option label="过期" value="过期" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="studentId" label="学生ID" width="100" />
        <el-table-column prop="cardType" label="证件类型" />
        <el-table-column prop="cardNo" label="证件号码" />
        <el-table-column prop="issueDate" label="发放日期" />
        <el-table-column prop="expireDate" label="有效期至" />
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === '正常' ? 'success' : 'danger'">{{ scope.row.status }}</el-tag>
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
        <el-form-item label="证件类型">
          <el-select v-model="form.cardType" style="width: 100%;">
            <el-option label="学生证" value="学生证" />
            <el-option label="校园卡" value="校园卡" />
            <el-option label="图书馆卡" value="图书馆卡" />
          </el-select>
        </el-form-item>
        <el-form-item label="证件号码">
          <el-input v-model="form.cardNo" />
        </el-form-item>
        <el-form-item label="发放日期">
          <el-date-picker v-model="form.issueDate" type="date" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="有效期至">
          <el-date-picker v-model="form.expireDate" type="date" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%;">
            <el-option label="正常" value="正常" />
            <el-option label="挂失" value="挂失" />
            <el-option label="过期" value="过期" />
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
  name: 'StudentCard',
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
      request.get('/student-work/card/list', {
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
      this.dialogTitle = '新增证件办理'
      this.isEdit = false
      this.form = { status: '正常' }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑证件信息'
      this.isEdit = true
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该记录？', '提示', { type: 'warning' }).then(() => {
        request.delete(`/student-work/card/${row.id}`).then(() => {
          this.$message.success('删除成功')
          this.loadData()
        })
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const api = this.isEdit ? request.put : request.post
          api('/student-work/card', this.form).then(() => {
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
