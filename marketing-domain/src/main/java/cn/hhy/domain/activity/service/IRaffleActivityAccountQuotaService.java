package cn.hhy.domain.activity.service;

import cn.hhy.domain.activity.model.entity.SkuRechargeEntity;

/**
 * @author Hhy
 * @description 抽奖活动订单接口
 * @create 2024/7/16
 */
public interface IRaffleActivityAccountQuotaService {

    /**
     * 创建 sku 账户充值订单，给用户增加抽奖次数
     * 1. 在【打卡、签到、分享、对话、积分兑换】等行为动作下，创建出活动订单，给用户的活动账户【日、月】充值可用的抽奖次数。
     * 2. 对于用户首次进来就有一次，则是依赖于运营配置的动作，在前端页面上。用户点击后，可以获得一次抽奖次数。
     *
     * @param skuRechargeEntity 活动商品充值实体对象
     * @return 活动ID
     */
    String createOrder(SkuRechargeEntity skuRechargeEntity);

    /**
     * 查询活动账户 - 每日参与次数
     *
     * @param activityId 活动ID
     * @param userId     用户ID
     * @return 参与次数
     */
    Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId);


}