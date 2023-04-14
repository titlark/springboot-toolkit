package com.lark.execl.listener;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 转换器
 */
public class StringConverter implements Converter<String> {

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 将 excel 对象转成 java 对象，这里读的时候会调用
     *
     * @param cellData             NotNull
     * @param excelContentProperty Nullable
     * @param globalConfiguration  NotNull
     * @return
     * @throws Exception
     */
    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return "自定义：" + cellData.getStringValue();
    }

    /**
     * 将 Java 对象转成 String 对象，写出的时候调用
     *
     * @param value
     * @param excelContentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(value);
    }
}
