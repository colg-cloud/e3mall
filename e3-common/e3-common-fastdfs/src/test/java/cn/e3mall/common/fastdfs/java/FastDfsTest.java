package cn.e3mall.common.fastdfs.java;

import cn.e3mall.common.fastdfs.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * FastDfs 测试
 *
 * @author colg
 */
@Slf4j
public class FastDfsTest extends BaseTest {

    /**
     * Test method for {@link cn.e3mall.common.fastdfs.FastDfsClient#uploadFile(java.lang.String)}.
     */
    @Test
    public final void testUploadFileString() throws Exception {
        // 初始化全局对象
        ClientGlobal.initByTrackers("192.168.21.110:22122");
        // 创建一个TrackerClient对象, 连接服务器
        TrackerClient trackerClient = new TrackerClient();
        // 通过TrackerClient获取一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 创建一个StorageServer的引用，可以是null
        StorageServer storageServer = null;
        // 创建一个StorageClient，参数需要TrackerServer和StorageServer
        StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
        // 使用StorageClient上传文件
        String uploadFile = storageClient.upload_file1(PROJECT_PATH + "\\src\\test\\resources\\images\\FastDfs架构.png", "png", null);

        // 服务路径: http://192.168.21.110
        // 图片路径: 组名/磁盘/存储目录/文件名
        // 图片完整路径: http://192.168.21.110/group1/M00/00/01/wKgVblx1OW2AEO0nAAHsodtdFiY723.png
        log.info("图片路径: {}", "http://192.168.21.110" + StorageClient1.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + uploadFile);
    }

}
