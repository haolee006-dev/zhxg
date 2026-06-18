<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>宿舍报修管理</span>
        <el-button style="float: right;" type="primary" size="small" @click="handleAdd">新增报修</el-button>
      </div>
      
      <el-form :inline="true" :model="queryParams" class="search-form">
        <el-form-item label="楼栋号">
          <el-input v-model="queryParams.buildingNo" placeholder="楼栋号" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="选择状态" clearable>
            <el-option label="待处理" value="待处理" />
            <el-option label="处理中" value="处理中" />
            <el-option label="已完成" value="已完成" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="buildingNo" label="楼栋号" />
        <el-table-column prop="roomNo" label="房间号" />
        <el-table-column prop="repairType" label="报修类型" />
        <el-table-column prop="description" label="问题描述" show-overflow-tooltip />
        <el-table-column prop="reportDate" label="报修日期" />
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="handler" label="处理人" />
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
        <el-form-item label="学生ID">
          <el-input-number v-model="form.studentId" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="宿舍ID">
          <el-input-number v-model="form.dormitoryId" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="楼栋号" prop="buildingNo">
          <el-input v-model="form.buildingNo" />
        </el-form-item>
        <el-form-item label="房间号" prop="roomNo">
          <el-input v-model="form.roomNo" />
        </el-form-item>
        <el-form-item label="报修类型">
          <el-select v-model="form.repairType" style="width: 100%;">
            <el-option label="水电" value="水电" />
            <el-option label="门窗" value="门窗" />
            <el-option label="家具" value="家具" />
            <el-option label="网络" value="网络" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="问题描述" prop="description">
          <el-input type="textarea" v-model="form.description" rows="3" />
        </el-form-item>
        <el-form-item label="报修日期">
          <el-date-picker v-model="form.reportDate" type="date" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%;">
            <el-option label="待处理" value="待处理" />
            <el-option label="处理中" value="处理中" />
            <el-option label="已完成" value="已完成" />
          </el-select>
        </el-form-item>
        <el-form-item label="完成日期">
          <el-date-picker v-model="form.completeDate" type="date" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="处理人">
          <el-input v-model="form.handler" />
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
  name: 'DormitoryRepair',
  data() {
    return {
      loading: false,
      tableData: [],
      queryParams: {
        buildingNo: '',
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
        buildingNo: [{ required: true, message: '请输入楼栋号', trigger: 'blur' }],
        roomNo: [{ required: true, message: '请输入房间号', trigger: 'blur' }],
        description: [{ required: true, message: '请输入问题描述', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      this.loading = true
      request.get('/dormitory/repair/list', {
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
        '待处理': 'warning',
        '处理中': 'primary',
        '已完成': 'success'
      }
      return map[status] || 'info'
    },
    handleQuery() {
      this.page.current = 1
      this.loadData()
    },
    resetQuery() {
      this.queryParams = { buildingNo: '', status: '' }
      this.handleQuery()
    },
    handleAdd() {
      this.dialogTitle = '新增报修'
      this.isEdit = false
      this.form = { status: '待处理' }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑报修'
      this.isEdit = true
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该报修记录？', '提示', { type: 'warning' }).then(() => {
        request.delete(`/dormitory/repair/${row.id}`).then(() => {
          this.$message.success('删除成功')
          this.loadData()
        })
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const api = this.isEdit ? request.put : request.post
          api('/dormitory/repair', this.form).then(() => {
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
