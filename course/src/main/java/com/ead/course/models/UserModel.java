package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Vai ocultar o valores não preenchidos
@Entity
@Table(name = "TB_USERS")
public class UserModel implements Serializable {
    //Identificação da classe model
    private static final long serialVersionUID = 1l;

    @Id
    private UUID userId;
}
