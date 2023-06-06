package scheduledExample.dataTransferScheduler.business.abstracts;

import scheduledExample.dataTransferScheduler.business.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomer();
}
