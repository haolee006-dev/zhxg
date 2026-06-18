<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>档案记录管理</span>
        <el-button style="float: right;" type="primary" size="small" @click="handleAdd">新增记录</el-button>
      </div>
      
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="studentId" label="学生ID" width="100" />
        <el-table-column prop="recordType" label="档案类型" />
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column prop="operator" label="操作人" width="120" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
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
        <el-form-item label="档案类型" prop="recordType">
          <el-select v-model="form.recordType" style="width: 100%;">
            <el-option label="学籍变动" value="学籍变动" />
            <el-option label="奖惩记录" value="奖惩记录" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input type="textarea" v-model="form.content" rows="4" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" />
        </el-form-item>
        <el-form-item label="操作人">
          <el-input v-model="form.operator" />
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
  name: 'StudentRecord',
  data() {
    return {
      loading: false,
      tableData: [],
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
        recordType: [{ required: true, message: '请选择档案类型', trigger: 'change' }],
        content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      this.loading = true
      request.get('/student-record/list', {
        params: {
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
    handleAdd() {
      this.dialogTitle = '新增档案记录'
      this.isEdit = false
      this.form = {}
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑档案记录'
      this.isEdit = true
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该记录？', '提示', { type: 'warning' }).then(() => {
        request.delete(`/student-record/${row.id}`).then(() => {
          this.$message.success('删除成功')
          this.loadData()
        })
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const api = this.isEdit ? request.put : request.post
          api('/student-record', this.form).then(() => {
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
