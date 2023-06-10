package scheduledExample.dataTransferScheduler.business.services.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import scheduledExample.dataTransferScheduler.entities.Customer;

@Component
public class WebClientHelper {

    @Value("${SCHEDULER_SERVICE_URL:http://localhost:9090/v1/baseCustomer/getAllCustomerWithVersion?version=}")
    private String SchedulerService_URL;

    public Flux<Customer> getAllCustomerWithVersion(int version) {
        String versionStr = String.valueOf(version);
        WebClient webClient = WebClient.create();
        String url = SchedulerService_URL + version;

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Customer.class);
        //.bodyToMono(String.class);
    }





/*    WebClient webClient = WebClient.create();
    String url = "http://localhost:9090/v1/baseCustomer/getAllCustomerWithVersion?version=";

    Mono<String> responseMono = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String.class);

    String responseBody = responseMono.block();*/

}
