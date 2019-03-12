package cn.e3mall.sso.dao;

import org.apache.ibatis.annotations.Param;

import cn.e3mall.common.base.entity.User;

/**
 * - @mbg.generated
 *
 * @author colg
 */
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User> {

    /**
     * 根据参数和参数类型查询用户
     *
     * @param param
     * @param type
     * @return
     */
    User findByParam(@Param("param") String param, @Param("type") Integer type);

    /**
     * 根据用户名和密码查询用户信息
     *
     * @param username
     * @param password
     * @return
     */
    User findByUserNameAndPassword(@Param("username") String username, @Param("password") String password);
}