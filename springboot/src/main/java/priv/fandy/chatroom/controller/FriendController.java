package priv.fandy.chatroom.controller;

import priv.fandy.chatroom.common.Result;
import priv.fandy.chatroom.entity.vo.UserFriendVO;
import priv.fandy.chatroom.entity.dto.AddFriendDTO;
import priv.fandy.chatroom.entity.dto.FriendDTO;
import priv.fandy.chatroom.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/friend")
public class FriendController extends BaseController {

    @Autowired
    private FriendService friendService;

    /**
     * 根据用户id获取好友
     * @param dto
     * @return
     */
    @PostMapping("/selectFriendsByUsername")
    public Result<?> selectFriendsByUsername(@RequestBody FriendDTO dto){
        List<UserFriendVO> friendVOS = friendService.selectFriendsByUsername(dto);
        return Result.success(friendVOS);
    }

    /**
     * 新增好友
     * @param dto
     * @return
     */
    @PostMapping("/addFriend")
    public Result<?> addFriend(@RequestBody AddFriendDTO dto){
        return friendService.addFriend(dto);
    }

}
