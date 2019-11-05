package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Rule(
        key = "R4AvoidClassesWhichShouldBeMethods",
        name = "Avoid classes with single usage / function",
        description = "Avoid using classes that should be methods instead!",
        priority = Priority.MAJOR,
        tags = {"clean-code"})
public class R4AvoidClassesWhichShouldBeMethods extends IssuableSubscriptionVisitor {

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.CLASS, Tree.Kind.INTERFACE);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        Symbol symbol = classTree.symbol();
        boolean isFunctionalInterface = symbol.metadata().isAnnotatedWith("java.lang.FunctionalInterface");
        Long memberCount = classTree.members().stream().filter(method -> method.is(Tree.Kind.METHOD)).count();
        if(memberCount >= 1 && memberCount < 2 && !isFunctionalInterface) {
            reportIssue(tree, "Avoid using classes that should be methods instead!");
        }

    }

}
