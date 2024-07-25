package cn.hhy.test.domain.award;

import cn.hhy.domain.award.model.entity.DistributeAwardEntity;
import cn.hhy.domain.award.model.entity.UserAwardRecordEntity;
import cn.hhy.domain.award.model.valobj.AwardStateVO;
import cn.hhy.domain.award.service.IAwardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author Hhy
 * @description 奖品服务测试
 * @create 2024/7/19
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardServiceTest {

    @Resource
    private IAwardService awardService;

    /**
     * 模拟发放抽奖记录，流程中会发送MQ，以及接收MQ消息，还有 task 表，补偿发送MQ
     */
    @Test
    public void test_saveUserAwardRecord() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            UserAwardRecordEntity userAwardRecordEntity = UserAwardRecordEntity.builder()
                    .userId("hhy")
                    .activityId(100301L)
                    .strategyId(100006L)
                    .orderId(RandomStringUtils.randomNumeric(12))
                    .awardId(101)
                    .awardTitle("OpenAI 增加使用次数")
                    .awardTime(new Date())
                    .awardState(AwardStateVO.create)
                    .build();
            awardService.saveUserAwardRecord(userAwardRecordEntity);
            Thread.sleep(500);
        }

        new CountDownLatch(1).await();
    }

    @Test
    public void test_distributeAward() throws InterruptedException {
        DistributeAwardEntity distributeAwardEntity=DistributeAwardEntity.builder()
                .userId("hhy")
                .orderId("690124733440")
                .awardId(101)
                .awardConfig("0.01,1")
                .build();
        awardService.distributeAward(distributeAwardEntity);
    }

}
