package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKillDao {
	
	/*
	 *插入购买明细，可过滤重复 
	 * 插入的行数
	 */
	int insertSuccesskilled(@Param("sekillId") long seckillId,@Param("userPhone") long userPhone);
	
	//并携带商品对象
	SuccessKilled queryByIdWithSeckill(@Param("sekillId") long seckillId,@Param("userPhone") long userPhone);
}
