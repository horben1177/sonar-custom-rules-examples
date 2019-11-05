package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.Collections;
import java.util.List;

@Rule(
        key = "AvoidUsingNonPrivateInOtherClass",
        name = "Non private members of other class used",
        description = "Avoid using non private members of other class",
        priority = Priority.MAJOR,
        tags = {"clean-code"})
public class C2AvoidUsingNonPublicInOtherClass extends IssuableSubscriptionVisitor {

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.VARIABLE);
    }

    @Override
    public void visitNode(Tree tree) {
        boolean canBeUsed = true;
        VariableTree varTree = (VariableTree) tree;
        Symbol symbol = varTree.symbol();
        List<IdentifierTree> usages = symbol.usages();
        for(int i = 0; i <  usages.size() - 1; i++){
            if(!usages.get(i).symbol().enclosingClass().equals(usages.get(i+1).symbol().enclosingClass())){
                if((usages.get(i).symbol().isPackageVisibility() || usages.get(i+1).symbol().isPackageVisibility())){
                    canBeUsed = false;
                }
            }
        }
        if(!canBeUsed){
            reportIssue(tree, "Avoid accessing other method's variables!");
        }
    }

}
