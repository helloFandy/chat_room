package priv.fandy.chatroom.entity.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendQuery {

    //用户id
    private String username;

    //朋友账号
    private String friendUsername;
}
