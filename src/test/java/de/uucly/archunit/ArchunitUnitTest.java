package de.uucly.archunit;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.dependencies.SliceRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

class ArchunitUnitTest {

    @Test
    void cycleTest() {

        SliceRule sliceRule = slices().matching("de.uucly.archunit.(*)..").should().beFreeOfCycles();

        JavaClasses jc = new ClassFileImporter().importPackages("de.uucly.archunit");
        sliceRule.check(jc);
    }

    @Test
    void resideTest() {

        ArchRule r1 = classes()
                .that().haveNameMatching("Service")
                .should().resideInAPackage("..service..");

        JavaClasses jc = new ClassFileImporter()
                .importPackages("de.uucly.archunit");
        r1.check(jc);
    }

    @Test
    void classesTest() {

        ArchRule r1 = classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().accessClassesThat().haveNameNotMatching(".*Repository");

        JavaClasses jc = new ClassFileImporter()
                .importPackages("de.uucly.archunit");
        r1.check(jc);
    }

    @Test
    void annotationTest() {

        ArchRule r1 = classes()
                .that().haveNameMatching(".*Repository")
                .should().implement("CrudRepository");

        JavaClasses jc = new ClassFileImporter()
                .importPackages("de.uucly.archunit");
        r1.check(jc);
    }

    @Test
    void layerTest() {

        JavaClasses jc = new ClassFileImporter()
                .importPackages("de.uucly.archunit");

        Architectures.LayeredArchitecture arch = layeredArchitecture()
                // Define layers
                .layer("Business").definedBy("..business")
                .layer("Controller").definedBy("..controller")
                .layer("Service").definedBy("..service")
                .layer("Persistence").definedBy("..persistence")
                .layer("Repository").definedBy("..repo")
                // Add constraints
                .whereLayer("Controller").mayOnlyBeAccessedByLayers("Service", "Business")
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service")
                .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Persistence");

        arch.check(jc);
    }

}
