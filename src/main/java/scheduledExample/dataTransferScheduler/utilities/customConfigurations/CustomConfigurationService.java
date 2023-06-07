package scheduledExample.dataTransferScheduler.utilities.customConfigurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomConfigurationService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomConfigurationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getCronValue() {
        String sql = "SELECT value FROM general_configurations WHERE key = 'cron_value'";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public String getVersionNumberValue() {
        String sql = "SELECT value FROM general_configurations WHERE key = 'version_number'";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}