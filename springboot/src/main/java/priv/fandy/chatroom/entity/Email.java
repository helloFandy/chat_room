package priv.fandy.chatroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("email")
@Data
public class Email {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private String toUsername;

    private String fromUsername;

    private Integer status;

    private Integer adminStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}
