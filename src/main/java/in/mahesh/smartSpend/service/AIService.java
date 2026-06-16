package in.mahesh.smartSpend.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

    @Service
    public class AIService {

        private final ChatClient chatClient;

        public AIService(ChatClient.Builder builder) {
            this.chatClient = builder.build();
        }

        public String getFinancialAdvice(String prompt) {
            return chatClient.prompt(prompt)
                    .call()
                    .content();
        }
    }

