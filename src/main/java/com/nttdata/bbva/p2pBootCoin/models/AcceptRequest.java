package com.nttdata.bbva.p2pBootCoin.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptRequest {
    @NotEmpty(message = "El campo requestBootCoinId es requerido.")
    private String requestBootCoinId;
    @NotEmpty(message = "El campo userIdAccept es requerido.")
    private String userIdAccept;
}
