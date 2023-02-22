<template>
  <div style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add" v-if="this.user.username !== 'admin'">给管理员写邮件</el-button>
    </div>

    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="searchTitle" placeholder="请输入标题" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table
        v-loading="loading"
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column
          prop="id"
          label="ID"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="title"
          label="标题">
      </el-table-column>
      <el-table-column v-if="this.user.username !== 'admin'"
          prop="status"
          label="状态"
          :formatter="statusFormatter">
      </el-table-column>
      <el-table-column v-else
           prop="adminStatus"
           label="状态"
           :formatter="statusFormatter">
      </el-table-column>
      <el-table-column
          prop="createDate"
          label="时间">
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="mini" @click="details(scope.row)">查看内容</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="写邮件" v-model="dialogVisible" width="50%">
      <el-form :model="form" label-width="40px">
        <el-form-item label="标题">
          <el-input v-model="form.title" style="width: 50%"></el-input>
        </el-form-item>

        <div id="div1"></div>
        <!--        <el-form-item label="内容">-->
        <!--          <el-input v-model="form.price" style="width: 80%"></el-input>-->
        <!--        </el-form-item>-->
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
      </template>
    </el-dialog>

    <el-dialog title="邮件内容" v-model="vis" width="50%">
      <el-card>
        <template #header>
          <div class="card-header">
            <span>from: {{this.detail.fromUsername}}</span>
            <span class="button" text>to: {{this.detail.toUsername}}</span>
          </div>
        </template>
        <div v-html="detail.content" style="min-height: 250px"></div>
      </el-card>
    </el-dialog>

  </div>
</template>

<style>
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
</style>

<script>

import E from 'wangeditor'
import request from "@/utils/request";

let editor;

export default {
  name: 'Email',
  components: {},
  data() {
    return {
      loading: true,
      form: {},
      dialogVisible: false,
      searchTitle: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      vis: false,
      detail: {},
      messages: [],
      entity: {},
      user: {},
      dialogFormVisible: false,
    }
  },
  created() {
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    this.load()
  },
  methods: {
    details(row) {
      this.detail = row
      this.vis = true
      if (this.user.username === 'admin'){
        this.detail.adminStatus = 2
      }else{
        this.detail.status = 2
      }
      this.updateStatus(this.detail.id,this.detail.status,this.detail.adminStatus)
    },

    load() {
      this.loading = true
      let params = {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        title: this.searchTitle
      }
      if(this.user.username !== 'admin'){
        params.fromUsername = this.user.username
      }

      request.post("/email/findPage", params).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },

    add() {
      this.dialogVisible = true
      this.form = {}

      this.$nextTick(() => {
        // 关联弹窗里面的div，new一个 editor对象
        if (!editor) {
          editor = new E('#div1')

          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://' + window.server.filesUploadUrl + ':9090/files/editor/upload'
          editor.config.uploadFileName = "file"  // 设置上传参数名称
          editor.create()
        }

        editor.txt.html("")

      })

    },

    save() {
      this.form.content = editor.txt.html()  // 获取 编辑器里面的值，然后赋予到实体当中

     // 新增
      let param = {
        "content": this.form.content,
        "fromUsername": this.user.username,
        "toUsername": 'admin',
        "title": this.form.title
      }

      request.post("/email/addEmail", param).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "新增成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }

        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      })


    },

    updateStatus(id,status,adminStatus){
      let params = {
        id: id,
        status: status,
        adminStatus: adminStatus
      }
      request.post("/email/updateStatus", params).then(res => {
        console.dir(res)
      })
    },

    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.load()
    },

    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.load()
    },

    statusFormatter(row, column, cellValue){
      if(cellValue === 1){
        return '未读'
      }
      if(cellValue === 2){
        return '已读'
      }
      else{
        return undefined
      }
    }
  }
}
</script>
