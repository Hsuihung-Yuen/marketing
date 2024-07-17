package cn.hhy.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hhy
 * @description 活动sku库存 key 值对象
 * @create 2024/7/17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySkuStockKeyVO {

    /** 商品sku */
    private Long sku;

    /** 活动ID */
    private Long activityId;
}
