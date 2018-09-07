
-- ��ɱִ�д洢����
DELIMITER $$
-- ����洢����
-- ������in ���������out�������
-- rowCount():������һ���޸�����sql��delete,insert,update����Ӱ������
-- rowCount: 0��δ�޸�����  >0����ʾ�޸ĵ����� <0��sql����/δִ���޸�sql

CREATE PROCEDURE excuteSeckill(IN fadeSeckillId INT,IN fadeUserPhone VARCHAR (15),IN fadeKillTime TIMESTAMP ,OUT fadeResult INT)
 BEGIN
    DECLARE insert_count INT DEFAULT 0;
	START TRANSACTION ;
	INSERT ignore success_killed(seckill_id,user_phone,state,create_time) VALUES(fadeSeckillId,fadeUserPhone,0,fadeKillTime); -- �Ȳ��빺����ϸ
	SELECT ROW_COUNT() INTO insert_count;
	IF(insert_count = 0) THEN
      ROLLBACK ;
      SET fadeResult = -1;  -- �ظ���ɱ
    ELSEIF(insert_count < 0) THEN
      ROLLBACK ;
      SET fadeResult = -2;  -- �ڲ�����
	ELSE
		UPDATE seckill SET number = number -1 WHERE seckill_id = fadeSeckillId AND start_time < fadeKillTime AND end_time > fadeKillTime AND number > 0;
      SELECT ROW_COUNT() INTO insert_count;
      IF (insert_count = 0)  THEN
        ROLLBACK ;
        SET fadeResult = 0;  -- ���û���ˣ�������ɱ�Ѿ��ر�
      ELSEIF (insert_count < 0) THEN
        ROLLBACK ;
        SET fadeResult = -2;  -- �ڲ�����
      ELSE
        COMMIT ;    -- ��ɱ�ɹ��������ύ
        SET  fadeResult = 1;  -- ��ɱ�ɹ�����ֵΪ1
      END IF;
END IF;
  END
$$

DELIMITER ;
 
SET @fadeResult = -3;
-- ִ�д洢����
CALL excuteSeckill(1003,18810464493,NOW(),@fadeResult);
-- ��ȡ���
SELECT @fadeResult;
