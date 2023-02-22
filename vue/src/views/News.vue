<template>
  <div style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
    </div>

    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
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
      <el-table-column
          prop="author"
          label="作者">
      </el-table-column>
      <el-table-column
          prop="time"
          label="时间">
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="mini" @click="details(scope.row)">详情</el-button>
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
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

    <el-dialog title="提示" v-model="dialogVisible" width="50%">
      <el-form :model="form" label-width="120px">
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

    <el-dialog title="详情" v-model="vis" width="50%">
      <el-card>
        <div v-html="detail.content" style="min-height: 100px"></div>
      </el-card>
      <el-card>
        <div style="padding: 20px; color: #888">
          <div>
            <el-input type="textarea" :rows="3" v-model="entity.content"></el-input>
            <div style="text-align: right; padding: 10px"><el-button type="primary" @click="saveMessage">留言</el-button></div>
          </div>
        </div>

        <div style="display: flex; padding: 20px" v-for="item in messages">
          <div style="text-align: center; flex: 1">
            <el-image :src="item.avatar" style="width: 60px; height: 60px; border-radius: 50%"></el-image>
          </div>
          <div style="padding: 0 10px; flex: 5">
            <div><b style="font-size: 14px">{{ item.username }}</b></div>
            <div style="padding: 10px 0; color: #888">
              {{ item.content }}
              <el-button type="text" size="mini" @click="delMessage(item.id)" v-if="item.username === user.username">删除</el-button>
            </div>
            <div style="background-color: #eee; padding: 10px" v-if="item.parentMessage">{{ item.username }}：{{ item.parentMessage.content }}</div>
            <div style="color: #888; font-size: 12px">
              <span>{{ item.time  }}</span>
              <el-button type="text" style="margin-left: 20px" @click="reReply(item.id)">回复</el-button>
            </div>
          </div>
        </div>

        <el-dialog title="回复信息" v-model="dialogFormVisible" width="30%">
          <el-form :model="entity" label-width="80px">
            <el-form-item label="内容">
              <el-input v-model="entity.reply" autocomplete="off" type="textarea" :rows="3"></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="reply">确 定</el-button>
          </template>
        </el-dialog>
      </el-card>
    </el-dialog>

  </div>
</template>

<script>

import E from 'wangeditor'
import request from "@/utils/request";

let editor;

export default {
  name: 'News',
  components: {},
  data() {
    return {
      loading: true,
      form: {},
      dialogVisible: false,
      search: '',
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
    this.load()
  },
  methods: {
    details(row) {
      this.detail = row
      this.vis = true
      this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
      this.loadMessage()
    },

    filesUploadSuccess(res) {
      console.log(res)
      this.form.cover = res.data
    },

    load() {
      this.loading = true
      request.get("/news", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
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

      if (this.form.id) {  // 更新
        request.put("/news", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
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
      } else {  // 新增
        let userStr = sessionStorage.getItem("user") || "{}"
        let user = JSON.parse(userStr)
        this.form.author = user.nickName

        request.post("/news", this.form).then(res => {
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
      }

    },

    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true

      this.$nextTick(() => {
        // 关联弹窗里面的div，new一个 editor对象
        // 关联弹窗里面的div，new一个 editor对象
        if (!editor) {
          editor = new E('#div1')

          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://localhost:9090/files/editor/upload'
          editor.config.uploadFileName = "file"  // 设置上传参数名称
          editor.create()
        }

        editor.txt.html(row.content)
      })
    },

    handleDelete(id) {
      console.log(id)
      request.delete("/news/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()  // 删除之后重新加载表格的数据
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

    loadMessage() {
      let _this = this
      // 如果是留言的话，就写死=0
      // 如果是 评论，则需要设置 当前被评论的模块的id作为foreignId
      _this.messages = []
      let foreignId = this.detail.id
      request.get("/message/foreign/" + foreignId).then(res => {
        _this.messages = res.data;
      })

    },

    saveMessage() {
      if (!this.user.username) {
        this.$message({
          message: "请登录",
          type: "warning"
        });
        return;
      }
      if (!this.entity.content) {
        this.$message({
          message: "请填写内容",
          type: "warning"
        });
        return;
      }
      this.entity.foreignId = this.detail.id
      // 如果是评论的话，在 save的时候要注意设置 当前模块的id为 foreignId。也就是  entity.foreignId = 模块id
      request.post("/message", this.entity).then(res => {
        if (res.code === '0') {
          this.$message({
            message: "评论成功",
            type: "success"
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
        this.entity = {}
        this.loadMessage();
        this.dialogFormVisible = false;
      })
    },

    delMessage(id) {
      request.delete("/message/" + id).then(res => {
        console.dir(res)
        this.$message({
          message: "删除成功",
          type: "success"
        });
        this.loadMessage()
      })
    },

    reReply(id) {
      this.dialogFormVisible = true;
      this.entity.parentId = id;
    },

    reply() {
      this.entity.content = this.entity.reply;
      this.saveMessage();
    },

    cancel() {
      this.dialogFormVisible = false;
      this.entity = {};
    },
  }
}
</script>
