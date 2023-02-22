package priv.fandy.chatroom.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class DownloadChatMessages {

    /**
     * 内容
     */
    @ExcelProperty("内容")
    private String content;

    /**
     * 发送方用户名
     */
    @ExcelProperty("发送方")
    private String fromUsername;

    /**
     * 接收方姓名
     */
    @ExcelProperty("接收方")
    private String toUsername;

    /**
     * 接收时间
     */
    @ExcelProperty("时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
}

