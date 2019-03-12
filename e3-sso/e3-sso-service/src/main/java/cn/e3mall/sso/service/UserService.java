package cn.e3mall.sso.service;

import cn.e3mall.common.base.entity.User;
import cn.e3mall.common.base.pojo.E3Result;

/**
 * 
 *
 * @author colg
 */
public interface UserService {

    /**
     * 注册, 数据校验
     *
     * @param param 参数
     * @param type 参数类型 - 1: 用户名; 2: 手机; 3: 邮箱
     * @return
     */
    E3Result checkData(String param, Integer type);

    /**
     * 注册, 添加用户
     *
     * @param user
     * @return
     */
    E3Result addUser(User user);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    E3Result login(String username, String password);

}
