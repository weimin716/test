package com.gdd.ylz.modules.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.modules.excel.VO.ExcelReadVO;
import com.gdd.ylz.modules.excel.listener.CommenListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：weimin
 * @date ：Created in 2022/3/10 0010 9:43
 * @description：excel工具类
 * @modified By：`
 * @version: 1.0
 */
public class ExcelUtils {

    /**
     * 简单读一个sheet
     *
     * @author weimin
     * @date 2022/3/10 0010 10:08
     * @param
     * @return void
     * @Version1.0
     */
    public static <T> void simpleRead(String fileName, ExcelReadVO excelReadVO){
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName,excelReadVO.getClazz(), excelReadVO.getCommenListener()).sheet().doRead();
    }
   /**
    *
    * 简单读多个sheet
    * @author weimin
    * @date 2022/3/10 0010 10:58
    * @param
    * @return
    * @Version1.0
    *
    */
    public static <T> void simpleReadRepeated(String fileName, Map<Integer, ExcelReadVO> map) {
        ExcelReader excelReader = EasyExcel.read(fileName).build();
        List<ReadSheet> readSheetList=new ArrayList<>();
        for(Map.Entry<Integer, ExcelReadVO> entry:map.entrySet()){
            ReadSheet readSheet =
                    EasyExcel.readSheet(entry.getKey()).head(entry.getValue().getClazz()).registerReadListener(entry.getValue().getCommenListener()).build();
            readSheetList.add(readSheet);
        }
       excelReader.read(readSheetList);

    }

    /**
     * 简单读所有sheet
     *
     * @author weimin
     * @date 2022/3/10 0010 10:08
     * @param [fileName, excelReadVO]
     * @return void
     * @Version1.0
     */
    public static <T> void simpleReadAll(String fileName,ExcelReadVO excelReadVO){
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName,excelReadVO.getClazz(), excelReadVO.getCommenListener()).doReadAll();
    }


    
}
