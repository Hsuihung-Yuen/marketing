package cn.hhy.domain.strategy.service.rule.chain.factory;

import cn.hhy.domain.strategy.model.entity.StrategyEntity;
import cn.hhy.domain.strategy.repository.IStrategyRepository;
import cn.hhy.domain.strategy.service.rule.chain.ILogicChain;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Hhy
 * @description 工厂，责任链的装配并返回
 * @create 2024/7/6
 */
@Service
public class DefaultChainFactory {

    //由于给bean配置了名字因此spring会自动寻找类行为 ILogicChain 的bean
    //并将名字赋予ke，value为bean
    private final Map<String, ILogicChain> logicChainGroup;
    protected IStrategyRepository repository;

    public DefaultChainFactory(Map<String, ILogicChain> logicChainGroup, IStrategyRepository repository) {
        this.logicChainGroup = logicChainGroup;
        this.repository = repository;
    }

    /**
     * 通过策略
     *
     * @param strategyId
     * @return LogicChain 责任链首节点
     */
    public ILogicChain openLogicChain(Long strategyId) {
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();

        // 如果未配置策略规则，则只装填一个默认责任链
        if (null == ruleModels || 0 == ruleModels.length) return logicChainGroup.get(LogicModel.RULE_DEFAULT.getCode());

        // 按照配置顺序装填用户配置的责任链；rule_blacklist、rule_weight 「注意此数据从Redis缓存中获取，如果更新库表，记得在测试阶段手动处理缓存」
        ILogicChain logicChain = logicChainGroup.get(ruleModels[0]);
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain nextChain = logicChainGroup.get(ruleModels[i]);
            current = current.appendNext(nextChain);
        }

        // 责任链的最后装填默认责任链
        current.appendNext(logicChainGroup.get(LogicModel.RULE_DEFAULT.getCode()));

        return logicChain;
    }

    /**
     * 写在 factory 内部方便管理，毕竟只有 factory 相关的在用
     * 也可写在 vo 层
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {

        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;

        /** 抽奖类型；黑名单抽奖、权重规则、默认抽奖 */
        private String logicModel;

        /** 抽奖奖品规则，即具体值 */
        private String awardRuleValue;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }


}
