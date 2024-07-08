package cn.hhy.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Hhy
 * @description 规则树线实体类
 * @create 2024/7/8
 */
@Data
public class RuleTreeNodeLine {

    /** 自增ID */
    private Long id;

    /** 规则树ID */
    private String treeId;

    /** 规则Key节点 From */
    private String ruleNodeFrom;

    /** 规则Key节点 To */
    private String ruleNodeTo;

    /** 限定类型；见枚举类 RuleLimitTypeVO 定义的值 */
    private String ruleLimitType;

    /** 限定值（到下个节点） */
    private String ruleLimitValue;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
