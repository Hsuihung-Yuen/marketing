package cn.hhy.domain.activity.service;

import cn.hhy.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

/**
 * @author Hhy
 * @description
 * @create 2024/7/16
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity{

    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }
}
