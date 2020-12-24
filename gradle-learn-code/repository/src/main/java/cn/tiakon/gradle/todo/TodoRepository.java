package cn.tiakon.gradle.todo;

import todo.TodoItem;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
public class TodoRepository {

    private Map map = new HashMap<String, TodoItem>();

    private void save(TodoItem item) {
        map.put("todo-01", item);
    }
}
