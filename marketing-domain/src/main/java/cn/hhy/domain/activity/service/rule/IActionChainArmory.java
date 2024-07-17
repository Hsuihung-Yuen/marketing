package cn.hhy.domain.activity.service.rule;

/**
 * @author Hhy
 * @description 责任链装配接口
 * @create 2024/7/17
 */
public interface IActionChainArmory {

    IActionChain next();

    IActionChain appendNext(IActionChain next);

}
