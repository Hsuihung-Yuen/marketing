package cn.hhy.infrastructure.persistent.dao;

import cn.hhy.infrastructure.persistent.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hhy
 * @description 规则树表DAO
 * @create 2024/7/8
 */
@Mapper
public interface IRuleTreeDao {
    RuleTree queryRuleTreeByTreeId(String treeId);
}
