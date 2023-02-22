<template>
  <div style="padding: 10px; margin-bottom: 50px">
    <el-row>
      <el-col :span="4">
        <el-card style="width: 300px; height: 500px; color: #333">
          <div style="padding-bottom: 10px; border-bottom: 1px solid #ccc">
           <span>好友列表</span>
           <span style="font-size: 12px" >（点击聊天气泡开始聊天）</span>
           <i class="el-icon-plus" @click="addFriendDialogFormVisible = true"></i>
         </div>
          <div style="padding: 10px 0; display: flex; align-items: center" v-for="friend in friendsSort" :key="friendsSort.username">

            <!--好友信息popover框-->
            <el-popover
                :width="300"
                popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
                placement="right"
                trigger="hover"
            >
              <template #reference>
                <el-avatar>User</el-avatar>
              </template>
              <template #default>
                <div
                    class="demo-rich-conent"
                    style="display: flex; gap: 16px; flex-direction: column"
                >
                  <p class="demo-rich-content__desc" style="margin: 0">
                    昵称：{{friend.nickName}}
                  </p>
                  <p class="demo-rich-content__desc" style="margin: 0">
                    年龄：{{friend.age}}
                  </p>
                </div>
              </template>
            </el-popover>

            <span v-if="friend.online" style="color:#409EFF;margin-left: 10px;">{{ friend.username }}</span>
            <span style="margin-left: 10px;margin-left: 10px;" v-else>{{ friend.username }}</span>
            <i class="el-icon-chat-dot-round" style="margin-left: 10px; font-size: 16px; cursor: pointer" @click="openChatBox(friend)">{{friend.unread === 0?'':friend.unread}}</i>
            <span style="font-size: 12px;color: limegreen; margin-left: 5px" v-if="friend.username === chatUser.username">chatting...</span>
          </div>
        </el-card>
      </el-col>

      <el-col :span="16" style="margin-left: 100px" v-if="showChatBox">
        <div style="width: 800px; margin: 0 auto; background-color: white;
                    border-radius: 5px; box-shadow: 0 0 10px #ccc">
          <div style="text-align: center; line-height: 50px;">
            {{ chatUser.username }}
          </div>
          <div id="dialogue_box" style="height: 350px; overflow:auto; border-top: 1px solid #ccc" v-html="content"></div>
          <div style="height: 200px">
            <textarea v-model="text" style="height: 160px; width: 100%; padding: 20px; border: none; border-top: 1px solid #ccc;
             border-bottom: 1px solid #ccc; outline: none"></textarea>
            <div style="text-align: right; padding-right: 10px">
              <el-button type="primary" size="mini" @click="send">发送</el-button>
              <el-button type="primary" size="mini" @click="downloadMsg">下载聊天记录</el-button>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!--  添加好友会话框  -->
    <el-dialog title="添加好友" v-model="addFriendDialogFormVisible" width="40%">
      <el-form :model="addFriendForm" label-width="100px">
          <el-form-item label="请输入账号" style="width: 80%">
            <el-input v-model="addFriendForm.friendUsername" style="width: 70%"></el-input>
          </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="addFriendDialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="addFriend">确 定</el-button>
          </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>

import request from "@/utils/request";
import {ElMessage} from "element-plus";
import moment from "moment";
let socket;

export default {
  name: "Im",

  data() {
    return {
      circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      user: {},
      isCollapse: false,
      users: [],
      chatUser: '',
      text: "",
      messages: [],
      content: '',
      friends: [],
      friend: {},
      addFriendDialogFormVisible: false,
      addFriendForm: {},
      formLabelWidth: '120px',
      showChatBox: false,
    }
  },

  computed:{
    friendsSort:function(){
      return this.friends.sort((a,b)=>{
        if (a.online && b.online){
          if(a.username > b.username){
            return -1
          }else {
            return 1
          }
        }else if (a.online){
          return -1
        }else if (b.online){
          return 1
        }else{
          if(a.username > b.username){
            return -1
          }else {
            return 1
          }
        }
      })
    },
  },

  created() {
    this.getFriends()
    this.init()
  },

  methods: {
    send() {
      if (!this.chatUser) {
        this.$message({type: 'warning', message: "请选择聊天对象"})
        return;
      }
      if (!this.text) {
        this.$message({type: 'warning', message: "请输入内容"})
      } else {
        if (typeof (WebSocket) == "undefined") {
          console.log("您的浏览器不支持WebSocket");
        } else {
          console.log("您的浏览器支持WebSocket");

          //消息入库
          this.addMessages()

          //在线才往socket发消息
          if(this.users.find(user => user.username === this.chatUser.username)){
            // 组装待发送的消息 json
            // {"from": "zhang", "to": "admin", "text": "聊天文本"}
            let message = {from: this.user.username, to: this.chatUser.username, text: this.text}
            socket.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
          }

          this.messages.push({user: this.user.username, text: this.text})
          // 构建消息内容，本人消息
          let date  =  moment(new Date().getTime()).format('YYYY-MM-DD HH:mm')
          this.createContent(null, this.user.username, this.text,date)
          this.text = '';
        }
      }
    },

    createContent(remoteUser, nowUser, text, createTime) {  // 这个方法是用来将 json的聊天消息数据转换成 html的。
      let html
      // 当前用户消息
      if (nowUser) { // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
        html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-24\" style=\"text-align: center;\">\n" +
            "    <div style=\"font-size: 5px; display: inline\">" + createTime + "</div>\n" +
            "  </div>\n" +
            "  </div>\n" +
            "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-22\" style=\"text-align: right; padding-right: 10px\">\n" +
            "    <div class=\"tip left\">" + text + "</div>\n" +
            "  </div>\n" +
            "  <div class=\"el-col el-col-2\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
            "  </span>\n" +
            "  </div>\n" +
            "</div>";
      } else if (remoteUser) {   // remoteUser表示远程用户聊天消息，蓝色的气泡
        html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-24\" style=\"text-align: center;\">\n" +
            "    <div style=\"font-size: 5px; display: inline\">" + createTime + "</div>\n" +
            "  </div>\n" +
            "</div>"+
            "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
            "  </span>\n" +
            "  </div>\n" +
            "  <div class=\"el-col el-col-22\" style=\"text-align: left; padding-left: 10px\">\n" +
            "    <div class=\"tip right\">" + text + "</div>\n" +
            "  </div>\n" +
            "</div>";
      }
      this.content += html;

      //滚动条下拉
      this.$nextTick(() => {
        setTimeout(() => {
          let textarea = document.getElementById('dialogue_box');
          textarea.scrollTop = textarea.scrollHeight;
        }, 13)
      })

    },

    init() {
      this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
      let username = this.user.username;
      let _this = this;
      if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
      } else {
        console.log("您的浏览器支持WebSocket");
        let socketUrl = "ws://localhost:9090/imserver/" + username;
        if (socket != null) {
          socket.close();
          socket = null;
        }
        // 开启一个websocket服务
        socket = new WebSocket(socketUrl);
        //打开事件
        socket.onopen = function () {
          console.log("websocket已打开");
        };
        //  浏览器端收消息，获得从服务端发送过来的文本消息
        socket.onmessage = function (msg) {
          console.log("收到数据====" + msg.data)
          let data = JSON.parse(msg.data)  // 对收到的json数据进行解析， 类似这样的： {"users": [{"username": "zhang"},{ "username": "admin"}]}
          if (data.users) {  // 获取在线人员信息
            _this.users = data.users.filter(user => user.username !== username)  // 获取当前连接的所有用户信息，并且排除自身，自己不会出现在自己的聊天列表里
            //设置好友在线状态
            for (let i=0; i<_this.users.length; i++) {
              for (let j=0; j<_this.friends.length; j++){
                if (_this.users[i].username === _this.friends[j].username){
                    _this.friends[j].online = true;
                }else {
                  _this.friends[j].online = false;
                }
              }
            }

          } else {
            // 如果服务器端发送过来的json数据 不包含 users 这个key，那么发送过来的就是聊天文本json数据
            //  // {"from": "zhang", "text": "hello"}
            if (data.from === _this.chatUser.username) {
              _this.messages.push(data)
              let date  =  moment(new Date().getTime()).format('YYYY-MM-DD HH:mm')
              _this.createContent(data.from, null, data.text,date)

              //更新消息状态
              _this.updateMessagesStatus(data.from,_this.user.username,1)
            }else{
              //将好友列表的消息数+1
              let user = _this.friends.find(friend => friend.username === data.from)
              user.unread += 1
            }
          }
        };
        //关闭事件
        socket.onclose = function () {
          console.log("websocket已关闭");
        };
        //发生了错误事件
        socket.onerror = function () {
          console.log("websocket发生了错误");
        }
      }
    },

    getFriends(){
      this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
      let _this = this;
      let frienddto = {
        "username": _this.user.username
      }
      request.post("/friend/selectFriendsByUsername",frienddto).then(res => {
        if(res.code === '0') {
          _this.friends = res.data
        }else{
          ElMessage.error("获取好友列表失败")
        }
      })
    },

    async addFriend(){
      let _this = this
      let param = {
        "username": _this.user.username,
        "friendUsername": _this.addFriendForm.friendUsername
      }
      await request.post("/friend/addFriend",param).then(res => {
        if(res.code === '0') {
          let frienddto = {
            "username": _this.user.username
          }
          request.post("/friend/selectFriendsByUsername",frienddto).then(res => {
            if(res.code === '0') {
              let newFriends = res.data
              for (let i = 0; i < newFriends.length; i++) {
                let existsFriend = false
                for (let j = 0; j < _this.friends.length; j++) {
                  if(_this.friends[j].username === newFriends[i].username){
                    existsFriend = true;
                  }
                }
                if (!existsFriend){
                  _this.friends.push(newFriends[i])
                }
              }
            }else{
              ElMessage.error("获取好友列表失败")
              return
            }
          })
          ElMessage.success("添加好友成功")
          _this.addFriendDialogFormVisible = false
        }else{
          ElMessage.error(res.msg)
        }
      })
    },

    openChatBox(friend){
      this.chatUser = friend
      this.showChatBox = true
      this.chatUser.unread = ''
      this.getMessages()
    },

    //获取消息
    getMessages(){
      let _this = this
      _this.content = ''
      let param = {
        "fromUsername": _this.user.username,
        "toUsername": _this.chatUser.username
      }
      request.post("/chatMessages/selectChatMessages",param).then(res => {
        if(res.code === '0') {
          let datas = res.data
          for (const data of datas) {
            if(data.fromUsername === _this.user.username){
              _this.createContent(null,data.fromUsername,data.content,data.createTime)
            }else{
              _this.createContent(data.fromUsername,null,data.content,data.createTime)
            }

          }
        }else{
          ElMessage.error("获取消息失败，请稍后重试")
          return
        }
      })
    },

    //新增消息
    addMessages(){
      let _this = this
      let param = {
        "fromUsername": _this.user.username,
        "toUsername": _this.chatUser.username,
        "content": _this.text,
        "status": 2
      }
      request.post("/chatMessages/addChatMessages",param).then(res => {
        if(res.code === '0') {
          console.dir("add message success")
        }else{
          console.dir("add message fail")
        }
      })
    },

    //更新消息的状态
    updateMessagesStatus(fromUsername,toUsername,status){
      let param = {
        "fromUsername": fromUsername,
        "toUsername": toUsername,
        "status": status
      }
      request.post("/chatMessages/updateChatMessagesStatus",param).then(res => {
        if(res.code === '0') {
          console.dir("update message status success")
        }else{
          console.dir("update message status fail")
        }
      })
    },

    //下载聊天记录
    downloadMsg(){
      let param = {
        "fromUsername": this.user.username,
        "toUsername": this.chatUser.username,
      }
      request.post("/chatMessages/downloadChatMessages",param).then(res => {
        console.dir(res)
      })
    },
  },

}

</script>

<style>
.tip {
  color: white;
  text-align: center;
  border-radius: 10px;
  font-family: sans-serif;
  padding: 10px;
  width:auto;
  display:inline-block !important;
  display:inline;
}

.right {
  background-color: deepskyblue;
}
.left {
  background-color: forestgreen;
}
</style>
