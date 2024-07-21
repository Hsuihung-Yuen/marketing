package cn.hhy.domain.task.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hhy
 * @description 任务实体对象
 * @create 2024/7/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    /** 活动ID */
    private String userId;

    /** 消息主题 */
    private String topic;

    /** 消息编号 */
    private String messageId;

    /** 消息主体 */
    private String message;
}