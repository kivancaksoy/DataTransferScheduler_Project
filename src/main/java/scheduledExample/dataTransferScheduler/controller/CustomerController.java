package scheduledExample.dataTransferScheduler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import scheduledExample.dataTransferScheduler.business.abstracts.CustomerService;
import scheduledExample.dataTransferScheduler.business.dto.CustomerDto;
import scheduledExample.dataTransferScheduler.entities.Customer;

import java.util.List;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<CustomerDto>> getAllCustomer() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @GetMapping("/getAllCustomerFromBase")
    public ResponseEntity<Flux<Customer>> getAllCustomerFromBase(@RequestParam("version") int version) {
        return ResponseEntity.ok(customerService.getAllCustomerFromBase(version));
    }
}
