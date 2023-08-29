package com.fox.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/8/29 11:18
 *
 */
public class ExcelUtil {
    public static void writeToExcel(String fileName, List<?> dataList) {
        if (dataList==null || dataList.size()==0) return;
        Class<?> clazz = dataList.get(0).getClass();

        File templateFile = new File(SystemUtil.absolutePath()+fileName);
        File destFile = new File(SystemUtil.absolutePath()+"temp.xlsx");

        if (templateFile.exists()){
            // 第二次按照原有格式，不需要表头，追加写入
            EasyExcel.write(templateFile,clazz).needHead(false).
                    withTemplate(templateFile).file(destFile).sheet().doWrite(dataList);
        }else {
            // 第一次写入需要表头
            EasyExcel.write(templateFile,clazz).sheet().doWrite(dataList);
        }

        if (destFile.exists()) {
            //删除原模板文件，新生成的文件变成新的模板文件
            templateFile.delete();
            destFile.renameTo(templateFile);
        }
        System.out.println("成功追加"+dataList.size()+"条数据到 Excel 文件：" + fileName);
    }

    private static List<List<Object>> getExcelHeadData(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<List<Object>> headData = new ArrayList<>();
        List<Object> rowData = new ArrayList<>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelProperty.class)) {
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                rowData.add(excelProperty.value()[0]);
            }
        }

        headData.add(rowData);
        return headData;
    }
}
