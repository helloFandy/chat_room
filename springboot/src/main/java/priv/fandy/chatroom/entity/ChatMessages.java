package priv.fandy.chatroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
@TableName("chat_messages")
public class ChatMessages extends Model<ChatMessages> {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    private Date createTime;
}

