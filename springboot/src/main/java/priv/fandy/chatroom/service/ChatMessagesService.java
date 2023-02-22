package priv.fandy.chatroom.service;

import priv.fandy.chatroom.entity.ChatMessages;
import priv.fandy.chatroom.entity.dto.DownloadChatMessagesDTO;
import priv.fandy.chatroom.entity.dto.SelectChatMessagesDTO;
import priv.fandy.chatroom.entity.dto.UpdateChatMessagesStatusDTO;
import priv.fandy.chatroom.entity.vo.SelectChatMessagesVO;

import java.util.List;

public interface ChatMessagesService {

    List<SelectChatMessagesVO> selectChatMessages(SelectChatMessagesDTO dto);

    void addChatMessages(ChatMessages chatMessages);

    void updateChatMessagesStatus(UpdateChatMessagesStatusDTO updateStatusDTO);

    void downloadChatMessages(DownloadChatMessagesDTO dto);
}
