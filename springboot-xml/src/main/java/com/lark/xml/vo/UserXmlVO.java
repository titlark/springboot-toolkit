package com.lark.xml.vo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @JacksonXmlRootElement： 用在类上，用来自定义根节点名称；
 */
@JacksonXmlRootElement(localName = "response")
@Data
@Builder
public class UserXmlVO {

    /**
     * @JacksonXmlProperty： 用在属性上，用来自定义子节点名称；
     */
    @JacksonXmlProperty(localName = "user_name")
    private String name;

    /**
     * @JacksonXmlElementWrapper： 用在属性上，可以用来嵌套包装一层父节点，或者禁用此属性参与 XML 转换。
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "order_info")
    private List<OrderInfoVO> orderList;
}
