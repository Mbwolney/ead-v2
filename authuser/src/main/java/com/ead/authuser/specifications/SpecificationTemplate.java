package com.ead.authuser.specifications;

import com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    // FILTRO
    @And({
            @Spec(path = "userType", spec = Equal.class), // O path é o atributo que vai filtrar, spec é o tipo que vai utilizar na Specification do tipo EQUAL
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class), // LIKE é o tipo igual do SQL %Math%
            @Spec(path = "fullName", spec = Like.class)
    })
    public interface UserSpec extends Specification<UserModel> {}
}
