package com.joragupra.budinv.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Paths;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureTest {

    private static final String DOMAIN_PACKAGE = "..domain..";
    private static final String DTO_PACKAGE = "..dto..";
    private static final String ENTITY_PACKAGE = "..entity..";
    private static final String REPOSITORY_PACKAGE = "..repository..";

    private static JavaClasses projectClasses;

    @BeforeAll
    static void importClasses() {
        projectClasses = new ClassFileImporter()
                .withImportOption(new ImportOption.DoNotIncludeTests())
                .importPaths(
                        Paths.get("target/classes"),
                        Paths.get("../budinv-model/target/classes"),
                        Paths.get("../budinv-infrastructure/target/classes")
                );
    }

    @Test
    void domain_is_free_of_framework_dependencies() {
        noClasses().that().resideInAPackage(DOMAIN_PACKAGE)
                .should().dependOnClassesThat().resideInAnyPackage(
                        "org.springframework..",
                        "jakarta.persistence..",
                        "com.fasterxml.jackson..",
                        "jakarta.xml.bind.."
                )
                .because("the domain model must remain framework-agnostic")
                .check(projectClasses);
    }

    @Test
    void domain_does_not_depend_on_dto() {
        noClasses().that().resideInAPackage(DOMAIN_PACKAGE)
                .should().dependOnClassesThat().resideInAPackage(DTO_PACKAGE)
                .because("the domain model must not depend on its HTTP representation")
                .check(projectClasses);
    }

    @Test
    void domain_does_not_depend_on_entities() {
        noClasses().that().resideInAPackage(DOMAIN_PACKAGE)
                .should().dependOnClassesThat().resideInAPackage(ENTITY_PACKAGE)
                .because("the domain model must not depend on JPA entities")
                .check(projectClasses);
    }

    @Test
    void rest_controllers_reside_in_root_package() {
        classes().that().areAnnotatedWith(RestController.class)
                .should().resideInAPackage("com.joragupra.budinv")
                .because("REST controllers belong to the HTTP layer root package")
                .check(projectClasses);
    }

    @Test
    void jpa_entities_reside_in_entity_package() {
        classes().that().areAnnotatedWith("jakarta.persistence.Entity")
                .should().resideInAPackage(ENTITY_PACKAGE)
                .because("JPA entities must be confined to the infrastructure entity package")
                .check(projectClasses);
    }

    @Test
    void repositories_reside_in_repository_package() {
        classes().that().areInterfaces()
                .and().areAssignableTo("org.springframework.data.repository.Repository")
                .should().resideInAPackage(REPOSITORY_PACKAGE)
                .because("Spring Data repositories must be confined to the infrastructure repository package")
                .check(projectClasses);
    }

    @Test
    void controllers_do_not_use_jpa_entities() {
        noClasses().that().areAnnotatedWith(RestController.class)
                .should().dependOnClassesThat().resideInAPackage(ENTITY_PACKAGE)
                .because("controllers must interact with the domain model, not JPA entities directly")
                .check(projectClasses);
    }
}
