package com.cipher;

import java.util.List;

/**
 * @author Allen-zhu
 * 积分区分器
 */
public class IntegeralDistinguisher {

	private int round;			//轮数
	private List<Integer> balancedBits;	//平衡比特位置
	
	public IntegeralDistinguisher(int round, List<Integer> balancedBits) {
		super();
		this.round = round;
		this.balancedBits = balancedBits;
	}

	public int getRound() {
		return round;
	}

	public List<Integer> getBalancedBits() {
		return balancedBits;
	}
	
	
}

