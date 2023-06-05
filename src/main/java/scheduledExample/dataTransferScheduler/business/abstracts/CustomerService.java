package scheduledExample.dataTransferScheduler.business.abstracts;

import scheduledExample.dataTransferScheduler.business.responses.GetAllCustomersResponse;

import java.util.List;

public interface CustomerService {
    List<GetAllCustomersResponse> getAllCustomer();
}
