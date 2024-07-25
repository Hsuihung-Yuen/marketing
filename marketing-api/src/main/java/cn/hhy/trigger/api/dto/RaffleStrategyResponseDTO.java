package cn.hhy.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Hhy
 * @description 抽奖应答结果
 * @create 2024/7/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleStrategyResponseDTO implements Serializable {

    /** 奖品ID */
    private Integer awardId;

    /** 排序编号【策略奖品配置的奖品顺序编号 sort 】*/
    private Integer awardIndex;

}
