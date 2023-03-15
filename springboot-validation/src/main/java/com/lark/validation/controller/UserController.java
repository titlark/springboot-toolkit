package com.lark.validation.controller;

import com.lark.common.response.Result;
import com.lark.validation.custom.ValidationList;
import com.lark.validation.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * 常用校验注解（通过注解的 message 属性自定义校验错误的信息）：
 *
 * @NotNull 值不能为空
 * @Null 值必须为空
 * @Pattern (regx = ） 字符串必须匹配正则表达式
 * @Size (min =, max =) 集合元素数量必须在 min 和 max 之间
 * @CreditCardNumber (ignoreNonDigitCharacters =) 字符串必须是信用卡号（按美国的标准校验）
 * @Email 字符串必须是 email 地址
 * @Length (min =, max =) 字符串长度
 * @NotBlank 字符串必须有字符
 * @NotEmpty 字符串不为 null，集合有元素
 * @Range (min =, max =) 数字范围
 * @SafeHtml 字符串必须是安全的 html
 * @URL 字符串是合法的 URL
 * @AssertFalse 值必须是 false
 * @AssertTrue 值必须是 true
 * @DecimalMax (value =, inclusive =) 值必须小于等于 (inclusive=true)/ 小于 (inclusive=false) value 指定的值，可以注解在字符串类型属性上
 * @DecimalMin (value =, inclusive =) 值必须大于等于 (inclusive=true)/ 大于 (inclusive=false) value 指定的值，可以注解在字符串类型属性上
 * @Digits (integer =, fraction =) 数字格式检查，integer 指定整数部分最大长度，fraction 指定小数部分最大长度
 * @Future 值必须是未来的日期
 * @Past 值必须是过去的日期
 * @Max (value =) 值必须小于等于 value 指定的值，不能注解在字符串类型属性上
 * @Min (value =) 值必须大于等于 value 指定的值，不能注解在字符串类型属性上
 *
 * </p>
 * @Null 被注释的元素必须为 null
 * @NotNull 被注释的元素必须不为 null
 * @NotBlank 检查约束字符串是不是 Null 还有被 Trim 的长度是否大于 0, 只对字符串，且会去掉前后空格.
 * @NotEmpty 检查约束元素不能为 NULL 或者是 EMPTY.
 * @AssertTrue 被注释的元素必须为 true
 * @AssertFalse 被注释的元素必须为 false
 * @Min (value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @Max (value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @DecimalMin (value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @DecimalMax (value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @Size (max, min) 注释的元素的长度必须在指定的范围内，支持字符串和集合
 * @Length (min =, max =) 被注释的字符串的大小必须在指定的范围内
 * @Email 被注释的元素必须是电子邮箱地址
 * @Digits (integer, fraction) 被注释的元素必须是一个数字，其值必须在可接受的范围内
 * @Range (min =, max =) 被注释的元素必须在合适的范围内
 * @Past 被注释的元素必须是一个过去的日期
 * @Future 被注释的元素必须是一个将来的日期
 * @Pattern (value) 被注释的元素必须符合指定的正则表达式
 * @URL(protocol=, host=, port=, regexp=, flags=) 被注释的字符串必须是一个有效的 url
 * @CreditCardNumber 被注释的字符串必须通过 Luhn 校验算法，银行卡，信用卡等号码一般都用 Luhn 计算合法性
 */
@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    @Autowired
    private javax.validation.Validator globalValidator;

    /**
     * POST、PUT 请求一般会使用 requestBody 传递参数，这种情况下，后端使用 DTO 对象进行接收。只要给 DTO 对象加上 @Validated 注解就能实现自动参数校验。
     * <p>
     * 这种情况下，使用 @Valid 和 @Validated 都可以。
     *
     * @param userDTO
     * @return
     */
    @PostMapping("saveUser")
    public Object saveUser(@RequestBody @Validated({UserDTO.Save.class}) UserDTO userDTO) {
        // 校验通过，才会执行业务逻辑处理
        System.out.println("userDTO.getUsername() + \" \" + userDTO.getPassword() = " + userDTO.getUsername() + " " + userDTO.getPassword());
//        int a = 2 / 0;
        userDTO.setUserId(1001L);
        userDTO.setPassword(null);
        return Result.ok(userDTO);
    }

    @PostMapping("updateUser")
    public Object updateUser(@RequestBody @Validated(UserDTO.Update.class) UserDTO userDTO) {
        System.out.println("userDTO.getUserId() + \" \" + userDTO.getUsername() + \" \" + userDTO.getPassword() = " + userDTO.getUserId() + " " + userDTO.getUsername() + " " + userDTO.getPassword());
        userDTO.setUserId(1001L);
        return Result.ok(userDTO);
    }

    /**
     * GET 请求一般会使用 requestParam/PathVariable 传参。如果参数比较多 (比如超过 6 个)，还是推荐使用 DTO 对象接收。
     *
     * @param userId
     * @return
     */
    @GetMapping("{userId}")
    public Object findOne(@PathVariable("userId") @Min(value = 1000L) @Max(value = 2000L) Long userId) {
        System.out.println("userId = " + userId);
        // 校验通过，才会执行业务逻辑处理
        UserDTO dto = new UserDTO();
        dto.setUserId(userId);
        dto.setUsername("xixi");
        return Result.ok(dto);
    }

    /**
     * 自定义集合校验
     *
     * @param userList
     * @return
     */
    @PostMapping("saveList")
    public Result saveList(@RequestBody @Validated(UserDTO.Save.class) ValidationList<UserDTO> userList) {
        // 校验通过，才会执行业务逻辑处理
        return Result.ok().list(Arrays.asList(userList.toArray())).count(userList.size());
    }

    // 编程式校验
    @PostMapping("saveWithCodingValidate")
    public Result saveWithCodingValidate(@RequestBody UserDTO userDTO) {
        Set<ConstraintViolation<UserDTO>> validate = globalValidator.validate(userDTO, UserDTO.Save.class);
        // 如果校验通过，validate为空；否则，validate包含未校验通过项
        if (validate.isEmpty()) {
            // 校验通过，才会执行业务逻辑处理

            userDTO.setUserId(1001L);
            return Result.ok(userDTO);
        } else {
            // 校验失败，做其它逻辑
            return Result.error(getValidateFailedMessage(validate));
        }
    }

    /**
     * 获取校验失败的信息
     *
     * @param validate
     * @return
     */
    private String getValidateFailedMessage(Set<ConstraintViolation<UserDTO>> validate) {
        StringBuilder sb = new StringBuilder();
        Iterator<ConstraintViolation<UserDTO>> it = validate.iterator();
        int i = 0;
        while (it.hasNext()) {
            ConstraintViolation<UserDTO> violation = it.next();
            sb.append(violation.getPropertyPath() + ":" + violation.getMessage() + (i == validate.size() - 1 ? "" : ", "));
            i++;
        }
        return sb.toString();
    }

}
