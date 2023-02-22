package priv.fandy.chatroom.factory;

import priv.fandy.chatroom.entity.ChatMessages;

public abstract class AbstractChatMessagesFactory {

    abstract ChatMessages buildChatMessages(String socketMessage);

    public ChatMessages build(String content, Integer status, String fromUsername, String toUsername){
        ChatMessages chatMessages = ChatMessages.builder()
                .content(content)
                .status(status)
                .fromUsername(fromUsername)
                .toUsername(toUsername)
                .build();
        return chatMessages;
    }
}
