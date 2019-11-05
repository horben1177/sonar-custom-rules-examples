package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;

@Rule(
        key = "NonCamelCaseCheck",
        name = "Methods should adhere to camel case naming convention",
        description = "In Java, methods should adhere to the camel case naming convention",
        priority = Priority.MINOR,
        tags = {"bug"})
public class AvoidNonCamelCase extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    protected static final String LOWER_CASE_REGEXP = "[a-z]+((\\d)|([A-Z0-9][a-z0-9]+))*([A-Z])?";

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
        System.out.println(PrinterVisitor.print(context.getTree()));
    }

    @Override
    public void visitMethod(MethodTree tree) {

        if (tree.simpleName().name().matches(LOWER_CASE_REGEXP)) {
            context.reportIssue(this, tree, "Avoid using non-camel case methods names!");
        }
        super.visitMethod(tree);
    }

}

