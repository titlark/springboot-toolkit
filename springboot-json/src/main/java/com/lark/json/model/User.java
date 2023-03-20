package com.lark.json.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    /**
     * @JsonProperty： 可用来自定义属性标签名称；
     */
    @JsonProperty("user-name")
    private String userName;

    private Long id;

    private Integer age;

    /**
     * @JsonIgnore： 可用来忽略不想输出某个属性的标签；
     */
    @JsonIgnore
    private String address;

    /**
     * @JsonInclude： 可用来动态包含属性的标签，如可以不包含为 null 值的属性；
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String memo;
}
