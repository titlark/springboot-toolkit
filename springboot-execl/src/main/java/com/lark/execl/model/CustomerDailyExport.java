package com.lark.execl.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CustomerDailyExport {
    /**
     * 客户名称
     */
    @ExcelProperty("客户编码")
    private String customerName;

    /**
     * MIS编码
     */
    @ExcelProperty("MIS编码")
    private String misCode;

    /**
     * 月度滚动额
     */
    @ExcelProperty("月度滚动额")
    private BigDecimal monthlyQuota;

    /**
     * 最新应收账款余额
     */
    @ExcelProperty("最新应收账款余额")
    private BigDecimal accountReceivableQuota;

    /**
     * 本月利率(年化）
     */
    @NumberFormat("#.##%")
    @ExcelProperty("本月利率(年化）")
    private BigDecimal dailyInterestRate;
}
