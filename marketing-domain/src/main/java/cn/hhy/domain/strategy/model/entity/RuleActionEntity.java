package cn.hhy.domain.strategy.model.entity;

import cn.hhy.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import lombok.*;

/**
 * @author Hhy
 * @description
 * @create 2024/7/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleActionEntity <T extends RuleActionEntity.RaffleEntity>{

    private String code = RuleLogicCheckTypeVO.ALLOW.getCode();
    private String info = RuleLogicCheckTypeVO.ALLOW.getInfo();
    private String ruleModel;
    private T data;

    //作为一个规范，抽奖前中后规则的父类
    static public class RaffleEntity {
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class RaffleBeforeEntity extends RaffleEntity {

        /** 策略ID */
        private Long strategyId;

        /** 权重值Key；用于抽奖时可以选择权重抽奖。*/
        private String ruleWeightValueKey;

        /** 奖品ID；抽奖前还设奖品id主要为了黑名单直接返回这个奖品，不需要继续下去 */
        private Integer awardId;
    }

    // 抽奖之中
    static public class RaffleCenterEntity extends RaffleEntity {

    }

    // 抽奖之后
    static public class RaffleAfterEntity extends RaffleEntity {

    }
}
