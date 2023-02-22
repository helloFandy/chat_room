package priv.fandy.chatroom.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import priv.fandy.chatroom.common.Result;
import priv.fandy.chatroom.entity.Email;
import priv.fandy.chatroom.entity.dto.SelectEmailPageDTO;
import priv.fandy.chatroom.service.EmailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/email")
public class EmailController extends BaseController {

    @Resource
    private EmailService emailService;

    @PostMapping("/findPage")
    public Result<?> findPage(@RequestBody SelectEmailPageDTO dto) {
        Page<Email> page = emailService.selectPage(dto);
        return Result.success(page);
    }

    @PostMapping("/updateStatus")
    public Result<?> updateStatus(@RequestBody Email dto) {
        emailService.updateStatus(dto);
        return Result.success();
    }

    @PostMapping("/addEmail")
    public Result<?> addEmail(@RequestBody Email dto) {
        emailService.addEmail(dto);
        return Result.success();
    }
}
