package com.lark.xml.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lark.xml.vo.OrderInfoVO;
import com.lark.xml.vo.UserXmlVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/xml")
public class XmlController {

    @RequestMapping(value = "/hello", produces = MediaType.APPLICATION_XML_VALUE)
    public Object hello() throws JsonProcessingException {
        UserXmlVO userXmlVO = UserXmlVO.builder().name("body").orderList(Arrays.asList(OrderInfoVO.builder().id("1").orderName("电视").build(), OrderInfoVO.builder().id("2").orderName("mac").build())).build();
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(userXmlVO);
    }
}
