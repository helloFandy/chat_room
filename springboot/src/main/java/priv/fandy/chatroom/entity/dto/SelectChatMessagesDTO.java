package priv.fandy.chatroom.entity.dto;

import lombok.Data;


@Data
public class SelectChatMessagesDTO{

    /**
     * 发送方用户名
     */
    private String fromUsername;

    /**
     * 接收方姓名
     */
    private String toUsername;

}

