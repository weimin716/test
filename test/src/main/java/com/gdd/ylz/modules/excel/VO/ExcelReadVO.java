package com.gdd.ylz.modules.excel.VO;

import com.gdd.ylz.modules.excel.listener.CommenListener;
import lombok.Data;

/**
 * @author ：weimin
 * @date ：Created in 2022/3/10 0010 10:49
 * @description：
 * @modified By：`
 * @version: 1.0
 */
@Data
public class ExcelReadVO<T> {
   private Class<T> clazz;
   private CommenListener<T> commenListener;
}
