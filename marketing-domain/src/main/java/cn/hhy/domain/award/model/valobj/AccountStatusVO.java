package cn.hhy.domain.award.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hhy
 * @description 账户状态枚举
 * @create 2024/7/23
 */
@Getter
@AllArgsConstructor
public enum AccountStatusVO {

    open("open", "开启"),
    close("close", "冻结"),
    ;

    private final String code;
    private final String desc;

}
