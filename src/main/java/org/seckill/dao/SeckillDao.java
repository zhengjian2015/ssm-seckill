package org.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

public interface SeckillDao {
	
	//���Ӱ������>1��ʾ��������
	int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);
	
	Seckill queryById(long seckillId);
	
	/*���ݱ�������ѯ
	 * */
	List<Seckill> queryAll(@Param("offst") int offset, @Param("limit") int limit);
	
	void killByProcedure(Map<String,Object> map);
}
