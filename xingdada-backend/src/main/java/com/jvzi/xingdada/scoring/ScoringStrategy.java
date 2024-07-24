package com.jvzi.xingdada.scoring;

import com.jvzi.xingdada.model.entity.App;
import com.jvzi.xingdada.model.entity.UserAnswer;

import java.util.List;

/**
 * 评分策略
 *
 * @author <a href="https://github.com/qingyv6">橘子</a>
 *
 */
public interface ScoringStrategy {

    /**
     * 执行评分
     *
     * @param choices
     * @param app
     * @return
     * @throws Exception
     */
    UserAnswer doScore(List<String> choices, App app) throws Exception;
}