package priv.fandy.chatroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import priv.fandy.chatroom.common.Result;
import priv.fandy.chatroom.component.WebSocketServer;
import priv.fandy.chatroom.entity.ChatMessages;
import priv.fandy.chatroom.entity.Friend;
import priv.fandy.chatroom.entity.User;
import priv.fandy.chatroom.entity.vo.UserFriendVO;
import priv.fandy.chatroom.entity.dos.UserFriendDO;
import priv.fandy.chatroom.entity.dto.AddFriendDTO;
import priv.fandy.chatroom.entity.dto.FriendDTO;
import priv.fandy.chatroom.entity.query.FriendQuery;
import priv.fandy.chatroom.enums.ChatMessagesStatusEnum;
import priv.fandy.chatroom.mapper.ChatMessagesMapper;
import priv.fandy.chatroom.mapper.FriendMapper;
import priv.fandy.chatroom.mapper.UserMapper;
import priv.fandy.chatroom.service.FriendService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendServiceImpl implements FriendService {

    @Resource
    private FriendMapper friendMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ChatMessagesMapper chatMessagesMapper;

    @Override
    public List<UserFriendVO> selectFriendsByUsername(FriendDTO dto) {
        FriendQuery query = FriendQuery.builder().build();
        BeanUtils.copyProperties(dto,query);

        List<UserFriendDO> userFriendDOS = friendMapper.selectFriendsByUsername(query);
        List<UserFriendVO> vos = userFriendDOS.stream().map(item->{
            UserFriendVO vo = UserFriendVO.builder().build();
            BeanUtils.copyProperties(item,vo);

            LambdaQueryWrapper<ChatMessages> queryMessages = new LambdaQueryWrapper<>();
            queryMessages.eq(ChatMessages::getFromUsername,vo.getUsername());
            queryMessages.eq(ChatMessages::getToUsername,dto.getUsername());
            queryMessages.eq(ChatMessages::getStatus, ChatMessagesStatusEnum.UNREAD.getCode());
            Integer unread = chatMessagesMapper.selectCount(queryMessages);

            vo.setUnread(unread);
            if (WebSocketServer.sessionMap.containsKey(item.getUsername())){
                vo.setOnline(true);
            }
            return vo;
        }).collect(Collectors.toList());

        return vos;
    }

    /**
     * 添加好友
     * @param dto
     */
    @Override
    public Result<?> addFriend(AddFriendDTO dto) {

        //判断账号是否存在
        User user = userMapper.selectByName(dto.getFriendUsername());
        if (user == null){
            return Result.error("10010","账号不存在");
        }

        //判断是否已是好友
        FriendQuery friendQuery = FriendQuery.builder()
                .username(dto.getUsername())
                .friendUsername(dto.getFriendUsername())
                .build();
        List<Friend> friends = friendMapper.selectFriends(friendQuery);
        if (CollectionUtils.isNotEmpty(friends)){
            return Result.error("10011","对方已是你的好友");
        }

        Friend friend = Friend.builder()
                .username(dto.getUsername())
                .friendUsername(dto.getFriendUsername())
                .build();
        friendMapper.insert(friend);

        Friend ffriend = Friend.builder()
                .username(dto.getFriendUsername())
                .friendUsername(dto.getUsername())
                .build();
        friendMapper.insert(ffriend);

        return Result.success();
    }
}
