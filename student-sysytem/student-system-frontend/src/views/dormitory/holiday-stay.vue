<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>假期留校管理</span>
        <el-button style="float: right;" type="primary" size="small" @click="handleAdd">新增申请</el-button>
      </div>
      
      <el-form :inline="true" :model="queryParams" class="search-form">
        <el-form-item label="学生ID">
          <el-input-number v-model="queryParams.studentId" placeholder="学生ID" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="选择状态" clearable>
            <el-option label="待审批" value="待审批" />
            <el-option label="已通过" value="已通过" />
            <el-option label="已拒绝" value="已拒绝" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="studentId" label="学生ID" width="100" />
        <el-table-column prop="holidayName" label="假期名称" />
        <el-table-column prop="startDate" label="开始日期" />
        <el-table-column prop="endDate" label="结束日期" />
        <el-table-column prop="reason" label="留校原因" show-overflow-tooltip />
        <el-table-column prop="contactPhone" label="联系电话" />
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
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
        <el-form-item label="假期名称" prop="holidayName">
          <el-input v-model="form.holidayName" placeholder="如：寒假、暑假" />
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker v-model="form.startDate" type="date" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker v-model="form.endDate" type="date" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="留校原因" prop="reason">
          <el-input type="textarea" v-model="form.reason" rows="3" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.contactPhone" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%;">
            <el-option label="待审批" value="待审批" />
            <el-option label="已通过" value="已通过" />
            <el-option label="已拒绝" value="已拒绝" />
          </el-select>
        </el-form-item>
        <el-form-item label="审批人">
          <el-input v-model="form.approver" />
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
  name: 'HolidayStay',
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
        holidayName: [{ required: true, message: '请输入假期名称', trigger: 'blur' }],
        reason: [{ required: true, message: '请输入留校原因', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      this.loading = true
      request.get('/dormitory/holiday-stay/list', {
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
    getStatusType(status) {
      const map = {
        '待审批': 'warning',
        '已通过': 'success',
        '已拒绝': 'danger'
      }
      return map[status] || 'info'
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
      this.dialogTitle = '新增留校申请'
      this.isEdit = false
      this.form = { status: '待审批' }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑留校申请'
      this.isEdit = true
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该申请？', '提示', { type: 'warning' }).then(() => {
        request.delete(`/dormitory/holiday-stay/${row.id}`).then(() => {
          this.$message.success('删除成功')
          this.loadData()
        })
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const api = this.isEdit ? request.put : request.post
          api('/dormitory/holiday-stay', this.form).then(() => {
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
