package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

/*
 * 
 * ҵ��ӿ� վ��ʹ���߽Ƕ�ʹ�ýӿ�
 * �������� ������������ ���� ,�������ͣ���return ����/�쳣��
 *
 */
public interface SeckillService {
	//��ѯ������ɱ��¼
	List<Seckill> getSeckillList();
	
	Seckill getById(long seckillId);
	
	//��ɱ�����������ɱ�ӿڵ�ַ �������ϵͳʱ�����ɱʱ��
	Exposer exportSeckillUrl(long seckillId);
	
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5) throws RepeatKillException,SeckillCloseException,SeckillException;
	
	SeckillExecution executeSeckillProcedure(long seckillId,long userPhone,String md5);
}
