package com.nttdata.bbva.p2pBootCoin.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestBootCoin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @NotEmpty(message = "El campo userId es requerido.")
    private String userId;
    @DecimalMin(value = "5.000", message = "El campo amount debe ser mayor o igual a 5.000.")
    @Digits(integer = 3, fraction = 3, message = "El campo amount de tener el siguiente formato: '###.000'.")
    @NotNull(message = "El campo amount es requerido.")
    private BigDecimal amount;
    @NotEmpty(message = "El campo payWith es requerido.")
    private String payWith;

    private String userIdAccept;
}
