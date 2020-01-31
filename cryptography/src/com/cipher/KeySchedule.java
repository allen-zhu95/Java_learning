package com.cipher;

import java.util.List;

/**
 * 
 * @author Allen-zhu
 * 密钥编排，只分析不涉及中间变量的
 */

public class KeySchedule {

	private int keySize;		//密钥长度
	private String order;		//顺序
	private List<Integer> sbox;	//S盒，适用于4进4出，每位输出都与4位输入有关的S盒。
	private int rotate;		//循环移位
	public KeySchedule(int keySize, String order, List<Integer> sbox, int rotate) {
		super();
		this.keySize = keySize;
		this.order = order;
		this.sbox = sbox;
		this.rotate = rotate;
	}
	
	public int getKeySize() {
		return keySize;
	}
	public String getOrder() {
		return order;
	}
	public List<Integer> getSbox() {
		return sbox;
	}
	public int getRotate() {
		return rotate;
	}
	
	
}

