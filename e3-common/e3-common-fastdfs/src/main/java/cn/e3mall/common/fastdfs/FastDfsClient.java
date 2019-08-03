package cn.e3mall.common.fastdfs;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.IOException;

/**
 * FastDfs 分布式文件系统
 *
 * @author colg
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FastDfsClient {

    /**
     * server的IP和端口用冒号':'分隔, server之间用逗号','分隔; 例如："10.0.11.245:22122,10.0.11.246:22122"
     */
    @Getter
    @Setter
    private String trackerServers = "192.168.21.110:22122";

    /**
     * imageServer http://ip; http://域名
     */
    @Getter
    @Setter
    private String imageServer = "http://192.168.21.110";

    /**
     * Storage client
     */
    private StorageClient1 storageClient1 = null;

    /**
     * Initialization FastDfsClient
     *
     * @param trackerServers
     * @throws IOException
     * @throws MyException
     */
    private FastDfsClient(String trackerServers) throws IOException, MyException {
        if (StrUtil.isEmpty(trackerServers)) {
            ClientGlobal.initByTrackers(this.getTrackerServers());
        } else {
            /// 处理运行环境配置
            /*
            String classpath = "classpath:";
            if (confFilename.contains(classpath)) {
                confFilename = confFilename.replace(classpath, this.getClass().getResource("/").getPath());
            }
            ClientGlobal.init(confFilename);
            */
            ClientGlobal.initByTrackers(trackerServers);
        }

        // Tracker client
        TrackerClient trackerClient = new TrackerClient();
        // Tracker Server Info
        TrackerServer trackerServer = trackerClient.getConnection();
        storageClient1 = new StorageClient1(trackerServer, null);

        log.info("FastDFS: 初始化完成!");
        log.info("FastDFS: \n{}", ClientGlobal.configInfo());
    }

    /**
     * Create FastDfsClient
     *
     * @param trackerServers
     * @return
     * @throws IOException
     * @throws MyException
     * @author colg
     */
    public static FastDfsClient create(String trackerServers) throws IOException, MyException {
        return new FastDfsClient(trackerServers);
    }

    /**
     * Create FastDfsClient
     *
     * @return
     * @throws IOException
     * @throws MyException
     * @author colg
     */
    public static FastDfsClient create() throws IOException, MyException {
        return new FastDfsClient(null);
    }

    /**
     * Upload file to FastDfs Server
     *
     * @param localFilename 文件全路径
     * @param extName       文件扩展名, 不包含(.)
     * @param metas         文件扩展信息
     * @return
     * @throws IOException
     * @throws MyException
     * @author colg
     */
    public String uploadFile(String localFilename, String extName, NameValuePair[] metas) throws IOException, MyException {
        return this.getImageServer() + StorageClient1.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + storageClient1.upload_file1(localFilename, extName, metas);
    }

    public String uploadFile(String localFilename, String extName) throws IOException, MyException {
        return uploadFile(localFilename, extName, null);
    }

    public String uploadFile(String localFilename) throws IOException, MyException {
        return uploadFile(localFilename, null);
    }

    /**
     * Upload file to FastDfs Server
     *
     * @param fileBuff 文件的内容, 字节数组
     * @param extName  文件扩展名, 不包含(.)
     * @param metas    文件扩展信息
     * @return
     * @throws IOException
     * @throws MyException
     */
    public String uploadFile(byte[] fileBuff, String extName, NameValuePair[] metas) throws IOException, MyException {
        return this.getImageServer() + StorageClient1.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + storageClient1.upload_file1(fileBuff, extName, metas);
    }

    public String uploadFile(byte[] fileBuff, String extName) throws IOException, MyException {
        return uploadFile(fileBuff, extName, null);
    }

    public String uploadFile(byte[] fileBuff) throws IOException, MyException {
        return uploadFile(fileBuff, null);
    }

    /**
     * Delete file from FastDfs Server
     *
     * @param fileId 文件id(包含组名和文件名)
     * @return 0: 成功, 失败(抛异常)
     * @throws IOException
     * @throws MyException
     */
    public int deleteFile(String fileId) throws IOException, MyException {
        return storageClient1.delete_file1(fileId);
    }

}
