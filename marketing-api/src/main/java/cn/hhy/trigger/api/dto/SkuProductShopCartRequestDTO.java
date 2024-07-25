package cn.hhy.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hhy
 * @description 商品"购物车"请求对象
 * @create 2024/7/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuProductShopCartRequestDTO {

    /** 用户ID */
    private String userId;

    /** sku 商品 */
    private Long sku;

}