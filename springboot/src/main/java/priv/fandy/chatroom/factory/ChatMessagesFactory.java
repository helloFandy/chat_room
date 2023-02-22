package priv.fandy.chatroom.factory;

import cn.hutool.json.JSONObject;
import priv.fandy.chatroom.component.WebSocketServer;
import priv.fandy.chatroom.entity.ChatMessages;
import org.springframework.stereotype.Component;

@Component
public class ChatMessagesFactory extends AbstractChatMessagesFactory{

    @Override
    public ChatMessages buildChatMessages(String socketMessages) {
        JSONObject messageObj = new JSONObject(socketMessages);
        String fromUsername = messageObj.getStr("from");
        String content = messageObj.getStr("text");
        JSONObject toUserObj = messageObj.getJSONObject("to");
        String toUsername = toUserObj.getStr("username");
        Integer status = 2;

        if (WebSocketServer.sessionMap.containsKey(toUsername)){
            status = 1;
        }

        return build(content,status,fromUsername,toUsername);
    }
}
