package scheduledExample.dataTransferScheduler.business.dto.converter;

import org.springframework.stereotype.Component;
import scheduledExample.dataTransferScheduler.business.dto.CustomerDto;
import scheduledExample.dataTransferScheduler.entities.Customer;

@Component
public class CustomerDtoConverter {
    public CustomerDto convertToCustomerDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getTckn(),
                customer.getName(),
                customer.getSurname(),
                customer.getAddress(),
                customer.getVersionNumber()
        );
        //throw new RuntimeException("deneme hatası");
    }
}
