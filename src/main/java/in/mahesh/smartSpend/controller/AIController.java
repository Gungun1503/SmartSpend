package in.mahesh.smartSpend.controller;

import in.mahesh.smartSpend.service.AIService;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/ai")
    @CrossOrigin(origins = "*")
    public class AIController {

        private final AIService aiService;

        public AIController(AIService aiService) {
            this.aiService = aiService;
        }

        @PostMapping("/insight")
        public String getInsight(@RequestBody String prompt) {
            return aiService.getFinancialAdvice(prompt);
        }

        @GetMapping("/test")
        public String test() {
            return "AI Controller Working";
        }
    }
