package cn.e3mall.common.base.util;

import cn.e3mall.common.base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 序号生成工具 测试
 *
 * @author colg
 */
@Slf4j
public class SnUtilTest extends BaseTest {

    /**
     * Test method for {@link cn.e3mall.common.base.util.SnUtil#genImageName()}.
     */
    @Test
    public void testGenImageName() {
        String imageName = SnUtil.genImageName();
        log.info("imageName: {}", imageName);
    }

    /**
     * Test method for {@link cn.e3mall.common.base.util.SnUtil#genItemId()}.
     */
    @Test
    public void testGenItemId() {
        long itemId = SnUtil.genItemId();
        log.info("itemId: {}", itemId);
    }

}