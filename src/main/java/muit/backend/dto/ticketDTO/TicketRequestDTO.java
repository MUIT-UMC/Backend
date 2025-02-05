package muit.backend.dto.ticketDTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TicketRequestDTO {
    @JsonProperty("quantity")  // JSON의 "quantity"를 매핑
    private Integer quantity;
}

