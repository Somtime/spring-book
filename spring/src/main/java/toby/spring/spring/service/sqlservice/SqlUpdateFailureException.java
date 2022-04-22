package toby.spring.spring.service.sqlservice;

public class SqlUpdateFailureException extends RuntimeException {
    public SqlUpdateFailureException(String key) {
        super(String.format("fail to update SQL '%s'!", key));
    }
}
