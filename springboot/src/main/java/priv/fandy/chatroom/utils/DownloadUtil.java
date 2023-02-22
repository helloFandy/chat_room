package priv.fandy.chatroom.utils;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DownloadUtil{

    private static String FILE_NAME = "/Users/handy/downloads/download.xlsx";

    private static String SHEET_NAME = "模板";


    public static void simpleWrite(String fileName, Class clazz, List data,String sheetName){
        EasyExcel.write(fileName, clazz)
                .sheet(sheetName)
                .doWrite(() -> {
                    // 分页查询数据
                    return data;
                });
    }

    public static void simpleWrite(Class clazz, List data,String sheetName){
       simpleWrite(FILE_NAME,clazz,data, sheetName);
    }

    public static void simpleWrite(Class clazz, List data){
        simpleWrite(FILE_NAME,clazz,data, SHEET_NAME);
    }

    public static void simpleWrite(String fileName, Class clazz, List data){
        simpleWrite(fileName,clazz,data, SHEET_NAME);
    }
}
