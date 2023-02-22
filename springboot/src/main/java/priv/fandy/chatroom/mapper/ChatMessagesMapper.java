package priv.fandy.chatroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import priv.fandy.chatroom.entity.ChatMessages;
import priv.fandy.chatroom.entity.dto.SelectChatMessagesDTO;
import priv.fandy.chatroom.entity.dto.UpdateChatMessagesStatusDTO;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ChatMessagesMapper extends BaseMapper<ChatMessages> {

    List<ChatMessages> selectChatMessages(SelectChatMessagesDTO dto);

    @Update("update chat_messages set status = #{status} where from_username = #{fromUsername} and to_username = #{toUsername}")
    void updateChatMessagesStatus(UpdateChatMessagesStatusDTO updateStatusDTO);
}
