package toby.spring.spring.service.sqlservice;

import java.util.Map;

public class FactorySqlReader implements SqlReader{
    private Map<String, String> sqlMap;

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
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
}
