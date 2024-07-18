package cn.hhy.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hhy
 * @description 参与抽奖活动实体对象
 * @create 2024/7/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartakeRaffleActivityEntity {

    /** 用户ID */
    private String userId;

    /** 活动ID */
    private Long activityId;

}
