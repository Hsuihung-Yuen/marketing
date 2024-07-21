package cn.hhy.domain.rebate.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hhy
 * @description 返利类型
 * @create 2024/7/21
 */
@Getter
@AllArgsConstructor
public enum RebateTypeVO {

    SKU("sku", "活动库存充值商品"),
    INTEGRAL("integral", "用户活动积分"),
    ;

    private final String code;
    private final String info;

}
