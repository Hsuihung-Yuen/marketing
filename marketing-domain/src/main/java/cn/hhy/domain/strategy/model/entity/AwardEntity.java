package cn.hhy.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hhy
 * @description 策略结果实体
 * @create 2024/7/1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AwardEntity {

    /** 用户ID */
    private String userId;
    /** 奖品ID */
    private Integer awardId;

}