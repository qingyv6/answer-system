package com.jvzi.xingdada.model.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * AI生成题目请求
 *
 * @author <a href="https://github.com/qingyv6">橘子</a>
 */
@Data
public class AiGenerateQuestionRequest implements Serializable {

    /**
     * 应用id
     */
    private Long appId;
    /**
     * 题目数量
     */
    int questionNumber = 10;
    /**
     * 选项个数
     */
    int optionNumber = 2;

    private static final long serialVersionUID = 1L;

}
