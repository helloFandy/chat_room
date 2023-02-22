package priv.fandy.chatroom.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import priv.fandy.chatroom.entity.Email;
import priv.fandy.chatroom.entity.dto.SelectEmailPageDTO;
import priv.fandy.chatroom.mapper.EmailMapper;
import priv.fandy.chatroom.service.EmailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    private EmailMapper emailMapper;

    @Override
    public Page<Email> selectPage(SelectEmailPageDTO dto) {
        LambdaQueryWrapper<Email> wrapper = Wrappers.<Email>lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(dto.getTitle()),Email::getTitle, dto.getTitle());
        wrapper.eq(StringUtils.isNotBlank(dto.getFromUsername()),Email::getFromUsername,dto.getFromUsername());
        return emailMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), wrapper);
    }

    @Override
    public void updateStatus(Email email) {
        emailMapper.updateById(email);
    }

    @Override
    public void addEmail(Email email) {
        emailMapper.insert(email);
    }
}
