package com.lark.execl.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.lark.execl.listener.CustomerDailyImportListener;
import com.lark.execl.listener.StringConverter;
import com.lark.execl.model.CustomerDailyExport;
import com.lark.execl.model.CustomerDailyImport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 流程为：框架读取一行数据，先执行转换器，当一行数据转换完成，执行监听器的回调方法，
 * 如果转换的过程中，出现转换异常，也会回调监听器中的 onException 方法。因此，可以
 * 在监听器中校验数据，在转换器中转换数据类型或者格式。
 *
 * --------------------------------------------------------------------------------------------
 *
 * 导入相关常用 API
 * 注解
 * - ExcelProperty 指定当前字段对应 excel 中的那一列。可以根据名字或者 Index 去匹配。当然也可以不写，默认第一个字段就是 index=0，以此类推。千万注意，要么全部不写，要么全部用 index，要么全部用名字去匹配。千万别三个混着用，除非你非常了解源代码中三个混着用怎么去排序的。
 * - ExcelIgnore 默认所有字段都会和 excel 去匹配，加了这个注解会忽略该字段。
 * - DateTimeFormat 日期转换，用 String 去接收 excel 日期格式的数据会调用这个注解。里面的 value 参照 java.text.SimpleDateFormat。
 * - NumberFormat 数字转换，用 String 去接收 excel 数字格式的数据会调用这个注解。里面的 value 参照 java.text.DecimalFormat。
 * <p>
 * EasyExcel 相关参数
 * <p>
 * - readListener 监听器，在读取数据的过程中会不断的调用监听器。
 * - converter 转换器，默认加载了很多转换器。也可以自定义，如果使用的是 registerConverter，那么该转换器是全局的，如果要对单个字段生效，可以在 ExcelProperty 注解的 converter 指定转换器。
 * - headRowNumber 需要读的表格有几行头数据。默认有一行头，也就是认为第二行开始起为数据。
 * - head 与 clazz 二选一。读取文件头对应的列表，会根据列表匹配数据，建议使用 class。
 * - autoTrim 字符串、表头等数据自动 trim。
 * - sheetNo 需要读取 Sheet 的编码，建议使用这个来指定读取哪个 Sheet。
 * - sheetName 根据名字去匹配 Sheet,excel 2003 不支持根据名字去匹配。
 *
 *
 * ---------------------------------------------------------------------------------------------
 *
 * 导出相关常用 API
 * 注解
 * - ExcelProperty 指定写到第几列，默认根据成员变量排序。value 指定写入的名称，默认成员变量的名字。
 * - ExcelIgnore 默认所有字段都会写入 excel，这个注解会忽略这个字段。
 * - DateTimeFormat 日期转换，将 Date 写到 excel 会调用这个注解。里面的 value 参照 java.text.SimpleDateFormat。
 * - NumberFormat 数字转换，用 Number 写 excel 会调用这个注解。里面的 value 参照 java.text.DecimalFormat。
 *
 * EasyExcel 相关参数
 * - needHead 监听器是否导出头。
 * - useDefaultStyle 写的时候是否是使用默认头。
 * - head 与 clazz 二选一。写入文件的头列表，建议使用 class。
 * - autoTrim 字符串、表头等数据自动 trim。
 * - sheetNo 需要写入的编码。默认 0。
 * - sheetName 需要些的 Sheet 名称，默认同 sheetNo。
 */
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    /**
     * 读取Excel
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/import")
    public Object importCustomerDaily(@RequestParam MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<CustomerDailyImport> customerDailyImports = EasyExcel.read(inputStream)
                //这个转换是成全局的，所有java为string,excel为String的都会用这个转换器。
                //如果就想单个字段使用请使用@ExcelProperty 指定converter
                .registerConverter(new StringConverter())
                //注册监听器，可以在这里校验字段
                .registerReadListener(new CustomerDailyImportListener()).head(CustomerDailyImport.class)
                // 设置sheet,默认读取第一个
                .sheet()
                // 设置标题所在行数
                .headRowNumber(2).doReadSync();
        return customerDailyImports.toString();
    }

    @RequestMapping("/export")
    public void exportCustomerDaily(HttpServletResponse response) throws IOException {
        // 生成数据
        List<CustomerDailyExport> customerDailyExportList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            CustomerDailyExport customerDailyExport = CustomerDailyExport.builder().misCode(String.valueOf(i)).customerName("customerName" + i).monthlyQuota(new BigDecimal(String.valueOf(i))).accountReceivableQuota(new BigDecimal(String.valueOf(i))).dailyInterestRate(new BigDecimal(String.valueOf(i))).build();
            customerDailyExportList.add(customerDailyExport);
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里 URLEncoder.encode 可以防止中文乱码，当然和 easyexcel 没有关系
        String filename = URLEncoder.encode("导出", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xlsx");
        EasyExcel.write(response.getOutputStream(), CustomerDailyExport.class).sheet("sheet0")
                // 设置字段宽度为自动调整，不太精确
                .head(getHeader())
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(customerDailyExportList);
    }

    private List<List<String>> getHeader() {
        /**
         * 装表头的数据
         */
        List<List<String>> list = new ArrayList<>();
        String[] arr1 = {"额度利率导入模板表", "客户名称"};
        String[] arr2 = {"额度利率导入模板表", "MIS编码"};
        String[] arr3 = {"额度利率导入模板表", "月度滚动额（元）"};
        String[] arr4 = {"额度利率导入模板表", "最新应收账款余额（元）"};
        String[] arr5 = {"额度利率导入模板表", "本月利率(年化)"};

        //第一列
        List<String> head0 = new ArrayList<>();
        Collections.addAll(head0, arr1);
        //第二列
        List<String> head1 = new ArrayList<>();
        Collections.addAll(head1, arr2);
        //第三列
        List<String> head2 = new ArrayList<>();
        Collections.addAll(head2, arr3);
        //第四列
        List<String> head3 = new ArrayList<>();
        Collections.addAll(head3, arr4);
        //第五列
        List<String> head4 = new ArrayList<>();
        Collections.addAll(head4, arr5);

        list.add(head0);
        list.add(head1);
        list.add(head2);
        list.add(head3);
        list.add(head4);
        return list;
    }
}
