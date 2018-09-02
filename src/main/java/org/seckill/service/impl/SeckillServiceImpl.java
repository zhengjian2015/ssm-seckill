package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKillDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

//@Component @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService{

	private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);
	
	private final String salt="saaa2333(((9cxx";
	
	//ע��service����
	@Resource
	private SeckillDao seckillDao;
	
	@Resource
	private SuccessKillDao successKillDao;
	
	@Override
	public List<Seckill> getSeckillList() {
		// TODO Auto-generated method stub
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		// TODO Auto-generated method stub
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		// TODO Auto-generated method stub
		
		Seckill seckill = seckillDao.queryById(seckillId);
		if(seckill == null) {
			return new Exposer(false,seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		//ϵͳ��ǰʱ��
		Date nowTime = new Date();
		if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
		}
		//ת���ض��ַ����Ĺ��̣�������
		String md5 = getMd5(seckillId);
		return new Exposer(true,md5,seckillId);
	}

	private String getMd5(long seckillId) {
		String base = seckillId +"/"+salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
	@Override
	@Transactional
	/*ʹ��ע��������﷽��������
	 * 1�������ŶӴ��һ��Լ������ȷ��ע���񷽷��ı�̷��
	 * 2����֤���񷽷���ִ��ʱ�価���̣ܶ���Ҫ��������������� Http�������� �����񷽷��ⲿ 
	 * 3���������еķ�������Ҫ������ֻ��һ���޸Ĳ�����ֻ����������Ҫ�������
	 * @see org.seckill.service.SeckillService#executeSeckill(long, long, java.lang.String)
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws RepeatKillException, SeckillCloseException, SeckillException {
		// TODO Auto-generated method stub
		if(md5 == null || !md5.equals(getMd5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		//ִ����ɱ�߼�
		Date nowTime = new Date();
		try {
			int updateCoint = seckillDao.reduceNumber(seckillId, nowTime);
			if(updateCoint <= 0) {
				//û�и��¿��
				throw new SeckillCloseException("Seckill close");
			} else {
				//��¼������Ϊ
				int inserCount = successKillDao.insertSuccesskilled(seckillId, userPhone);
				if(inserCount <= 0) {
					throw new RepeatKillException("seckill repeat");
				} else {
					//��ɱ�ɹ�
					SuccessKilled successKilled = successKillDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		} 
		catch (SeckillCloseException e1){
			throw e1;
		}
		catch (RepeatKillException e2) {
			throw e2;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
			//���б������쳣 ת��Ϊ �������쳣
			throw new SeckillException("seckill inner error:"+e.getMessage());
		}
	}
	
	
	
}
