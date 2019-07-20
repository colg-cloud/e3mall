package cn.e3mall.common.fastdfs;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * FastDfsClient 测试
 *
 * @author colg
 */
@Slf4j
public class FastDfsClientTest extends BaseTest {

    /** Project Base Path */
    public static final String PROJECT_PATH = System.getProperty("user.dir");

    /**
     * Test method for {@link cn.e3mall.common.fastdfs.FastDfsClient#uploadFile(java.lang.String)}.
     */
    @Test
    public final void testUpload() throws Exception {
        FastDfsClient fastDfsClient = FastDfsClient.create();
        String uploadFile = fastDfsClient.uploadFile(PROJECT_PATH + "\\src\\test\\resources\\images\\FastDfs文件上传流程.png");
        log.info("图片路径: {}", uploadFile);
    }

    /**
     * Test method for {@link cn.e3mall.common.fastdfs.FastDfsClient#deleteFile(java.lang.String)}.
     */
    @Test
    public final void testDeleteFile() throws Exception {
        FastDfsClient fastDfsClient = FastDfsClient.create();
        int deleteFile = fastDfsClient.deleteFile("group1/M00/00/01/wKgVblx3TNSAIyO-AACyH0DVQw8099.png");
        log.info("图片路径: {}", deleteFile);
    }

}
