package priv.fandy.chatroom.service.impl;

import priv.fandy.chatroom.export.ExcelExportService;
import priv.fandy.chatroom.entity.ChatMessages;
import priv.fandy.chatroom.entity.dto.DownloadChatMessagesDTO;
import priv.fandy.chatroom.entity.dto.SelectChatMessagesDTO;
import priv.fandy.chatroom.entity.dto.UpdateChatMessagesStatusDTO;
import priv.fandy.chatroom.entity.vo.SelectChatMessagesVO;
import priv.fandy.chatroom.enums.ChatMessagesStatusEnum;
import priv.fandy.chatroom.mapper.ChatMessagesMapper;
import priv.fandy.chatroom.service.ChatMessagesService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessagesServiceImpl implements ChatMessagesService {

    @Resource
    private ChatMessagesMapper chatMessagesMapper;

    @Resource
    private ExcelExportService excelExportService;

    /**
     * 获取消息
     * @param dto
     * @return
     */
    @Override
    public List<SelectChatMessagesVO> selectChatMessages(SelectChatMessagesDTO dto) {
        //获取消息
        List<ChatMessages> chatMessages = chatMessagesMapper.selectChatMessages(dto);
        if (CollectionUtils.isEmpty(chatMessages)){
            return new ArrayList<>();
        }
        List<SelectChatMessagesVO> messagesVOS = chatMessages.stream().map(item->{
            SelectChatMessagesVO messagesVO = SelectChatMessagesVO.builder().build();
            BeanUtils.copyProperties(item,messagesVO);
            return messagesVO;
        }).collect(Collectors.toList());

        //将未读对话消息置为已读
        UpdateChatMessagesStatusDTO updateStatusDTO = UpdateChatMessagesStatusDTO.builder()
                .fromUsername(dto.getToUsername())
                .toUsername(dto.getFromUsername())
                .status(ChatMessagesStatusEnum.READ.getCode())
                .build();
        chatMessagesMapper.updateChatMessagesStatus(updateStatusDTO);

        return messagesVOS;
    }

    /**
     * 新增消息
     * @param chatMessages
     */
    @Override
    public void addChatMessages(ChatMessages chatMessages) {
        chatMessagesMapper.insert(chatMessages);
    }

    /**
     * 更新消息状态
     * @param updateStatusDTO
     */
    @Override
    public void updateChatMessagesStatus(UpdateChatMessagesStatusDTO updateStatusDTO) {
        chatMessagesMapper.updateChatMessagesStatus(updateStatusDTO);
    }


    /**
     * 下载mac
     * @param dto
     */
    @Override
    public void downloadChatMessages(DownloadChatMessagesDTO dto) {

        excelExportService.export(dto);;
    }
}
