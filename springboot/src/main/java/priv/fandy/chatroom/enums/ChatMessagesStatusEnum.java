package priv.fandy.chatroom.enums;

import lombok.Getter;

/**
 * @version 1.0
 * @Description 聊天消息状态枚举
 * @Date 2021/12/3 21:40
 */
@Getter
public enum ChatMessagesStatusEnum {

    /**
     * 已读
     */
    READ(1, "已读"),

    /**
     * 未读
     */
    UNREAD(2, "未读");


    /**
     * code
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;


    ChatMessagesStatusEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
