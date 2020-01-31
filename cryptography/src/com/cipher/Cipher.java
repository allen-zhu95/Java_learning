package com.cipher;

import java.util.List;


/**
 * 密码算法的整体结构
 * @author Allen-zhu
 *
 */
public class Cipher {

	private String structure;		//结构：Feistel/SPN
	private int blockSize;			//分组长度
	private String order;			//顺序
	private List<Integer> sbox;		//S盒，适用于4进4出，每位输出都与4位输入有关的S盒。
	private List<Integer> permution;	//P置换
	private List<Integer> rotate;		//循环移位
	
	public Cipher(String structure, int blockSize, String order, List<Integer> permution, List<Integer> rotate) {
		super();
		this.structure = structure;
		this.blockSize = blockSize;
		this.order = order;
		this.permution = permution;
		this.rotate = rotate;
	}
	
	public String getStructure() {
		return structure;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public String getOrder() {
		return order;
	}

	public List<Integer> getPermution() {
		return permution;
	}

	public List<Integer> getRotate() {
		return rotate;
	}

	KeySchedule keySchedule = null;

	public KeySchedule getKeySchedule() {
		return keySchedule;
	}

	public void setKeySchedule(KeySchedule keySchedule) {
		this.keySchedule = keySchedule;
	}
	
}

