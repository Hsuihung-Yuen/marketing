package cn.hhy.domain.strategy.service.rule.chain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hhy
 * @description 责任链父类，判断走哪种策略。
 * @create 2024/7/6
 */
@Slf4j
public abstract class AbstractLogicChain implements ILogicChain{

    private ILogicChain next;

    @Override
    public ILogicChain next() {
        return next;
    }

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }

    protected abstract String ruleModel();

}
