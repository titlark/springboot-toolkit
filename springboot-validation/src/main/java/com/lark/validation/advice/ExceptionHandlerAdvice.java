package com.lark.validation.advice;

import com.lark.common.code.ErrorCode;
import com.lark.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 统一异常处理
 * <p>
 * 关于 @RestControllerAdvice 的几点说明：
 *
 * @RestControllerAdvice 注解包含了 @Component 注解，会把被注解的类作为组件交给 Spring 来管理。
 * @RestControllerAdvice 注解包含了 @ResponseBody 注解，异常处理完之后给调用方输出一个 JSON 格式的封装数据。
 * @RestControllerAdvice 注解有一个 basePackages 属性，该属性用来拦截哪个包中的异常信息，一般不指定，拦截项目工程中的所有异常。
 * 在方法上通过 @ExceptionHandler 注解来指定具体的异常，在方法中处理该异常信息，最后将结果通过统一的 JSON 结构体返回给调用者。
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 在有必要的时候，可以使用 @InitBinder 在类中进行全局的配置
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
//        binder.setFieldDefaultPrefix("abc");
    }

    /**
     * @param model
     * @ModelAttribute 配置与视图相关的参数
     */
    @ModelAttribute
    public void modelAttribute(Model model) {
//        model.addAttribute("aaa", "xxxxx");
    }

    /**
     * 处理参数校验失败的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder("校验失败:");
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (int i = 0; i < fieldErrors.size(); i++) {
            FieldError fieldError = fieldErrors.get(i);
            if (i == fieldErrors.size() - 1) {
                sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage());
            } else {
                sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
            }

        }
        String msg = sb.toString();
        log.error(msg);
        return Result.error(ErrorCode.ERROR.getCode(), msg);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage());
        return Result.error(ErrorCode.ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public Result handleException(Exception e) {
        log.error(e.getMessage());
        return Result.error(ErrorCode.ERROR.getCode(), e.getMessage());
    }
}
