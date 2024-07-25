package com.jvzi.xingdada.manager;

import com.jvzi.xingdada.common.ErrorCode;
import com.jvzi.xingdada.exception.BusinessException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用AI调用
 *
 * @Author jvzi
 * @Date 2024/7/25 19:39
 * @Version 1.0
 */
@Component
public class AiManager {
    @Resource
    private ClientV4 clientV4;

    //稳定的随机数
    private static final float STABLE_TEMPERATURE = 0.05f;

    //不稳定的随机数
    private static final float UNSTABLE_TEMPERATURE = 0.99f;

    /**
     * 同步调用（答案较随意）
     *
     * @param sysMessages  系统消息
     * @param userMessages 用户消息
     * @return
     */
    public String doSyncStableRequest(String sysMessages,String userMessages) {
        return doSyncRequest(sysMessages,userMessages, STABLE_TEMPERATURE);
    }

    /**
     * 同步调用（答案较随意）
     *
     * @param sysMessages  系统消息
     * @param userMessages 用户消息
     * @return
     */
    public String doSyncUnstableRequest(String sysMessages,String userMessages) {
        return doSyncRequest(sysMessages,userMessages, UNSTABLE_TEMPERATURE);
    }

    /**
     * 通用同步请求
     *
     * @param sysMessages  系统消息
     * @param userMessages 用户消息
     * @param temperature  随机数参数
     * @return
     */
    public String doSyncRequest(String sysMessages,String userMessages,Float temperature) {

        return doRequest(sysMessages,userMessages, Boolean.FALSE, temperature);
    }

    /**
     * 通用请求(简化消息队列）
     *
     * @param sysMessages  系统消息
     * @param userMessages 用户消息
     * @param stream  是否流式
     * @param temperature  随机数参数
     * @return
     */
    public String doRequest(String sysMessages,String userMessages, Boolean stream, Float temperature) {
        // 构造请求
        List<ChatMessage> chatMessagelist = new ArrayList<>();
        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),sysMessages);
        chatMessagelist.add(systemChatMessage);
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessages);
        chatMessagelist.add(userChatMessage);
        return doRequest(chatMessagelist, stream, temperature);
    }

    /**
     * 通用请求
     *
     * @param messages
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(List<ChatMessage> messages, Boolean stream, Float temperature) {
        // 构造请求
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(stream)
                .invokeMethod(Constants.invokeMethod)
                .temperature(temperature)
                .messages(messages)
                .build();
        try {
            ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
            return invokeModelApiResp.getData().getChoices().get(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new BusinessException(ErrorCode.SYSTEM_ERROR,e.getMessage());

        }

    }
}
