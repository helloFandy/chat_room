package priv.fandy.chatroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("friend")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String friendUsername;

    private String username;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Integer isDeleted;

}
