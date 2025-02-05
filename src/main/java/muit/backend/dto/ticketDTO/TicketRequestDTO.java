package muit.backend.dto.ticketDTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TicketRequestDTO {

    private Integer quantity;
}

