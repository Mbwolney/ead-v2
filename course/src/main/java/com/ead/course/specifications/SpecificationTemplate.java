package com.ead.course.specifications;

import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    // FILTRO
    @And({
            @Spec(path = "courseLevel", spec = Equal.class), // O path é o atributo que vai filtrar, spec é o tipo que vai utilizar na Specification do tipo EQUAL
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class) // LIKE é o tipo igual do SQL %Math%
    })
    public interface CourseSpec extends Specification<CourseModel> {}

    @Spec(path = "title", spec = Like.class) // LIKE é o tipo igual do SQL %Math%
    public interface ModuleSpec extends Specification<ModuleModel> {}

    @Spec(path = "title", spec = Like.class) // LIKE é o tipo igual do SQL %Math%
    public interface LessonSpec extends Specification<LessonModel> {}

    public static Specification<ModuleModel> moduleCourseId(final UUID courseId){
        return ((root, query, cb) -> {
            query.distinct(true); // Ele elimana os valores duplicados
            Root<ModuleModel> module = root; // Entidade A
            Root<CourseModel> course = query.from(CourseModel.class); // Entidade A
            Expression<Collection<ModuleModel>> coursesModules = course.get("modules"); // Extrair a coleção da entidade A na entidade B (Um curso tem multiplos modulos)
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(module, coursesModules)); // Verifica e retorna os modulos especifico relacionado aos curos
        });
    }

    public static Specification<LessonModel> lessonModuleId (final UUID moduleId){
        return ((root, query, cb) -> {
            query.distinct(true); // Ele elimana os valores duplicados
            Root<LessonModel> lesson = root; // Entidade A
            Root<ModuleModel> module = query.from(ModuleModel.class); // Entidade A
            Expression<Collection<LessonModel>> moduleLessons = module.get("lessons"); // Extrair a coleção da entidade A na entidade B (Um curso tem multiplos modulos)
            return cb.and(cb.equal(module.get("moduleId"), moduleId), cb.isMember(lesson, moduleLessons)); // Verifica e retorna os modulos especifico relacionado aos curos
        });
    }

    public static Specification<CourseModel> courseUserId(final UUID userId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<CourseModel, CourseUserModel> courseProd = root.join("courseUser");
            return cb.equal(courseProd.get("userId"), userId);
        };
    }

}
