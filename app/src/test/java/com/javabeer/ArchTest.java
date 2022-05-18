package com.javabeer;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

@AnalyzeClasses(packages = "com.javabeer", importOptions = ImportOption.DoNotIncludeTests.class)
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
    final SliceRule noCyclicDependenciesAllowed = SlicesRuleDefinition
            .slices()
            .matching("com.javabeer.(*)..")
            .should()
            .beFreeOfCycles();
}
