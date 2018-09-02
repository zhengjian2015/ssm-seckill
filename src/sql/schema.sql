---数据库初始化脚本

--创建数据库
CREATE DATABASE seckill;

--使用数据库
use seckill;
CREATE TABLE seckill(
 seckill_id  bigint NOT NULl AUTO_INCREMENT COMMENT '商品库存id',
 name varchar(120) NOT NULL COMMENT '商品名称',
 number int NOT NULL COMMENT '库存数量',
 start_time timestamp NOT NULL COMMENT  '秒杀开启时间',
 end_time timestamp NOT NULL COMMENT '秒杀结束时间',
 create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',

PRIMARY KEY (`seckill_id`),
 key idx_start_time(start_time),
 key idx_end_time(end_time),
 key idx_create_time(create_time)
 ) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表'
 
 --初始化数据
insert into seckill(name,number,start_time,end_time)
values ('1000元秒杀iphone8',100,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
 ('500元秒杀ipad5',100,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
('300元秒杀红米4',100,'2018-11-01 00:00:00','2018-11-02 00:00:00'),
('100元秒杀锤子',100,'2018-11-01 00:00:00','2018-11-02 00:00:00');

 --秒杀成功明细表
 CREATE TABLE success_killed(
 seckill_id  bigint NOT NULl AUTO_INCREMENT COMMENT '商品库存id',
 user_phone  bigint NOT NULL COMMENT '用户手机号码',
 state       tinyint NOT NULL DEFAULT -1 COMMENT '-1无效 0 成功 1已付款',
 create_time timestamp NOT NULL COMMENT '创建时间',
PRIMARY KEY(seckill_id,user_phone), /*联合主键*/
 
 key idx_create_time(create_time)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表'




 