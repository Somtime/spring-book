package toby.spring.spring.dao;

import java.sql.Connection;
import java.sql.SQLException;

// DB 연결 횟수 카운팅
public class CountingConnectionMaker implements ConnectionMaker {
    private int counter = 0;
    private ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return realConnectionMaker.makeConnection();
    }
    public int getCounter() {
        return this.counter;
    }
}
