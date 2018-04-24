package cn.e3mall.common.pojo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * 
 *
 * @author colg
 */
public class E3ResultTest {

	@Test
	public void test1() {
		User user = new User(1, "jack");
		User user2 = new User(2, "rose");
		List<Object> list = new ArrayList<>();
		list.add(user);
		list.add(user2);
		
		E3Result e3Result = E3Result.ok();
		System.out.println(JSON.toJSONString(e3Result.setData(user), true));
		
		e3Result.setData(list);
		System.out.println(JSON.toJSONString(e3Result, true));
		
		EasyUIDataGridResult dataGridResult = new EasyUIDataGridResult();
		dataGridResult.setTotal((long) list.size());
		dataGridResult.setRows(list);
		e3Result.setData(dataGridResult);
		System.out.println(JSON.toJSONString(e3Result, true));
		
		EasyUiTreeNode easyUITreeNode = new EasyUiTreeNode();
		easyUITreeNode.setId((long) 1);
		easyUITreeNode.setState("closed");
		easyUITreeNode.setText("名字");
		e3Result.setData(easyUITreeNode);
		System.out.println(JSON.toJSONString(e3Result, true));
	}
}
