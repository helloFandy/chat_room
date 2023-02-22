package priv.fandy.chatroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import priv.fandy.chatroom.entity.UserRole;
import org.apache.ibatis.annotations.Select;

/**
 * @author 太好听了吧
 * @version 1.0
 * @Description 用户角色数据层
 * @Date 2021/12/3 21:44
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Select("delete from user_role where user_id=#{userId}")
    void deleteByUserRoleId(Long userId);
}
