package com.lark.validation.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 详见：https://mp.weixin.qq.com/s/rL0n0qW_1eWhPOkqBzmMMw
 * <p>
 * DTO 表示数据传输对象（Data Transfer Object），用于服务器和客户端之间交互传输使用的。在 spring-web 项目中可以表示用于接收请求参数的 Bean 对象。
 * <p>
 * 分组校验
 * 在实际项目中，可能多个方法需要使用同一个 DTO 类来接收参数，而不同方法的校验规则很可能是不一样的。
 * 这个时候，简单地在 DTO 类的字段上加约束注解无法解决这个问题。因此，spring-validation 支持了分组校验的功能，专门用来解决这类问题。
 * <p>
 * 嵌套校验
 * 前面的示例中，DTO 类里面的字段都是基本数据类型和 String 类型。但是实际场景中，有可能某个字段也是一个对象，这种情况先，可以使用嵌套校验。
 * 比如，上面保存 User 信息的时候同时还带有 Job 信息。需要注意的是，此时 DTO 类的对应字段必须标记 @Valid 注解。
 * <p>
 * 嵌套校验可以结合分组校验一起使用。还有就是嵌套集合校验会对集合里面的每一项都进行校验，例如 List<Job> 字段会对这个 list 里面的每一个 Job 对象都进行校验
 */
@Data
public class UserDTO {

    @Min(value = 1000L, groups = {Update.class})
    @Max(value = 2000L, groups = {Update.class})
    private Long userId;

    @NotNull(groups = {Save.class, Update.class})
    @Length(min = 2, max = 10, groups = {Save.class, Update.class})
    private String username;

    @NotNull(groups = {Save.class})
    @Length(min = 6, max = 20, groups = {Save.class})
    private String password;

    @NotNull(groups = {Save.class, Update.class})
    @Valid
    private Job job;

    /**
     * 保存的时候校验分组
     */
    public interface Save {
    }

    /**
     * 更新的时候校验分组
     */
    public interface Update {
    }

}
