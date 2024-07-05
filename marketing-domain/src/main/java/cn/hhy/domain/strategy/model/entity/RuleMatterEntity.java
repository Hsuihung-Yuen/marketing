package cn.hhy.domain.strategy.model.entity;

import lombok.Data;

/**
 * @author Hhy
 * @description 规则物料实体对象，可以理解为规则本身；同时为规则逻辑过滤器的入参
 * @create 2024/7/5
 */
@Data
public class RuleMatterEntity {

    /** 用户ID */
    private String userId;

    /** 策略ID */
    private Long strategyId;

    /** 抽奖奖品ID【规则类型为策略，则不需要奖品ID】 */
    private Integer awardId;

    /** 抽奖奖品规则类型【rule_random - 随机值计算、rule_lock - 抽奖几次后解锁、rule_luck_award - 幸运奖(兜底奖品)】 */
    /** 抽奖策略规则类型 【rule_weight - 规则权重、rule_blacklist - 黑名单】 */
    private String ruleModel;

}
