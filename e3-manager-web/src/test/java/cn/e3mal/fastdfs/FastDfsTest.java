package cn.e3mal.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDfsClient;

/**
 * FastDFS 分布式文件系统测试
 *
 * @author colg
 */
public class FastDfsTest {

	@Test
	public void testUpload() throws Exception {
		// 创建一个配置文件。文件名任意，内容就是tracker服务器的地址
		// 使用全局对象加载配置文件。
		ClientGlobal.init("D:\\spring-tool-suite-3.7.3\\sts-3.7.3-workspace\\e3\\e3-manager-web\\src\\main\\resources\\conf\\storage-client.conf");
		// 创建一个TrackerClient对象。连接服务端
		TrackerClient trackerClient = new TrackerClient();
		// 通过TrackerClient获取一个TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		// 创建一个StroageServer的引用，可以是null
		StorageServer storageServer = null;
		// 创建一个StorageClient，参数需要TrackerServer和StroageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 使用StorageClient上传文件
		String[] strings = storageClient.upload_file("‪‪E:\\Downloads\\wKgVblqlOjuAB4ENAABdrZgsqUU889.jpg", "jpg", null);

		// 返回数组，包含组名和图片的路径
		for (String string : strings) {
			System.out.println(string);
		}
	}

	@Test
	public void testUpload1() {
		FastDfsClient fastDFSClient = new FastDfsClient(
				"D:\\spring-tool-suite-3.7.3\\sts-3.7.3-workspace\\e3\\e3-manager-web\\src\\main\\resources\\conf\\storage-client.conf");
		String uploadFile = fastDFSClient.uploadFile("C:\\Users\\Black Cloud\\Pictures\\IMG20180222212349.jpg");
		System.out.println(uploadFile);
	}

	@Test
	public void testDelete1() {
		FastDfsClient fastDFSClient = new FastDfsClient(
				"D:\\spring-tool-suite-3.7.3\\sts-3.7.3-workspace\\e3\\e3-manager-web\\src\\main\\resources\\conf\\storage-client.conf");
		int uploadFile = fastDFSClient.deleteFile("group1/M00/00/00/wKgVblqlOjuAB4ENAABdrZgsqUU889_big.jpg");
		System.out.println(uploadFile);
	}
}
