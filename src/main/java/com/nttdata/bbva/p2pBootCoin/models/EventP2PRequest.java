package com.nttdata.bbva.p2pBootCoin.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class EventP2PRequest {
	private String openAccountId;
	private String operationType; // Transferencia: D
	private BigDecimal amount;
}