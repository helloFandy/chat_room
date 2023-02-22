package priv.fandy.chatroom.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class SelectChatMessagesVO{

    /**
     * 主键
     */
    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 发送方用户名
     */
    private String fromUsername;

    /**
     * 接收方姓名
     */
    private String toUsername;

    /**
     * 接收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createTime;
}

