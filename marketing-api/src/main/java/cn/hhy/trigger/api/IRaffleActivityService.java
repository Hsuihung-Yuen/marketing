package cn.hhy.trigger.api;

import cn.hhy.trigger.api.dto.ActivityDrawRequestDTO;
import cn.hhy.trigger.api.dto.ActivityDrawResponseDTO;
import cn.hhy.types.model.Response;

/**
 * @author Hhy
 * @description 抽奖活动服务
 * @create 2024/7/20
 */
public interface IRaffleActivityService {

    /**
     * 活动装配，数据预热缓存
     * @param activityId 活动ID
     * @return 装配结果
     */
    Response<Boolean> armory(Long activityId);

    /**
     * 活动抽奖接口
     * @param request 请求对象
     * @return 返回结果
     */
    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);

}
