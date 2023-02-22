package priv.fandy.chatroom.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import priv.fandy.chatroom.entity.Email;
import priv.fandy.chatroom.entity.dto.SelectEmailPageDTO;

public interface EmailService {

    Page<Email> selectPage(SelectEmailPageDTO dto);

    void updateStatus(Email email);

    void addEmail(Email email);

}
