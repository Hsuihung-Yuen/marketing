package cn.hhy.infrastructure.persistent.repository;

import cn.hhy.domain.task.model.entity.TaskEntity;
import cn.hhy.domain.task.repository.ITaskRepository;
import cn.hhy.infrastructure.persistent.dao.ITaskDao;
import cn.hhy.infrastructure.persistent.event.EventPublisher;
import cn.hhy.infrastructure.persistent.po.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hhy
 * @description 任务服务仓储实现
 * @create 2024/7/19
 */
@Slf4j
@Repository
public class TaskRepository implements ITaskRepository {

    @Resource
    private ITaskDao taskDao;

    @Resource
    private EventPublisher eventPublisher;

    @Override
    public List<TaskEntity> queryNoSendMessageTaskList() {
        List<Task> tasks = taskDao.queryNoSendMessageTaskList();
        List<TaskEntity> taskEntities = new ArrayList<>(tasks.size());
        for (Task task : tasks) {
            TaskEntity taskEntity=TaskEntity.builder()
                    .userId(task.getUserId())
                    .topic(task.getTopic())
                    .messageId(task.getMessageId())
                    .message(task.getMessage())
                    .build();

            taskEntities.add(taskEntity);
        }
        return taskEntities;
    }

    @Override
    public void sendMessage(TaskEntity taskEntity) {
        eventPublisher.publish(taskEntity.getTopic(), taskEntity.getMessage());
    }

    @Override
    public void updateTaskSendMessageCompleted(String userId, String messageId) {
        Task taskReq = Task.builder()
                .userId(userId)
                .messageId(messageId)
                .build();
        taskDao.updateTaskSendMessageCompleted(taskReq);
    }

    @Override
    public void updateTaskSendMessageFail(String userId, String messageId) {
        Task taskReq = Task.builder()
                .userId(userId)
                .messageId(messageId)
                .build();
        taskDao.updateTaskSendMessageFail(taskReq);
    }

}
