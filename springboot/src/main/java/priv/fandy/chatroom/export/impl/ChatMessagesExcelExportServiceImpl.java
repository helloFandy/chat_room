package priv.fandy.chatroom.export.impl;

import priv.fandy.chatroom.entity.ChatMessages;
import priv.fandy.chatroom.entity.DownloadChatMessages;
import priv.fandy.chatroom.entity.dto.DownloadChatMessagesDTO;
import priv.fandy.chatroom.entity.dto.SelectChatMessagesDTO;
import priv.fandy.chatroom.export.AbstractExcelExportService;
import priv.fandy.chatroom.mapper.ChatMessagesMapper;
import priv.fandy.chatroom.utils.DownloadUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessagesExcelExportServiceImpl extends AbstractExcelExportService<DownloadChatMessages,DownloadChatMessagesDTO> {

    @Resource
    private ChatMessagesMapper chatMessagesMapper;

    @Override
    public void export(DownloadChatMessagesDTO param){
        //获取消息
        List<DownloadChatMessages> chatMessages = this.getData(param);
        DownloadUtil.simpleWrite(DownloadChatMessages.class,chatMessages);
    }

    @Override
    public List<DownloadChatMessages> getData(DownloadChatMessagesDTO dto) {
        SelectChatMessagesDTO selectDTO = new SelectChatMessagesDTO();
        BeanUtils.copyProperties(dto,selectDTO);
        List<ChatMessages> chatMessages = chatMessagesMapper.selectChatMessages(selectDTO);

        List<DownloadChatMessages> downloadChatMessages = chatMessages.stream().map(item->{
            DownloadChatMessages downloadChatMessage = DownloadChatMessages.builder().build();
            BeanUtils.copyProperties(item,downloadChatMessage);
            return downloadChatMessage;
        }).collect(Collectors.toList());

        return downloadChatMessages;
    }
}
