package org.seckill.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlogAction{
    //����һ��ȫ�ֵļ�¼����ͨ��LoggerFactory��ȡ
   private final static Logger logger = LoggerFactory.getLogger(BlogAction.class);
   public static void main(String[] args) {
       logger.info("logback �ɹ���");
       logger.error("logback �ɹ���");
   }
}
