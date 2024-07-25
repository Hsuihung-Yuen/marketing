package cn.hhy.test.domain.credit;

import cn.hhy.domain.credit.model.entity.TradeEntity;
import cn.hhy.domain.credit.model.valobj.TradeNameVO;
import cn.hhy.domain.credit.model.valobj.TradeTypeVO;
import cn.hhy.domain.credit.service.ICreditAdjustService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

/**
 * @author Hhy
 * @description 积分额度增加服务测试
 * @create 2024/7/23
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CreditAdjustServiceTest {

    @Resource
    private ICreditAdjustService creditAdjustService;

    @Test
    public void test_createOrder_forward() {
        TradeEntity tradeEntity=TradeEntity.builder()
                .userId("hhy")
                .tradeName(TradeNameVO.REBATE)
                .tradeType(TradeTypeVO.FORWARD)
                .amount(new BigDecimal("10.19"))
                .outBusinessNo("10000990991")
                .build();
        creditAdjustService.createOrder(tradeEntity);
    }

    @Test
    public void test_createOrder_reverse() {
        TradeEntity tradeEntity=TradeEntity.builder()
                .userId("hhy")
                .tradeName(TradeNameVO.REBATE)
                .tradeType(TradeTypeVO.FORWARD)
                .amount(new BigDecimal("-10.19"))
                .outBusinessNo("20000990991")
                .build();
        creditAdjustService.createOrder(tradeEntity);
    }

    @Test
    public void test_createOrder_pay() throws InterruptedException {
        TradeEntity tradeEntity = new TradeEntity();
        tradeEntity.setUserId("hhy");
        tradeEntity.setTradeName(TradeNameVO.CONVERT_SKU);
        tradeEntity.setTradeType(TradeTypeVO.REVERSE);
        tradeEntity.setAmount(new BigDecimal("-1.68"));
        tradeEntity.setOutBusinessNo("70009240609111");
        creditAdjustService.createOrder(tradeEntity);

        new CountDownLatch(1).await();
    }
}
