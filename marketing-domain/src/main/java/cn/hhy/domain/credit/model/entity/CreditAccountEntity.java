package cn.hhy.domain.credit.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Hhy
 * @description 积分账户实体
 * @create 2024/7/23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditAccountEntity {

    /** 用户ID */
    private String userId;

    /** 可用积分，每次扣减的值 */
    private BigDecimal adjustAmount;

}