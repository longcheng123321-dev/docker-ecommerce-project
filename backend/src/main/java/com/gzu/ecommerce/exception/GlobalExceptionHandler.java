package com.gzu.ecommerce.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获所有异常，提供统一错误页面
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        // 记录完整错误日志（便于排查问题）
        log.error("系统异常：{}", e.getMessage(), e);

        // 给用户友好的提示（不暴露技术细节）
        String errorMessage = "系统开小差了，请稍后再试~";
        if (e.getMessage() != null && (e.getMessage().contains("库存不足") || e.getMessage().contains("购物车为空"))) {
            errorMessage = e.getMessage(); // 对于业务异常，直接显示明确提示
        }

        model.addAttribute("error", errorMessage);
        return "error"; // 返回 src/main/resources/templates/error.html
    }
}
