import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

interface BlockSeeker {
    boolean isStart(String el);

    boolean isEnd(String el);

    default List<String> findBlock(Iterator<String> iterator) {
        var list = new ArrayList<String>();
        while (iterator.hasNext()) {
            if (isStart(iterator.next())) {
                while (iterator.hasNext()) {
                    var value = iterator.next();
                    if (!isEnd(value)) {
                        list.add(value);
                    } else return list;
                }
            }
        }
        return null;
    }
}
