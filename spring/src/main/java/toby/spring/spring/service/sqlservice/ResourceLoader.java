package toby.spring.spring.service.sqlservice;

import org.springframework.core.io.Resource;

public interface ResourceLoader {
    Resource getResource(String location);
}
