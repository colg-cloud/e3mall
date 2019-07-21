package cn.e3mall.common.tencent.cos;

import cn.e3mall.common.base.util.SnUtil;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 腾讯云 - 对象存储空间 测试
 *
 * @author colg
 */
@Slf4j
public class TencentCosClientTest {

    /**
     * 项目基础路径
     */
    public static final String PROJECT_PATH = System.getProperty("user.dir");

    /**
     * Test method for {@link cn.e3mall.common.tencent.cos.TencentCosClient#uploadFile(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testUploadFile() {
        TencentCosClient tencentCosClient = TencentCosClient.create();
        String filename = SnUtil.genImageName() + ".jpg";
        String uploadFile = tencentCosClient.uploadFile(PROJECT_PATH + "\\src\\test\\resources\\images\\FastDfs文件上传流程.png", TencentCosClient.KEY_PRE + filename);
        log.info("图片路径: {}", uploadFile);
    }

    /**
     * Test method for {@link cn.e3mall.common.tencent.cos.TencentCosClient#deleteFile(java.lang.String)}.
     */
    @Test
    public final void testDeleteFile() {
        TencentCosClient tencentCosClient = TencentCosClient.create();
        String filename = "2019-07-21_0674.jpg";
        tencentCosClient.deleteFile(TencentCosClient.KEY_PRE + filename);
    }

}
