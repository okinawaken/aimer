package org.dromara.aimer.service.configuration.excel.listener;

import com.alibaba.excel.read.listener.ReadListener;
import org.dromara.aimer.service.configuration.excel.result.ExcelResult;

/**
 * Excel 导入监听
 *
 * @author Lion Li
 */
public interface ExcelListener<T> extends ReadListener<T> {

    ExcelResult<T> getExcelResult();

}
