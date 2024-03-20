package schwarz.it.ae.bookmarx.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ArchitectureTest {

    private static JavaClasses importedClasses;

    @BeforeAll
    public static void setup() {
        importedClasses = new ClassFileImporter()
                .withImportOption(new DoNotIncludeTests())
                .importPackages("schwarz.it.ae.bookmarx");
    }

    @Test
    void testThatNoClassesDependOnClassesThatResideInPackageConfig() {
        ArchRuleDefinition
                .noClasses().that()
                .resideInAnyPackage(
                        "..bookmarx.core..",
                        "..bookmarx.dataprovides..",
                        "..bookmarx.entries..")
                .should().dependOnClassesThat().resideInAnyPackage("..bookmarx")
                .check(importedClasses);
    }

    @Test
    void testThatNoClassesFromCoreDependOnClassesOutsidePackage() {
        ArchRuleDefinition
                .noClasses().that().resideInAnyPackage("..bookmarx.core..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "..bookmarx.entries..",
                        "..bookmarx.dataproviders..",
                        "..bookmarx")
                .check(importedClasses);
    }

    @Test
    void testThatNoClassesFromEntryPointsDependOnClassesOutsidePackageExceptCore() {
        ArchRuleDefinition
                .noClasses().that().resideInAnyPackage("..bookmarx.entries..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "..bookmarx.dataproviders..",
                        "..bookmarx")
                .check(importedClasses);
    }

    @Test
    void testThatNoClassesFromDataprovidersDependOnClassesOutsidePackageExceptCore() {
        ArchRuleDefinition
                .noClasses().that().resideInAnyPackage("..bookmarx.dataproviders..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "..bookmarx.entries..",
                        "..bookmarx")
                .check(importedClasses);
    }

    @Test
    void testThatNoTestClassesFromCoreDependOnSpringFramework() {
        ArchRuleDefinition
                .noClasses().that().resideInAnyPackage("..bookmarx.core..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..org.springframework..")
                .check(importedClasses);
    }
}
