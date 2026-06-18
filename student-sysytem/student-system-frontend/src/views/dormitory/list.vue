<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>宿舍信息管理</span>
        <el-button style="float: right;" type="primary" size="small" @click="handleAdd">新增宿舍</el-button>
      </div>
      
      <el-form :inline="true" :model="queryParams" class="search-form">
        <el-form-item label="楼栋号">
          <el-input v-model="queryParams.buildingNo" placeholder="楼栋号" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="选择状态" clearable>
            <el-option label="正常" value="正常" />
            <el-option label="维修中" value="维修中" />
            <el-option label="已满" value="已满" />
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
        <el-table-column prop="floor" label="楼层" />
        <el-table-column prop="capacity" label="容量" />
        <el-table-column prop="currentCount" label="当前人数" />
        <el-table-column prop="roomType" label="房间类型" />
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="buildingType" label="楼栋类型" />
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
        <el-form-item label="楼栋号" prop="buildingNo">
          <el-input v-model="form.buildingNo" />
        </el-form-item>
        <el-form-item label="房间号" prop="roomNo">
          <el-input v-model="form.roomNo" />
        </el-form-item>
        <el-form-item label="楼层">
          <el-input-number v-model="form.floor" :min="1" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="容量">
          <el-input-number v-model="form.capacity" :min="1" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="当前人数">
          <el-input-number v-model="form.currentCount" :min="0" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="房间类型">
          <el-select v-model="form.roomType" style="width: 100%;">
            <el-option label="四人间" value="四人间" />
            <el-option label="六人间" value="六人间" />
            <el-option label="八人间" value="八人间" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%;">
            <el-option label="正常" value="正常" />
            <el-option label="维修中" value="维修中" />
            <el-option label="已满" value="已满" />
          </el-select>
        </el-form-item>
        <el-form-item label="楼栋类型">
          <el-select v-model="form.buildingType" style="width: 100%;">
            <el-option label="男生宿舍" value="男生宿舍" />
            <el-option label="女生宿舍" value="女生宿舍" />
            <el-option label="混合宿舍" value="混合宿舍" />
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
  name: 'DormitoryList',
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
      request.get('/dormitory/list', {
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
        '正常': 'success',
        '维修中': 'warning',
        '已满': 'info'
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
      this.dialogTitle = '新增宿舍'
      this.isEdit = false
      this.form = { status: '正常', capacity: 4, currentCount: 0 }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑宿舍'
      this.isEdit = true
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该宿舍？', '提示', { type: 'warning' }).then(() => {
        request.delete(`/dormitory/${row.id}`).then(() => {
          this.$message.success('删除成功')
          this.loadData()
        })
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const api = this.isEdit ? request.put : request.post
          api('/dormitory', this.form).then(() => {
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
