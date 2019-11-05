package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.plugins.java.api.semantic.Symbol;

import java.util.Collections;
import java.util.List;

@Rule(
        key = "C1AvoidPublicVariables",
        name = "OOP Classes should not contain public variables",
        description = "OOP Classes should not contain public variables",
        priority = Priority.MAJOR,
        tags = {"clean-code"})
public class C1AvoidPublicVariables extends IssuableSubscriptionVisitor {
    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.VARIABLE);
    }

    @Override
    public void visitNode(Tree tree) {
        VariableTree varTree = (VariableTree) tree;
        Symbol symbol = varTree.symbol();
        if(!symbol.owner().isMethodSymbol() && !symbol.isPrivate()){
            reportIssue(tree, "Avoid using non-private variables!");
        }
    }
}
