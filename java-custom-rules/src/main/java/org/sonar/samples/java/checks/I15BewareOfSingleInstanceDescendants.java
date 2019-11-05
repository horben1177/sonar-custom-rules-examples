package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TypeTree;

import java.util.Collections;
import java.util.List;

@Rule(
        key = "I15BewareOfSingleInstanceDescendants",
        name = "Do not confuse objects with descendants,",
        description = "Do not confuse objects with descendants, beware of single instance descendants",
        priority = Priority.MAJOR,
        tags = {"clean-code"})
public class I15BewareOfSingleInstanceDescendants extends IssuableSubscriptionVisitor {

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree treeClazz = (ClassTree) tree;

        if (treeClazz.superClass() == null) {
            return;
        }
        if(treeClazz.members().isEmpty()){
            reportIssue(tree, String.format("Do not confuse objects with descendants! "));
            return;
        }
        boolean reportFlag = false;
        for(Tree t : treeClazz.members()){
            if(t.is(Tree.Kind.METHOD)){
                MethodTree meTree = (MethodTree) t;
                if(meTree.block().body().isEmpty()){
                    reportFlag = true;
                }
            }
        }
        if(reportFlag){
            reportIssue(tree, String.format("Do not confuse objects with descendants! "));
        }
    }

}
