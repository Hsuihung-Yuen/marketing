package cn.hhy.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Hhy
 * @description 规则树对象【因为不需要改变数据库结果，因此定义为值对象，不具有唯一id】
 * @create 2024/7/7
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTreeVO {

    /** 规则树ID */
    private String treeId;

    /** 规则树名称 */
    private String treeName;

    /** 规则树描述 */
    private String treeDesc;

    /** 规则根节点key */
    private String treeRootRuleNode;

    /** 规则节点 */
    private Map<String, RuleTreeNodeVO> treeNodeMap;

}
