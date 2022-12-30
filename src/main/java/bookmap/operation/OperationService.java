package bookmap.operation;

public interface OperationService {
    void apply(String request);

    boolean isApplicable(String operation);
}
