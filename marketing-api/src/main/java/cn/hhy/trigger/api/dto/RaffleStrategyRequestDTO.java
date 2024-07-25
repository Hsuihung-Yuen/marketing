package cn.hhy.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Hhy
 * @description 抽奖请求参数
 * @create 2024/7/10
 */
@Data
public class RaffleStrategyRequestDTO implements Serializable {

    /** 抽奖策略ID */
    private Long strategyId;

}
