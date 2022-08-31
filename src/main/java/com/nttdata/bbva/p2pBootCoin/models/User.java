package com.nttdata.bbva.p2pBootCoin.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @NotEmpty(message = "El campo identificationDocument es requerido.")
    private String identificationDocument;
    @NotEmpty(message = "El campo cellPhoneNumber es requerido.")
    @Length(min = 9, max = 9, message = "El campo cellPhoneNumber debe tener 9 caracteres.")
    private String cellPhoneNumber;
    @NotEmpty(message = "El campo emailAddress es requerido.")
    @Email(message = "El campo emailAddress tiene un formato no válido.")
    private String emailAddress;
    @NotEmpty(message = "El campo bankAccountNumber es requerido.")
    private String bankAccountNumber;
}
