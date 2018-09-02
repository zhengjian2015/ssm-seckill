package org.seckill.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring 和juit整合 junit启动时加载springIOC容器
 * @author zhengj
 *
 */

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("classpath:spring/spring-dao.xml")
//告诉junit spring配置文件
public class SeckillDaoTest {
	
	@Resource
	private SeckillDao seckillDao;
	@Test
	public void testQueryById() {
		long id = 1002L;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill);
	}

	@Test
	public void testReduceNumber() {
		long id = 1002L;
		Date killTime = new Date();
		int seckill = seckillDao.reduceNumber(id, killTime);
		System.out.println("updatecount:"+seckill);
	}

	@Test
	public void testQueryAll() {
		List<Seckill> seckills = seckillDao.queryAll(0, 100);
		for(Seckill seckill:seckills) {
			System.out.println(seckill);
		}
	}

}
