package ar.edu.unq.criptop2p.architecture

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes


@AnalyzeClasses(
    packages = [
        "ar.edu.unq.criptop2p.controller",
        "ar.edu.unq.criptop2p.model",
        "ar.edu.unq.criptop2p.service",
        "ar.edu.unq.criptop2p.persistance"]
)

internal class ArchitectureTest {

    @ArchTest
    val servicesOnlyAccessByControllersAndOtherServices: ArchRule = classes()
        .that().resideInAPackage("..service..")
        .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..")

    @ArchTest
    val repositoriesOnlyAccessByServices: ArchRule = classes()
        .that().resideInAPackage("..persistance..")
        .should().onlyBeAccessed().byAnyPackage("..service..")

}
