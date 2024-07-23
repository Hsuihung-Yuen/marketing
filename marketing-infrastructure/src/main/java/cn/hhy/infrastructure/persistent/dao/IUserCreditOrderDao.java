package cn.hhy.infrastructure.persistent.dao;

import cn.hhy.infrastructure.persistent.po.UserCreditOrder;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hhy
 * @description 用户积分流水单 DAO
 * @create 2024/7/23
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserCreditOrderDao {

    void insert(UserCreditOrder userCreditOrderReq);

}
