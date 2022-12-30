package bookmap;

import bookmap.operation.OperationService;
import bookmap.operation.impl.OperationServiceRemoveImpl;
import bookmap.operation.impl.OperationServiceSelectImpl;
import bookmap.operation.impl.OperationServiceUpdateImpl;
import bookmap.service.FileReaderService;
import bookmap.service.FileWriterService;
import bookmap.service.impl.TxtReaderServiceImpl;
import bookmap.service.impl.TxtWriterServiceImpl;
import bookmap.storage.OrderBook;
import bookmap.strategy.OperationServiceStrategy;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        long m = System.currentTimeMillis();
        FileReaderService readerService = new TxtReaderServiceImpl();
        List<String> requests = readerService.read("index.txt");
        List<OperationService> operationServices = List.of(new OperationServiceRemoveImpl(),
                new OperationServiceUpdateImpl(),
                new OperationServiceSelectImpl());
        OperationServiceStrategy strategy = new OperationServiceStrategy(operationServices);
        for (String request : requests) {
            strategy.getOperationService(String.valueOf(request.charAt(0))).apply(request);
        }
        FileWriterService fileWriterService = new TxtWriterServiceImpl();
        fileWriterService.write("output.txt");
        System.out.println(System.currentTimeMillis() - m);
    }
}
