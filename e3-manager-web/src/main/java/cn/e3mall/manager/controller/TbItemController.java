package cn.e3mall.manager.controller;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.PictureResult;
import cn.e3mall.common.utils.FastDfsClient;
import cn.e3mall.manager.core.BaseController;
import cn.e3mall.manager.pojo.TbItem;

/**
 * 商品管理Controller
 *
 * @author colg
 */
@RestController
@RequestMapping("/tb/item")
public class TbItemController extends BaseController {

	private static final String CLASSPATH_CONF_STORAGE_CLIENT_CONF = "classpath:conf/storage-client.conf";

	/** 获取图片服务器的地址 */
	@Value("${IMAGE_SERVER_URL}")
	private String imageServerUrl;

	/**
	 * 根据商品id查询商品
	 * 
	 * @param itemId
	 * @return
	 */
	@GetMapping("/{itemId}")
	public TbItem getTbItem(@PathVariable Long itemId) {
		return tbItemService.getTbItemById(itemId);
	}

	/**
	 * 商品列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@GetMapping("/list")
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 调用服务查询商品列表
		return tbItemService.getItemList(page, rows);
	}

	/**
	 * 上传图片
	 * 
	 * @param uploadFile
	 * @return
	 */
	@PostMapping("/pic/upload")
	public PictureResult uploadFile(MultipartFile uploadFile) {
		// 把图片上传到图片服务器
		FastDfsClient fastDFSClient = new FastDfsClient(CLASSPATH_CONF_STORAGE_CLIENT_CONF);
		// 得到一个图片地址和文件名
		String url = null;
		// 成功
		Integer error = 0;
		// 错误消息
		String message = null;
		try {
			url = fastDFSClient.uploadFile(uploadFile.getBytes(), FilenameUtils.getExtension(uploadFile.getOriginalFilename()));
		} catch (IOException e) {
			e.printStackTrace();
			// 失败
			error = 1;
			message = "图片上传失败！";
		}
		// 补充为完整的url
		url = imageServerUrl + url;
		// 响应json
		return new PictureResult().setError(error).setUrl(url).setMessage(message);
	}

	/**
	 * 添加商品
	 * 
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	@PostMapping("/save")
	public E3Result save(TbItem tbItem, String desc) {
		return tbItemService.addItem(tbItem, desc);
	}

	/**
	 * 修改商品
	 * 
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	@PostMapping("/update")
	public E3Result update(Long id, TbItem tbItem, String desc) {
		return tbItemService.updateItem(id, tbItem, desc);
	}

	/**
	 * 批量删除商品
	 * 
	 * @param ids
	 * @return
	 */
	@PostMapping("/delete")
	public E3Result delete(String ids) {
		return tbItemService.delete(ids);
	}

	/**
	 * 批量下架商品
	 * 
	 * @param ids
	 * @return
	 */
	@PostMapping("/instock")
	public E3Result instock(String ids) {
		return tbItemService.updateInstock(ids);
	}

	/**
	 * 批量上架商品
	 * 
	 * @param ids
	 * @return
	 */
	@PostMapping("/reshelf")
	public E3Result reshelf(String ids) {
		return tbItemService.updateReshelf(ids);
	}

	/**
	 * 一键导入商品数据到索引库
	 * 
	 * @return
	 */
	@PostMapping("/import")
	public E3Result importAllItem() {
		return tbItemServiceSearch.importAllItems();
	}
}