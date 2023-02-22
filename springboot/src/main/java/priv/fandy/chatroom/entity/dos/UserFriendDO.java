package priv.fandy.chatroom.entity.dos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendDO {

    private Integer id;

    //备注
    private String remark;

    //朋友账号
    private String username;

    //朋友昵称
    private String nickName;

    //朋友年龄
    private Integer age;
}
