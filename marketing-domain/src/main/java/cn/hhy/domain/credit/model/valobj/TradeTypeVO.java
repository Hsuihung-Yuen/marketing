package cn.hhy.domain.credit.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hhy
 * @description 交易类型枚举值
 * @create 2024/7/23
 */
@Getter
@AllArgsConstructor
public enum TradeTypeVO {

    FORWARD("forward", "正向交易，+ 积分"),
    REVERSE("reverse", "逆向交易，- 积分"),

    ;

    private final String code;
    private final String info;

}
