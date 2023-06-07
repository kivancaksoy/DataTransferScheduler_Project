package scheduledExample.dataTransferScheduler.business.services.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import scheduledExample.dataTransferScheduler.entities.Customer;

@Component
public class WebClientHelper {

    public Flux<Customer> getAllCustomerWithVersion(int version) {
        String versionStr = String.valueOf(version);
        WebClient webClient = WebClient.create();
        String url = "http://localhost:9090/v1/baseCustomer/getAllCustomerWithVersion?version=" + version;

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
