package com.lark.execl.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerDailyImport {
    /**
     * 客户名称
     */
    @ExcelProperty(index = 0)
    private String customerName;

    /**
     * MIS编码
     */
    @ExcelProperty(index = 1)
    private String misCode;

    /**
     * 月度滚动额
     */
    @ExcelProperty(index = 3)
    private BigDecimal monthlyQuota;

    /**
     * 最新应收账款余额
     */
    @ExcelProperty(index = 4)
    private BigDecimal accountReceivableQuota;

    /**
     * 本月利率(年化）
     */
    @ExcelProperty(index = 5)
    private BigDecimal dailyInterestRate;
}
