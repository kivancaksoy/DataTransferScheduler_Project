package scheduledExample.dataTransferScheduler.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import scheduledExample.dataTransferScheduler.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
