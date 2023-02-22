package priv.fandy.chatroom.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendVO {

    private Integer id;

    //朋友账号
    private String username;

    //备注
    private String remark;

    //朋友昵称
    private String nickName;

    //朋友年龄
    private Integer age;

    /**
     * 是否在线(默认不在线)
     */
    private Boolean online = false;

    /**
     * 未读消息数
     */
    private Integer unread;
}
