package cn.e3mall.common.tencent.cos;

import java.io.File;

import org.junit.Test;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;

import lombok.extern.slf4j.Slf4j;

/**
 * 腾讯云 - 对象存储测试
 *
 * @author colg
 */
@Slf4j
public class TencentCosTest {

    @Test
    public void testName() throws Exception {
        COSCredentials cred = new BasicCOSCredentials("AKIDUvjV6VEvvjS4Vliw360iEYpvpgMrqMKF", "sY3NVSL8kLk8KK8lIftzud9VggU5Vkne");
        // 采用了新的region名字，可用region的列表可以在官网文档中获取，也可以参考下面的XML SDK和JSON SDK的地域对照表
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket的名字需要的包含appid
        String bucketName = "colg-1256242877";

        // 上传文件
        File localFile = new File("D:\\workspace-all\\iheima\\e3mall\\e3-common\\e3-common-fastdfs\\src\\test\\resources\\images\\FastDfs架构.png");
        String key = "jiagou.png";
        
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard);
        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
            log.info("etag: {}", etag);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

        // 关闭客户端
        cosclient.shutdown();
    }
}
