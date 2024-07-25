package cn.hhy.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Hhy
 * @description 用户活动账户请求对象
 * @create 2024/7/22
 */

@Data
public class UserActivityAccountRequestDTO implements Serializable {

    /** 用户ID */
    private String userId;

    /** 活动ID */
    private Long activityId;
}
