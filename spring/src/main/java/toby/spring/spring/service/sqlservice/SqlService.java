package toby.spring.spring.service.sqlservice;

public interface SqlService {
    String getSql(String key) throws SqlRetrievalFailureException;
}

