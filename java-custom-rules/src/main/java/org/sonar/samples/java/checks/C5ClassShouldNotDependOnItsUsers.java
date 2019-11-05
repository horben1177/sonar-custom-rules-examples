package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Rule(
        key = "C5ClassShouldNotDependOnItsUsers",
        name = "A class should not depend on its users",
        description = "A class must not know about its descendants",
        priority = Priority.CRITICAL,
        tags = {"clean-code"})
public class C5ClassShouldNotDependOnItsUsers extends IssuableSubscriptionVisitor {

    @Override
    public List<Kind> nodesToVisit() {
        return Arrays.asList(Kind.METHOD, Kind.CLASS, Kind.VARIABLE);
    }

    @Override
    public void visitNode(Tree tree) {
        Tree innerTree = tree;
        if(tree.is(Kind.METHOD)) {
            Symbol sym = ((MethodTree) innerTree).symbol();
            Symbol.TypeSymbol enclosingClass = sym.enclosingClass();
        }else if(tree.is(Kind.CLASS)){
            Symbol sym = ((ClassTree) innerTree).symbol();
            Symbol.TypeSymbol enclosingClass = sym.enclosingClass();
        }else if(tree.is(Kind.VARIABLE)){
            Symbol sym = ((VariableTree) innerTree).symbol();
            Symbol.TypeSymbol enclosingClass = sym.enclosingClass();
        }

                reportIssue(tree, "A class should not depend on its users!");
    }

}