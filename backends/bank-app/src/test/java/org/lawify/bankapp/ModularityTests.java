package org.lawify.bankapp;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModularityTests {
    private static final String docPath = "./documentation";
    ApplicationModules modules = ApplicationModules.of(BankAppApplication.class);

    @Test
    void verifyModularity() {
        modules.forEach(System.out::println);

        modules.verify();
    }

    @Test
    void renderDocumentationUml() {
        var canvasOpts = Documenter.CanvasOptions.defaults();

        var docOpts = Documenter.DiagramOptions
                .defaults()
                .withStyle(Documenter.DiagramOptions.DiagramStyle.UML);

        new Documenter(modules, docPath + "/uml").writeDocumentation(docOpts, canvasOpts);
    }
    @Test
    void renderDocumentationC4() {
        var canvasOpts = Documenter.CanvasOptions.defaults();

        var docOpts = Documenter.DiagramOptions
                .defaults()
                .withStyle(Documenter.DiagramOptions.DiagramStyle.C4);

        new Documenter(modules, docPath + "/c4").writeDocumentation(docOpts, canvasOpts);
    }
}
