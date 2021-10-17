package com.dimon.tododemo;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.dimon.tododemo");

        noClasses()
            .that()
            .resideInAnyPackage("com.dimon.tododemo.service..")
            .or()
            .resideInAnyPackage("com.dimon.tododemo.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.dimon.tododemo.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
