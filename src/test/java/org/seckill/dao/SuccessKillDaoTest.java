package org.seckill.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKillDaoTest {

	@Resource
	private SuccessKillDao successKillDao;
	
	@Test
	public void testInsertSuccesskilled() {
		long id = 1000L;
		long phone = 1382212232L;
		int count = successKillDao.insertSuccesskilled(id, phone);
		System.out.println("count:"+count);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		long id = 1000L;
		long phone = 1382212232L;
		SuccessKilled  successKilled = successKillDao.queryByIdWithSeckill(id, phone);
		System.out.println(successKilled);
	}

}
