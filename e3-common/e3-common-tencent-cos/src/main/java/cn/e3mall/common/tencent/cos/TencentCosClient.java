package cn.e3mall.common.tencent.cos;

import java.io.File;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;

import lombok.extern.slf4j.Slf4j;

/**
 * 腾讯云 - 对象存储空间
 *
 * @author colg
 */
@Slf4j
public class TencentCosClient {

    /** 密钥 */
    public static final String ACCESSKEY = "AKIDUvjV6VEvvjS4Vliw360iEYpvpgMrqMKF";
    public static final String SECRETKEY = "sY3NVSL8kLk8KK8lIftzud9VggU5Vkne";
    /** 存储桶可用区域简称: 广州（华南）   ap-guangzhou */
    public static final String REGION_NAME = "ap-guangzhou";
    /** 存储桶名称: 自定义字符串-APPID */
    public static final String BUCKET_NAME = "colg-1256242877";
    /** 文件列表: 目录 */
    public static final String KEY_PRE = "e3mall/";

    public static final String SERVER_URL = "https://colg-1256242877.cos.ap-guangzhou.myqcloud.com/";

    /** 单例模式 */
    private static volatile TencentCosClient tencentCosClient = null;
    
    private ClientConfig clientConfig = null;
    private COSClient cosClient = null;

    public TencentCosClient() {
        COSCredentials cred = new BasicCOSCredentials(ACCESSKEY, SECRETKEY);
        clientConfig = new ClientConfig(new Region(REGION_NAME));
        cosClient = new COSClient(cred, clientConfig);
        log.info("腾讯云 - 对象存储初始化完成!");
    }

    public static TencentCosClient create() {
        if (tencentCosClient == null) {
            synchronized (TencentCosClient.class) {
                if (tencentCosClient == null) {
                    tencentCosClient = new TencentCosClient();
                }
            }
        }
        return tencentCosClient;
    }

    /**
     * 上传文件
     *
     * @param localFilename
     * @param key
     * @return
     */
    public String uploadFile(String localFilename, String key) {
        File file = new File(localFilename);
        cosClient.putObject(BUCKET_NAME, key, file);
        return SERVER_URL + key;
    }

    /**
     * 删除文件
     *
     * @param key
     */
    public void deleteFile(String key) {
        cosClient.deleteObject(BUCKET_NAME, key);
    }
}
