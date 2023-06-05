package scheduledExample.dataTransferScheduler.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllCustomersResponse {
    private int id;
    private String tckn;
    private String name;
    private String surname;
    private String address;
}
