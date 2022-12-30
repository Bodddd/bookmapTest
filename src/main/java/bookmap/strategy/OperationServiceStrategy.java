package bookmap.strategy;

import bookmap.operation.OperationService;
import java.util.List;
import java.util.NoSuchElementException;

public class OperationServiceStrategy {
    private List<OperationService> operationServices;

    public OperationServiceStrategy(List<OperationService> operationServices) {
        this.operationServices = operationServices;
    }

    public OperationService getOperationService(String operation) {
        return operationServices.stream()
                .filter(o -> o.isApplicable(operation))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
