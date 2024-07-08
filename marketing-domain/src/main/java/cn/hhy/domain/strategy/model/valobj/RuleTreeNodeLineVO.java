package cn.hhy.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hhy
 * @description 规则树节点指向的线对象。用于衔接 from->to 节点链路关系
 * @create 2024/7/7
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTreeNodeLineVO {

    /** 规则树ID */
    private String treeId;

    /** 规则Key节点 From */
    private String ruleNodeFrom;

    /** 规则Key节点 To */
    private String ruleNodeTo;

    /** 限定类型；见枚举类 RuleLimitTypeVO 定义的值 */
    private RuleLimitTypeVO ruleLimitType;

    /** 限定值（到下个节点）如放行则代表是左节点，拦截是右节点 */
    private RuleLogicCheckTypeVO ruleLimitValue;

}
