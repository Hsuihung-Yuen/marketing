package cn.hhy.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Hhy
 * @description 抽奖策略规则，权重配置，查询N次抽奖可解锁奖品范围
 * @create 2024/7/22
 */
@Data
public class RaffleStrategyRuleWeightRequestDTO implements Serializable {

    /** 用户ID */
    private String userId;

    /** 抽奖活动ID */
    private Long activityId;

}
