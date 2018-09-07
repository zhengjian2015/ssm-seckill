package org.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

public interface SeckillDao {
	
	//如果影响行数>1表示更行行数
	int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);
	
	Seckill queryById(long seckillId);
	
	/*根据编译量查询
	 * */
	List<Seckill> queryAll(@Param("offst") int offset, @Param("limit") int limit);
	
	void killByProcedure(Map<String,Object> map);
}
