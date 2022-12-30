package bookmap.operation.impl;

import bookmap.exception.NoSuchOrderException;
import bookmap.model.Order;
import bookmap.operation.OperationService;
import bookmap.storage.OrderBook;
import java.util.Comparator;

public class OperationServiceRemoveImpl implements OperationService {
    private static final String OPERATION_NAME = "o";
    private static final String SEPARATED_VALUE = ",";
    private static final String REQUEST_SELL_TYPE = "sell";
    private static final String REQUEST_BUY_TYPE = "buy";
    private static final int REQUEST_TYPE_INDEX = 1;
    private static final int REQUEST_COUNT_INDEX = 2;
    private static final String ORDER_BID_TYPE = "bid";
    private static final String ORDER_ASK_TYPE = "ask";

    @Override
    public void apply(String request) {
        String[] dataFromRequest = request.split(SEPARATED_VALUE);
        String type = dataFromRequest[REQUEST_TYPE_INDEX];
        if (type.equals(REQUEST_SELL_TYPE)) {
            applyForBid(dataFromRequest);
        } else if (type.equals(REQUEST_BUY_TYPE)) {
            applyForAsk(dataFromRequest);
        }
    }

    @Override
    public boolean isApplicable(String operation) {
        return operation.equals(OPERATION_NAME);
    }

    private void applyForBid(String[] dataFromRequest) {
        Order order = OrderBook.getOrderList(ORDER_BID_TYPE)
                .stream()
                .filter(o -> o.getSize() > 0)
                .max(Comparator.comparing(Order::getPrice))
                .orElseThrow(()
                        -> new NoSuchOrderException("The required product is not available"));
        order.setSize(order.getSize() - Integer.parseInt(dataFromRequest[REQUEST_COUNT_INDEX]));
    }

    private void applyForAsk(String[] dataFromRequest) {
        Order order = OrderBook.getOrderList(ORDER_ASK_TYPE)
                .stream()
                .filter(o -> o.getSize() > 0)
                .min(Comparator.comparing(Order::getPrice))
                .orElseThrow(()
                        -> new NoSuchOrderException("The required product is not available"));
        order.setSize(order.getSize() - Integer.parseInt(dataFromRequest[REQUEST_COUNT_INDEX]));
    }
}
