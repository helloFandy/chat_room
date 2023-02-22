package priv.fandy.chatroom.entity.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UpdateChatMessagesStatusDTO {

    /**
     * 发送方用户名
     */
    private String fromUsername;

    /**
     * 接收方姓名
     */
    private String toUsername;

    /**
     * 状态
     */
    private Integer status;
}

