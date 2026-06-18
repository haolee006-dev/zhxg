<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>学生通报管理</span>
        <el-button style="float: right;" type="primary" size="small" @click="handleAdd">新增通报</el-button>
      </div>
      
      <el-form :inline="true" :model="queryParams" class="search-form">
        <el-form-item label="类型">
          <el-select v-model="queryParams.type" placeholder="选择类型" clearable>
            <el-option label="通知" value="通知" />
            <el-option label="公告" value="公告" />
            <el-option label="通报" value="通报" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="type" label="类型" />
        <el-table-column prop="sender" label="发送人" />
        <el-table-column prop="receiverType" label="接收对象" />
        <el-table-column prop="isRead" label="是否已读">
          <template slot-scope="scope">
            <el-tag :type="scope.row.isRead ? 'success' : 'warning'">{{ scope.row.isRead ? '已读' : '未读' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="发送时间" />
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
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input type="textarea" v-model="form.content" rows="4" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.type" style="width: 100%;">
            <el-option label="通知" value="通知" />
            <el-option label="公告" value="公告" />
            <el-option label="通报" value="通报" />
          </el-select>
        </el-form-item>
        <el-form-item label="发送人">
          <el-input v-model="form.sender" />
        </el-form-item>
        <el-form-item label="接收对象类型">
          <el-select v-model="form.receiverType" style="width: 100%;">
            <el-option label="全体" value="全体" />
            <el-option label="学生" value="学生" />
            <el-option label="教师" value="教师" />
          </el-select>
        </el-form-item>
        <el-form-item label="接收对象ID">
          <el-input-number v-model="form.receiverId" style="width: 100%;" />
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
  name: 'Notification',
  data() {
    return {
      loading: false,
      tableData: [],
      queryParams: {
        type: ''
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
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
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
      request.get('/student-work/notification/list', {
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
      this.queryParams = { type: '' }
      this.handleQuery()
    },
    handleAdd() {
      this.dialogTitle = '新增通报'
      this.isEdit = false
      this.form = { type: '通知', receiverType: '全体' }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑通报'
      this.isEdit = true
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该通报？', '提示', { type: 'warning' }).then(() => {
        request.delete(`/student-work/notification/${row.id}`).then(() => {
          this.$message.success('删除成功')
          this.loadData()
        })
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const api = this.isEdit ? request.put : request.post
          api('/student-work/notification', this.form).then(() => {
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
