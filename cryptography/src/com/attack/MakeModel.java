package com.attack;

import java.util.List;

import com.cipher.Cipher;
import com.cipher.IntegeralDistinguisher;

/**
 * @author Allen-zhu
 * 对密码进行建模
 */
public interface MakeModel {

	/**
	 * 对密码算法建模
	 * @param filePath		文件路径
	 * @param fileName		文件名
	 * @return
	 */
	public Cipher makeCipherModel(String filePath, String fileName);
	
	/**
	 * 构建积分区分器
	 * @param filePath		文件路径
	 * @param fileName		文件名
	 * @return
	 */
	public List<IntegeralDistinguisher> makeIntegeralDistinguisher(String filePath, String fileName);
}

