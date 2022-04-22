package toby.spring.spring.service.sqlservice;

public class DefaultSqlService extends BaseSqlService {
    public DefaultSqlService() {
        setSqlReader(new FactorySqlReader());
        setSqlRegistry(new HashMapSqlRegistry());
    }
}
