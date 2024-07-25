package cn.hhy.domain.activity.service.product;

import cn.hhy.domain.activity.model.entity.SkuProductEntity;
import cn.hhy.domain.activity.repository.IActivityRepository;
import cn.hhy.domain.activity.service.IRaffleActivitySkuProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hhy
 * @description sku商品服务
 * @create 2024/7/24
 */
@Service
public class RaffleActivitySkuProductService implements IRaffleActivitySkuProductService {

    @Resource
    private IActivityRepository repository;

    @Override
    public List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId) {
        return repository.querySkuProductEntityListByActivityId(activityId);
    }

}
