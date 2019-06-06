package cn.e3mall.manager.service.pageHelper.spring;

import java.util.List;

import cn.e3mall.manager.service.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.base.entity.Item;
import cn.e3mall.manager.dao.ItemMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 分页插件 测试
 *
 * @author colg
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/applicationContext-dao.xml")
public class PageHelperTest extends BaseTest {

    @Autowired
    private ItemMapper itemMapper;

    @SuppressWarnings("resource")
    @Test
    public void testPageHelper() {
        // 执行sql语句之前设置分页信息, 使用PageHelper的startPage方法
        PageHelper.startPage(1, 10, "updated DESC");
        // 执行查询
        List<Item> list = itemMapper.selectAll();
        // 取分页信息
        PageInfo<Item> pageInfo = new PageInfo<>(list);

        log.info("总记录数: {}", pageInfo.getTotal());
        log.info("总页数: {}", pageInfo.getPages());
        log.info("每页的数量: {}", pageInfo.getPageSize());
        log.info("当前页: {}", pageInfo.getPageNum());
        log.info("当前页的数量: {}", pageInfo.getSize());

        log.info("------------------------------------------------------------------------------------------");

        Page<?> page = (Page<?>)list;
        log.info("总记录数: {}", page.getTotal());
        log.info("总页数: {}", page.getPages());
        log.info("每页的数量: {}", page.getPageSize());
        log.info("当前页: {}", page.getPageNum());
        log.info("当前页的数量: {}", page.size());
    }
}
