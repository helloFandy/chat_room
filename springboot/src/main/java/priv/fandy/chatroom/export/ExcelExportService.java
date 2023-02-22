package priv.fandy.chatroom.export;

import java.util.List;

public interface ExcelExportService<T,E> {

    void export(E param);

    List<T> getData(E param);

}
