package priv.fandy.chatroom.entity.dto;

import lombok.Data;

@Data
public class BasePageDTO {

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
