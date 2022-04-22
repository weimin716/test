package com.gdd.ylz.modules.excel;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * @author ：weimin
 * @date ：Created in 2022/3/11 0011 15:02
 * @description：自定义写拦截器
 * @modified By：`
 * @version: 1.0
 */

public class ContactCellWriteHandler implements CellWriteHandler {
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        Workbook workbook = cell.getSheet().getWorkbook();
        if(cell.getColumnIndex()==1 && cell.getStringCellValue().equals("Min Wei")){
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            WriteFont headWriteFont = new WriteFont();
            //字体样式
            headWriteFont.setBold(true);
            headWriteFont.setColor(IndexedColors.RED.getIndex());

            headWriteCellStyle.setWriteFont(headWriteFont);
            CellStyle cellStyle = StyleUtil.buildContentCellStyle(workbook, headWriteCellStyle);
            cell.setCellStyle(cellStyle);
       }
    }
}
