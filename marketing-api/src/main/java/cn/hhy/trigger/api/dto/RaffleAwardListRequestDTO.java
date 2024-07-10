package cn.hhy.trigger.api.dto;

import lombok.Data;

/**
 * @author Hhy
 * @description 获取抽奖奖品列表的请求对象
 * @create 2024/7/10
 */
@Data
public class RaffleAwardListRequestDTO {

    //抽奖策略ID
    private Long strategyId;
}
