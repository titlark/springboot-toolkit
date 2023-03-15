package com.lark.validation.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lark.common.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一包装处理
 * <p>
 * ResponseBodyAdvice 是对 Controller 返回的内容在 HttpMessageConverter 进行类型转换之前拦截，进行相应的处理操作后，再将结果返回给客户端。那这样就可以把统一包装的工作放到这个类里面。
 * <p>
 * supports：判断是否要交给 beforeBodyWrite 方法执行，ture：需要；false：不需要
 * beforeBodyWrite：对 response 进行具体的处理
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 重写 supports 方法，也就是说，当返回类型已经是 Result 了，那就不需要封装了，当不等与 Result 时才进行调用 beforeBodyWrite 方法，跟过滤器的效果是一样的
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果不需要进行封装的，可以添加一些校验手段，比如添加标记排除的注解
//        return true;
        // response 是 Result 类型，或者注释了 NotControllerResponseAdvice 都不进行包装
        return !(returnType.getParameterType().isAssignableFrom(Result.class) || returnType.hasMethodAnnotation(NotControllerResponseAdvice.class));
    }

    /**
     * 处理 cannot be cast to java.lang.String 问题
     *
     * 最后重写我们的封装方法 beforeBodyWrite，注意除了 String 的返回值有点特殊，无法直接封装成 json，我们需要进行特殊处理，其他的直接 Result.ok(body); 就 ok 了
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 提供一定的灵活度，如果body已经被包装了，就不进行包装
        /*if (body instanceof Result) {
            return body;
        }
        // 如果返回值是String类型，那就手动把Result对象转换成JSON字符串
        if (body instanceof String) {
            try {
                return this.objectMapper.writeValueAsString(Result.ok(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return Result.ok(body);*/

        /*** 使用上面的方式和下面的方式都ok ***/

        // String类型不能直接包装
//        if (returnType.getGenericParameterType().equals(String.class)) {
        if (body instanceof String) {
//            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在 Result 里后转换为json串进行返回
                return objectMapper.writeValueAsString(Result.ok(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        // 否则直接包装成 Result 返回
        return Result.ok(body);
    }
}
