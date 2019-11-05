package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Collections;
import java.util.List;

@Rule(
        key = "ThreePlusArgumentCheck",
        name = "Methods should avoid to have 3 or more arguments",
        description = "For complexity reasons, it is recommended to have methods take less than 3 arguments",
        priority = Priority.MINOR,
        tags = {"clean-code"})
public class AvoidThreeOrMoreArgumentFunctions extends IssuableSubscriptionVisitor {

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.METHOD);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodTree methodTree = (MethodTree) tree;
        if(methodTree.parameters().size() >= 3)
                reportIssue(tree, "Avoid methods having three or more arguments!");
            }
        }

