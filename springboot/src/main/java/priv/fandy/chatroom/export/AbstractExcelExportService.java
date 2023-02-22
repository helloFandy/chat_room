package priv.fandy.chatroom.export;

import java.util.List;

public abstract class AbstractExcelExportService<T,E> implements ExcelExportService<T,E>{

    @Override
    public void export(E param) {

    }

    @Override
    public List<T> getData(E param) {
        return null;
    }
}
