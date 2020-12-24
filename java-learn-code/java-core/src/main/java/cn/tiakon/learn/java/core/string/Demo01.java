package cn.tiakon.learn.java.core.string;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;


public class Demo01 {

    /**
     * 将KV格式的字符串转换成MAP
     */
    @Test
    public void convertStringToMapTest() {
        Map<String, String> map = splitToMap("A=4 H=X PO=87");
        assertEquals("4", map.get("A"));
        assertEquals("X", map.get("H"));
        assertEquals("87", map.get("PO"));
    }

    private Map<String, String> splitToMap(String in) {
        return Splitter.on(" ").withKeyValueSeparator("=").split(in);
    }

}