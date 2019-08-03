package cn.e3mall.common.fastdfs.java;

import cn.e3mall.common.fastdfs.BaseTest;
import cn.e3mall.common.fastdfs.FastDfsClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * FastDfsClient 测试
 *
 * @author colg
 */
@Slf4j
public class FastDfsClientTest extends BaseTest {

    /**
     * Test method for {@link cn.e3mall.common.fastdfs.FastDfsClient#uploadFile(java.lang.String)}.
     */
    @Test
    public final void testUpload() throws Exception {
        FastDfsClient fastDfsClient = FastDfsClient.create();
        String uploadFile = fastDfsClient.uploadFile(PROJECT_PATH + "\\src\\test\\resources\\images\\FastDfs架构.png");
        log.info("图片路径: {}", uploadFile);
    }

}
