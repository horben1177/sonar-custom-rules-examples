package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.java.ast.visitors.LinesOfCodeVisitor;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Arrays;
import java.util.List;

@Rule(
        key = "I23EmptyMethodOverride",
        name = "Inherited method is overriding super equivalent with empty body",
        description = "Inherited method should not override super method with empty body",
        priority = Priority.MAJOR,
        tags = {"clean-code"})
public class I23EmptyMethodOverride extends IssuableSubscriptionVisitor {

    @Override
    public List<Tree.Kind> nodesToVisit() {
        {
            return Arrays.asList(Tree.Kind.METHOD);
        }
    }

    @Override
    public void visitNode(Tree tree) {
            MethodTree methodTree = (MethodTree) tree;
            Symbol methodSymbol = methodTree.symbol();
            boolean isOverridingMethod = methodSymbol.metadata().isAnnotatedWith("java.lang.Override");
            BlockTree methodBlock = methodTree.block();
            if(methodBlock == null || methodBlock.body() == null || methodBlock.body().isEmpty() && isOverridingMethod){
                reportIssue(tree, "Inherited method overriden with empty method block!");
            }

    }

}
