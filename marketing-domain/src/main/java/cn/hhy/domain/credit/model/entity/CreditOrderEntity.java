package cn.hhy.domain.credit.model.entity;

import cn.hhy.domain.credit.model.valobj.TradeNameVO;
import cn.hhy.domain.credit.model.valobj.TradeTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Hhy
 * @description 积分订单实体积分订单实体
 * @create 2024/7/23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditOrderEntity {

    /** 用户ID */
    private String userId;

    /** 订单ID */
    private String orderId;

    /** 交易名称 */
    private TradeNameVO tradeName;

    /** 交易类型；交易类型；forward-正向、reverse-逆向 */
    private TradeTypeVO tradeType;

    /** 交易金额 */
    private BigDecimal tradeAmount;

    /** 业务仿重ID - 外部透传。返利、行为等唯一标识 */
    private String outBusinessNo;

}