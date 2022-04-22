package toby.spring.spring.service.sqlservice;

import javax.annotation.PostConstruct;
import java.sql.SQLType;
import java.util.Map;

public class SimpleSqlService implements SqlService, SqlRegistry, SqlReader {
    private SqlReader sqlReader;
    private SqlRegistry sqlRegistry;
    private Map<String, String> sqlMap;

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    public void setSqlReader(SqlReader sqlReader) {
        this.sqlReader = sqlReader;
    }

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        try {
            return this.sqlRegistry.findSql(key);
        } catch (SqlNotFoundException e) {
            throw new SqlRetrievalFailureException(e.getMessage());
        }
    }

    @Override
    public void registerSql(String key, String sql) {
        sqlMap.put(key, sql);
    }

    @Override
    public String findSql(String key) {
        String sql = sqlMap.get(key);
        if (sql == null) throw new SqlNotFoundException(key + "에 대한 SQL을 찾을 수 없습니다.");
        else return sql;
    }

    @Override
    public void read(SqlRegistry sqlRegistry) {
        try {
            for (Map.Entry<String, String> sql : sqlMap.entrySet()) {
                sqlRegistry.registerSql(sql.getKey(), sql.getValue());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void loadSql() {
        this.sqlReader.read(this.sqlRegistry);
    }
}