package priv.fandy.chatroom.entity.dto;

import lombok.Data;


@Data
public class SelectEmailPageDTO extends BasePageDTO {

    //标题
    private String title;

    //发送方
    private String fromUsername;
}
