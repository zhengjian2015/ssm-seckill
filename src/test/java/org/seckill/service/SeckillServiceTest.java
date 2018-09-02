package org.seckill.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.qos.logback.core.util.Loader;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {

	private final static Logger logger = LoggerFactory.getLogger(SeckillServiceTest.class);
	
	@Autowired
	private SeckillService seckillService;
	
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}",list);
	}

	@Test
	public void testGetById() {
		long id = 1000L;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}",seckill);
	}

	@Test
	public void testtSeckillLogic() {
		long seckillId = 1000;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		logger.info("isExposed={}",exposer.isExposed());
		if(exposer.isExposed()) {
			logger.info("exposer={}",exposer);
			String md5 = exposer.getMd5();
			long userPhone = 13801927805L;
			try {
				SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
				logger.info("seckillExecution={}",seckillExecution);
			} catch (SeckillCloseException e){
				logger.error(e.getMessage());
			}
			catch (RepeatKillException e) {
				logger.error(e.getMessage());
			}
			catch (SeckillException e) {
				logger.error(e.getMessage());
			}
		} else {
			//ÃëÉ±Î´¿ªÆô
			logger.warn("exposer={}",exposer);
		}
	}

	

}
