package cn.hhy.test.domain.activity;

import cn.hhy.domain.activity.model.entity.SkuRechargeEntity;
import cn.hhy.domain.activity.model.entity.UnpaidActivityOrderEntity;
import cn.hhy.domain.activity.model.valobj.OrderTradeTypeVO;
import cn.hhy.domain.activity.service.IRaffleActivityAccountQuotaService;
import cn.hhy.domain.activity.service.armory.IActivityArmory;
import cn.hhy.types.exception.AppException;
import com.alibaba.fastjson2.JSON;
import io.micrometer.core.annotation.TimedSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author Hhy
 * @description 抽奖活动订单单测
 * @create 2024/7/16
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityAccountQuotaServiceTest {

    @Resource
    private IRaffleActivityAccountQuotaService raffleOrder;

    @Resource
    private IActivityArmory activityArmory;

    @Test
    public void setUp() {
        log.info("装配活动：{}", activityArmory.assembleActivitySku(9011L));
    }

    @Test
    public void test_createSkuRechargeOrder_duplicate() {
        SkuRechargeEntity skuRechargeEntity = SkuRechargeEntity.builder()
                .userId("hhy")
                .sku(9011L)
                .outBusinessNo("700091009111")
                .orderTradeType(OrderTradeTypeVO.rebate_no_pay_trade)
                .build();
        UnpaidActivityOrderEntity unpaidActivityOrder = raffleOrder.createOrder(skuRechargeEntity);
        log.info("测试结果：{}", JSON.toJSONString(unpaidActivityOrder));
    }

    /**
     * 测试库存消耗和最终一致更新
     * 1. raffle_activity_sku 库表库存可以设置20个
     * 2. 清空 redis 缓存 flushall
     * 3. for 循环20次，消耗完库存，最终数据库剩余库存为0
     */
    @Test
    public void test_createSkuRechargeOrder() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            try {
                SkuRechargeEntity skuRechargeEntity = SkuRechargeEntity.builder()
                        .userId("xiaofuge")
                        .sku(9011L)
                        .outBusinessNo(RandomStringUtils.randomNumeric(12))
                        .orderTradeType(OrderTradeTypeVO.rebate_no_pay_trade)
                        .build();
                UnpaidActivityOrderEntity orderId = raffleOrder.createOrder(skuRechargeEntity);
                log.info("测试结果：{}", JSON.toJSONString(orderId));
            } catch (AppException e) {
                log.warn(e.getInfo());
            }
        }
        //多线程环境下等待计数器一次
        new CountDownLatch(1).await();
    }

    @Test
    public void test_credit_pay_trade() {
        SkuRechargeEntity skuRechargeEntity = SkuRechargeEntity.builder()
                .userId("xiaofuge")
                .sku(9011L)
                .outBusinessNo("70009240609111")
                .orderTradeType(OrderTradeTypeVO.credit_pay_trade)
                .build();
        UnpaidActivityOrderEntity orderId = raffleOrder.createOrder(skuRechargeEntity);
        log.info("测试结果：{}", JSON.toJSONString(orderId));
    }

}
