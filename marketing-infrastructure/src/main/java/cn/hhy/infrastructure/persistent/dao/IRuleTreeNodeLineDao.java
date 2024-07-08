package cn.hhy.infrastructure.persistent.dao;

import cn.hhy.infrastructure.persistent.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Hhy
 * @description 规则树节点连线表DAO
 * @create 2024/7/8
 */
@Mapper
public interface IRuleTreeNodeLineDao {

    List<RuleTreeNodeLine> queryRuleTreeNodeLineListByTreeId(String treeId);

}
