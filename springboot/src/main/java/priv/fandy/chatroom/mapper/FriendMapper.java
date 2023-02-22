package priv.fandy.chatroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import priv.fandy.chatroom.entity.Friend;
import priv.fandy.chatroom.entity.dos.UserFriendDO;
import priv.fandy.chatroom.entity.query.FriendQuery;

import java.util.List;

public interface FriendMapper extends BaseMapper<Friend> {

    List<UserFriendDO> selectFriendsByUsername(FriendQuery query);

    List<Friend> selectFriends(FriendQuery query);
}
