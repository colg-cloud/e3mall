package cn.e3mall.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.e3mall.manager.pojo.TbUser;

/**
 * 
 *
 * @author colg
 */
public interface TbUserMapper {
	int deleteByPrimaryKey(Long id);

	int insert(TbUser record);

	TbUser selectByPrimaryKey(Long id);

	List<TbUser> selectAll();

	int updateByPrimaryKey(TbUser record);

	List<TbUser> selectByParam(@Param("param") String param, @Param("type") Integer type);

	TbUser findByUserNameAndPassword(@Param("username") String username, @Param("password") String password);
}