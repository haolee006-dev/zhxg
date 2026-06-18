<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>宿舍违纪管理</span>
        <el-button style="float: right;" type="primary" size="small" @click="handleAdd">新增违纪</el-button>
      </div>
      
      <el-form :inline="true" :model="queryParams" class="search-form">
        <el-form-item label="楼栋号">
          <el-input v-model="queryParams.buildingNo" placeholder="楼栋号" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="选择状态" clearable>
            <el-option label="待处理" value="待处理" />
            <el-option label="已处理" value="已处理" />
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
        <el-table-column prop="violationType" label="违纪类型" />
        <el-table-column prop="description" label="违纪描述" show-overflow-tooltip />
        <el-table-column prop="violationDate" label="违纪日期" />
        <el-table-column prop="punishment" label="处罚措施" />
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === '已处理' ? 'success' : 'warning'">{{ scope.row.status }}</el-tag>
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
        <el-form-item label="违纪类型">
          <el-select v-model="form.violationType" style="width: 100%;">
            <el-option label="晚归" value="晚归" />
            <el-option label="夜不归宿" value="夜不归宿" />
            <el-option label="使用违规电器" value="使用违规电器" />
            <el-option label="大声喧哗" value="大声喧哗" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="违纪描述" prop="description">
          <el-input type="textarea" v-model="form.description" rows="3" />
        </el-form-item>
        <el-form-item label="违纪日期">
          <el-date-picker v-model="form.violationDate" type="date" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="处罚措施">
          <el-input type="textarea" v-model="form.punishment" rows="3" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%;">
            <el-option label="待处理" value="待处理" />
            <el-option label="已处理" value="已处理" />
          </el-select>
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
  name: 'DormitoryViolation',
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
        description: [{ required: true, message: '请输入违纪描述', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      this.loading = true
      request.get('/dormitory/violation/list', {
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
      this.queryParams = { buildingNo: '', status: '' }
      this.handleQuery()
    },
    handleAdd() {
      this.dialogTitle = '新增违纪记录'
      this.isEdit = false
      this.form = { status: '待处理' }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑违纪记录'
      this.isEdit = true
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该违纪记录？', '提示', { type: 'warning' }).then(() => {
        request.delete(`/dormitory/violation/${row.id}`).then(() => {
          this.$message.success('删除成功')
          this.loadData()
        })
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const api = this.isEdit ? request.put : request.post
          api('/dormitory/violation', this.form).then(() => {
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
