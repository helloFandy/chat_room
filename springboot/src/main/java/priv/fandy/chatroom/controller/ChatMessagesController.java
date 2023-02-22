package priv.fandy.chatroom.controller;

import priv.fandy.chatroom.common.Result;
import priv.fandy.chatroom.entity.ChatMessages;
import priv.fandy.chatroom.entity.dto.DownloadChatMessagesDTO;
import priv.fandy.chatroom.entity.dto.SelectChatMessagesDTO;
import priv.fandy.chatroom.entity.dto.UpdateChatMessagesStatusDTO;
import priv.fandy.chatroom.entity.vo.SelectChatMessagesVO;
import priv.fandy.chatroom.service.ChatMessagesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/chatMessages")
public class ChatMessagesController extends BaseController{

    @Resource
    private ChatMessagesService chatMessagesService;

    @Resource
    HttpServletRequest request;

    @PostMapping("/selectChatMessages")
    public Result<?> selectChatMessages(@RequestBody SelectChatMessagesDTO dto) {
        List<SelectChatMessagesVO> messagesVOS = chatMessagesService.selectChatMessages(dto);
        return Result.success(messagesVOS);
    }

    @PostMapping("/addChatMessages")
    public Result<?> addChatMessages(@RequestBody ChatMessages chatMessages) {
        chatMessagesService.addChatMessages(chatMessages);
        return Result.success();
    }

    @PostMapping("/updateChatMessagesStatus")
    public Result<?> updateChatMessagesStatus(@RequestBody UpdateChatMessagesStatusDTO updateStatusDTO) {
        chatMessagesService.updateChatMessagesStatus(updateStatusDTO);
        return Result.success();
    }

    @PostMapping("/downloadChatMessages")
    public Result<?> downloadChatMessages(@RequestBody DownloadChatMessagesDTO dto) {
        chatMessagesService.downloadChatMessages(dto);
        return Result.success();
    }

}
