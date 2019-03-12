package cn.e3mall.common.fastdfs;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * FastDfs 分布式文件系统
 *
 * @author colg
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FastDfsClient {
    
    /** 路径分隔符 */
    public static final String SEPARATOR  = "/";
    
    /** server的IP和端口用冒号':'分隔, server之间用逗号','分隔; 例如："10.0.11.245:22122,10.0.11.246:22122" */
    @Getter
    @Setter
    private String trackerServers = "192.168.21.110:22122";

    /** imageServer http://ip; http://域名 */
    @Getter
    @Setter
    private String imageServer = "http://192.168.21.110";

    /// fastdfs 配置
    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient1 storageClient = null;
    
    /**
     * 初始化FastDfsClient
     *
     * @param confFilename
     * @throws MyException
     * @throws IOException
     */
    /**
     * 初始化 FastDfsClient
     *
     * @param confFilename
     * @throws IOException
     * @throws MyException
     */
    private FastDfsClient(String trackerServers) throws IOException, MyException {
        if (StrUtil.isEmpty(trackerServers)) {
            ClientGlobal.initByTrackers(this.trackerServers);
        } else {
            // 处理运行环境配置
            /*
            String classpath = "classpath:";
            if (confFilename.contains(classpath)) {
                confFilename = confFilename.replace(classpath, this.getClass().getResource("/").getPath());
            }
            ClientGlobal.init(confFilename);
            */
            ClientGlobal.initByTrackers(trackerServers);
        }

        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
        
        log.info("FastDFS: {}", ClientGlobal.configInfo());
    }

    /**
     * 创建 FastDfsClient
     *
     * @param trackerServers
     * @return
     * @throws MyException
     * @throws IOException
     */
    public static FastDfsClient create(String trackerServers) throws IOException, MyException {
        return new FastDfsClient(trackerServers);
    }

    /**
     * 创建 FastDfsClient
     *
     * @return
     * @throws IOException
     * @throws MyException
     */
    public static FastDfsClient create() throws IOException, MyException {
        return new FastDfsClient(null);
    }

    /**
     * 上传文件到FastDfs服务器
     *
     * @param localFilename 文件全路径
     * @param extName 文件扩展名, 不包含(.)
     * @param metas 文件扩展信息
     * @return
     * @throws MyException
     * @throws IOException
     */
    public String uploadFile(String localFilename, String extName, NameValuePair[] metas) throws IOException, MyException {
        return this.getImageServer() + SEPARATOR + storageClient.upload_file1(localFilename, extName, metas);
    }

    public String uploadFile(String localFilename, String extName) throws IOException, MyException {
        return uploadFile(localFilename, extName, null);
    }

    public String uploadFile(String localFilename) throws IOException, MyException {
        return uploadFile(localFilename, null);
    }

    /**
     * 上传文件到FastDfs服务器
     *
     * @param fileBuff 文件的内容, 字节数组
     * @param extName 文件扩展名, 不包含(.)
     * @param metas 文件扩展信息
     * @return
     * @throws IOException
     * @throws MyException
     */
    public String uploadFile(byte[] fileBuff, String extName, NameValuePair[] metas) throws IOException, MyException {
        return this.getImageServer() + SEPARATOR + storageClient.upload_file1(fileBuff, extName, metas);
    }

    public String uploadFile(byte[] fileBuff, String extName) throws IOException, MyException {
        return uploadFile(fileBuff, extName, null);
    }

    public String uploadFile(byte[] fileBuff) throws IOException, MyException {
        return uploadFile(fileBuff, null);
    }

    /**
     * 从FastDfs服务器删除文件
     *
     * @param fileId 文件id(包含组名和文件名)
     * @return
     * @throws MyException
     * @throws IOException
     */
    /**
     * 从FastDfs服务器删除文件
     *
     * @param fileId 文件id(包含组名和文件名)
     * @return 0: 成功, 失败(抛异常)
     * @throws IOException
     * @throws MyException
     */
    public int deleteFile(String fileId) throws IOException, MyException {
        return storageClient.delete_file1(fileId);
    }
}
