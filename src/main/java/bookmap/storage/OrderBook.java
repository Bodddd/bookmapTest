package bookmap.storage;

import bookmap.model.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrderBook {
    private static final Map<String, List<Order>> orders = new HashMap<>();

    public static List<Order> getOrderList(String type) {
        if (!orders.containsKey(type)) {
            orders.put(type, new ArrayList<>());
        }
        return orders.get(type);
    }

    public static List<List<Order>> getAllOrders() {
        return new ArrayList<>(orders.values());
    }
}
