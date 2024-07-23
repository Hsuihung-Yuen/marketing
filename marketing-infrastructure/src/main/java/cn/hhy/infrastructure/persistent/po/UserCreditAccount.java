package cn.hhy.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hhy
 * @description 用户积分账户
 * @create 2024/7/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreditAccount {

    /** 自增ID */
    private Long id;

    /** 用户ID */
    private String userId;

    /** 总积分，显示总账户值，包括历史获得的总积分 */
    private BigDecimal totalAmount;

    /** 可用积分，每次扣减的值 */
    private BigDecimal availableAmount;

    /** 账户状态【open - 可用，close - 冻结】 */
    private String accountStatus;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
