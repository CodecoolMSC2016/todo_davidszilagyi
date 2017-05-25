package listener;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import util.ConnectionUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public final class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        registerSetCharacterEncodingFilter(sce);
        //initializeDatabase();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private void registerSetCharacterEncodingFilter(ServletContextEvent sce) {
        sce.getServletContext().addFilter("SetCharacterEncodingFilter", "org.apache.catalina.filters.SetCharacterEncodingFilter");
    }

    private void initializeDatabase() {
        try (Connection conn = ConnectionUtil.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new EncodedResource(new ClassPathResource("/init.sql"), StandardCharsets.UTF_8));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
