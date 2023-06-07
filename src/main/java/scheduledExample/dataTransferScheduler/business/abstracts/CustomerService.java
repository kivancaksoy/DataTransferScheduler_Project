package scheduledExample.dataTransferScheduler.business.abstracts;

import reactor.core.publisher.Flux;
import scheduledExample.dataTransferScheduler.business.dto.CustomerDto;
import scheduledExample.dataTransferScheduler.entities.Customer;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomer();
    void getAllCustomerFromBase();
}
