package bookmap.operation.impl;

import bookmap.exception.NoSuchOrderException;
import bookmap.model.Order;
import bookmap.operation.OperationService;
import bookmap.storage.OrderBook;
import bookmap.storage.ReportContainer;
import java.util.Collection;
import java.util.Comparator;

public class OperationServiceSelectImpl implements OperationService {
    private static final String OPERATION_NAME = "q";
    private static final String SEPARATED_VALUE = ",";
    private static final String REQUEST_BEST_BID = "best_bid";
    private static final String REQUEST_BEST_ASK = "best_ask";
    private static final String REQUEST_SIZE = "size";
    private static final int REQUEST_TYPE_INDEX = 1;
    private static final int REQUEST_COUNT_INDEX = 2;
    private static final String ORDER_BID_TYPE = "bid";
    private static final String ORDER_ASK_TYPE = "ask";

    @Override
    public void apply(String request) {
        String[] dataFromRequest = request.split(SEPARATED_VALUE);
        if (dataFromRequest[REQUEST_TYPE_INDEX].equals(REQUEST_BEST_BID)) {
            ReportContainer.add(bestBid());
        } else if (dataFromRequest[REQUEST_TYPE_INDEX].equals(REQUEST_BEST_ASK)) {
            ReportContainer.add(bestAsk());
        } else if (dataFromRequest[REQUEST_TYPE_INDEX].equals(REQUEST_SIZE)) {
            ReportContainer.add(countOfOrdersWithPrice(
                    Integer.parseInt(dataFromRequest[REQUEST_COUNT_INDEX])));
        }
    }

    @Override
    public boolean isApplicable(String operation) {
        return operation.equals(OPERATION_NAME);
    }

    private String bestBid() {
        Order order = OrderBook.getOrderList(ORDER_BID_TYPE).parallelStream()
                .max(Comparator.comparing(Order::getPrice).thenComparing(Order::getSize))
                .orElseThrow(
                        () -> new NoSuchOrderException("The required product is not available"));
        return order.getPrice() + "," + order.getSize();
    }

    private String bestAsk() {
        Order order = OrderBook.getOrderList(ORDER_ASK_TYPE).parallelStream()
                .min(Comparator.comparing(Order::getPrice)
                        .thenComparing(Order::getSize, Comparator.reverseOrder()))
                .orElseThrow(()
                        -> new NoSuchOrderException("The required product is not available"));
        return order.getPrice() + "," + order.getSize();
    }

    private String countOfOrdersWithPrice(int price) {
        int count = OrderBook.getAllOrders().parallelStream()
                .flatMap(Collection::parallelStream)
                .filter(o -> o.getPrice() == price)
                .mapToInt(Order::getSize)
                .sum();
        return String.valueOf(count);
    }
}
