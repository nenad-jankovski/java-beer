package com.javabeer;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

@AnalyzeClasses(packages = "com.javabeer")
public class ArchTest {

    @com.tngtech.archunit.junit.ArchTest
    final ArchRule coreDomainClassesShouldNotHaveAnyExternalDependencies = ArchRuleDefinition
            .noClasses()
            .that()
            .resideInAnyPackage("..domain..", "..usecase..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..adapter..");

    @com.tngtech.archunit.junit.ArchTest
    final SliceRule noCyclicDependenciesAllowedInDomain = SlicesRuleDefinition
            .slices()
            .matching("com.javabeer.domain.(*)..")
            .should()
            .beFreeOfCycles();

    @com.tngtech.archunit.junit.ArchTest
    final SliceRule noCyclicDependenciesAllowedInUseCases = SlicesRuleDefinition
            .slices()
            .matching("com.javabeer.usecase.(*)..")
            .should()
            .beFreeOfCycles();
}
