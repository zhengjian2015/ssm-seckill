package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKillDao {
	
	/*
	 *���빺����ϸ���ɹ����ظ� 
	 * ���������
	 */
	int insertSuccesskilled(@Param("sekillId") long seckillId,@Param("userPhone") long userPhone);
	
	//��Я����Ʒ����
	SuccessKilled queryByIdWithSeckill(@Param("sekillId") long seckillId,@Param("userPhone") long userPhone);
}
