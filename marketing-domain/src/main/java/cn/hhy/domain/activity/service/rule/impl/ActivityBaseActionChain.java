package cn.hhy.domain.activity.service.rule.impl;

import cn.hhy.domain.activity.model.entity.ActivityCountEntity;
import cn.hhy.domain.activity.model.entity.ActivityEntity;
import cn.hhy.domain.activity.model.entity.ActivitySkuEntity;
import cn.hhy.domain.activity.service.rule.AbstractActionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Hhy
 * @description 活动规则过滤【日期、状态】
 * @create 2024/7/17
 */
@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {
    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {


        return next().action(activitySkuEntity, activityEntity, activityCountEntity);
    }
}
