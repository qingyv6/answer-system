package com.jvzi.xingdada.utils;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author jvzi
 * @Date 2024/7/25 17:13
 * @Version 1.0
 */
@SpringBootTest
public class ZhiPuTest {

    @Resource
    private ClientV4 clientV4;

    @Test
    public void test() {
        // 构造请求
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "作为一名营销专家，请为智谱开放平台创作一个吸引人的slogan");
        messages.add(chatMessage);
        String requestId = String.valueOf(System.currentTimeMillis());
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                //是否是流式
                .stream(Boolean.FALSE)
                //随机性参数
                .temperature(0.5f)
                .invokeMethod(Constants.invokeMethod)
                //消息列表
                .messages(messages)
                .requestId(requestId)
                .build();
        // 调用
        ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
        System.out.println("model output:" + invokeModelApiResp.getMsg());
    }

}
