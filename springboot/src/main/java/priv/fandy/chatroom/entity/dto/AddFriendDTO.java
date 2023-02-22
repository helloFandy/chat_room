package priv.fandy.chatroom.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendDTO {

    //用户id
    private String username;

    private String friendUsername;
}
