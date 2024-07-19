package cn.hhy.domain.task.repository;

import cn.hhy.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @author Hhy
 * @description 任务服务仓储接口
 * @create 2024/7/19
 */
public interface ITaskRepository {

    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageCompleted(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);
}
