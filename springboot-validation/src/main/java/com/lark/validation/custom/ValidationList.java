package com.lark.validation.custom;

import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义集合参数校验
 * <p>
 * 如果请求体直接传递了 json 数组给后台，并希望对数组中的每一项都进行参数校验。此时，如果我们直接使用 java.util.Collection 下的 list 或者 set 来接收数据，参数校验并不会生效！我们可以使用自定义 list 集合来接收参数：
 * <p>
 * 包装 List 类型，并声明 @Valid 注解
 *
 * @param <E>
 * @Delegate 注解受 lombok 版本限制，1.18.6 以上版本可支持。如果校验不通过，会抛出 NotReadablePropertyException，同样可以使用统一异常进行处理。
 */
public class ValidationList<E> implements List<E> {

    @Delegate // @Delegate是lombok注解
    @Valid // 一定要加@Valid注解
    public List<E> list = new ArrayList<>();

    // 一定要记得重写toString方法
    @Override
    public String toString() {
        return list.toString();
    }
}