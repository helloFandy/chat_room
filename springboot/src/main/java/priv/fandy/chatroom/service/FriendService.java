package priv.fandy.chatroom.service;

import priv.fandy.chatroom.common.Result;
import priv.fandy.chatroom.entity.vo.UserFriendVO;
import priv.fandy.chatroom.entity.dto.AddFriendDTO;
import priv.fandy.chatroom.entity.dto.FriendDTO;

import java.util.List;

public interface FriendService {

    List<UserFriendVO> selectFriendsByUsername(FriendDTO dto);

    Result<?> addFriend(AddFriendDTO dto);
}
