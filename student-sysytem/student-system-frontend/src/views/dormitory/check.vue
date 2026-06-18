<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>宿舍卫生检查</span>
        <el-button style="float: right;" type="primary" size="small" @click="handleAdd">新增检查</el-button>
      </div>
      
      <el-form :inline="true" :model="queryParams" class="search-form">
        <el-form-item label="楼栋号">
          <el-input v-model="queryParams.buildingNo" placeholder="楼栋号" clearable />
        </el-form-item>
        <el-form-item label="房间号">
          <el-input v-model="queryParams.roomNo" placeholder="房间号" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="buildingNo" label="楼栋号" />
        <el-table-column prop="roomNo" label="房间号" />
        <el-table-column prop="checkDate" label="检查日期" />
        <el-table-column prop="checkType" label="检查类型" />
        <el-table-column prop="score" label="评分" />
        <el-table-column prop="result" label="检查结果" />
        <el-table-column prop="checker" label="检查人" />
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
        <el-form-item label="宿舍ID">
          <el-input-number v-model="form.dormitoryId" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="楼栋号" prop="buildingNo">
          <el-input v-model="form.buildingNo" />
        </el-form-item>
        <el-form-item label="房间号" prop="roomNo">
          <el-input v-model="form.roomNo" />
        </el-form-item>
        <el-form-item label="检查日期">
          <el-date-picker v-model="form.checkDate" type="date" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="检查类型">
          <el-select v-model="form.checkType" style="width: 100%;">
            <el-option label="日常检查" value="日常检查" />
            <el-option label="周检查" value="周检查" />
            <el-option label="月检查" value="月检查" />
            <el-option label="专项检查" value="专项检查" />
          </el-select>
        </el-form-item>
        <el-form-item label="评分">
          <el-input-number v-model="form.score" :min="0" :max="100" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="检查结果">
          <el-select v-model="form.result" style="width: 100%;">
            <el-option label="优秀" value="优秀" />
            <el-option label="良好" value="良好" />
            <el-option label="合格" value="合格" />
            <el-option label="不合格" value="不合格" />
          </el-select>
        </el-form-item>
        <el-form-item label="检查人">
          <el-input v-model="form.checker" />
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
  name: 'DormitoryCheck',
  data() {
    return {
      loading: false,
      tableData: [],
      queryParams: {
        buildingNo: '',
        roomNo: ''
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
        roomNo: [{ required: true, message: '请输入房间号', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      this.loading = true
      request.get('/dormitory/check/list', {
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
      this.queryParams = { buildingNo: '', roomNo: '' }
      this.handleQuery()
    },
    handleAdd() {
      this.dialogTitle = '新增卫生检查'
      this.isEdit = false
      this.form = { checkType: '日常检查', result: '合格' }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑卫生检查'
      this.isEdit = true
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该检查记录？', '提示', { type: 'warning' }).then(() => {
        request.delete(`/dormitory/check/${row.id}`).then(() => {
          this.$message.success('删除成功')
          this.loadData()
        })
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const api = this.isEdit ? request.put : request.post
          api('/dormitory/check', this.form).then(() => {
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
