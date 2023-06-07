package scheduledExample.dataTransferScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import scheduledExample.dataTransferScheduler.utilities.customConfigurations.CustomConfigurationService;

@EnableScheduling
@SpringBootApplication
public class DataTransferSchedulerApplication {

	private final CustomConfigurationService customConfigurationService;

	public DataTransferSchedulerApplication(CustomConfigurationService customConfigurationService) {
		this.customConfigurationService = customConfigurationService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DataTransferSchedulerApplication.class, args);
	}

	public void run() {
		String cronValue = customConfigurationService.getCronValue();
		System.out.println("Scheduled cron value: " + cronValue);
	}

}
