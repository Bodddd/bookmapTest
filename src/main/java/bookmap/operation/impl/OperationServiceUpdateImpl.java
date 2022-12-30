package bookmap.operation.impl;

import bookmap.model.Order;
import bookmap.operation.OperationService;
import bookmap.storage.OrderBook;

public class OperationServiceUpdateImpl implements OperationService {
    private static final String OPERATION_NAME = "u";
    private static final String SEPARATED_VALUE = ",";
    private static final int ORDER_PRICE_INDEX = 1;
    private static final int ORDER_SIZE_INDEX = 2;
    private static final int ORDER_TYPE_INDEX = 3;

    @Override
    public void apply(String request) {
        Order order = getOrderFromRequest(request);
        OrderBook.getOrderList(order.getType()).add(order);
    }

    @Override
    public boolean isApplicable(String operation) {
        return operation.equals(OPERATION_NAME);
    }

    private Order getOrderFromRequest(String request) {
        String[] dataFromRequest = request.split(SEPARATED_VALUE);
        Order order = new Order();
        order.setPrice(Integer.parseInt(dataFromRequest[ORDER_PRICE_INDEX]));
        order.setSize(Integer.parseInt(dataFromRequest[ORDER_SIZE_INDEX]));
        order.setType(dataFromRequest[ORDER_TYPE_INDEX]);
        return order;
    }
}
