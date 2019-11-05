package org.sonar.samples.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Rule(
        key = "R3AvoidSetterGetterClasses",
        name = "Useless Setter/Getter classes",
        description = "Avoid using pointless setter/getter classes",
        priority = Priority.MAJOR,
        tags = {"clean-code"})
public class R3AvoidSetterGetterClasses extends IssuableSubscriptionVisitor {

    private final static String SETTER = "set";
    private final static String GETTER = "get";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.METHOD);
    }

    @Override
    public void visitNode(Tree tree) {
        boolean faultyFlag = true;
        MethodTree methodTree = (MethodTree) tree;
        Symbol symbol = methodTree.symbol();
        boolean isEntity = getEnclosingClass(symbol).declaration().symbol().metadata().isAnnotatedWith("javax.persistence.Entity");
        Collection<Symbol> enclosedSymbols = getEnclosingClass(symbol).memberSymbols();
        for(Symbol symbols : enclosedSymbols){
            if(!isSetterMethod(symbols) || !isGetterMethod(symbols)){
                faultyFlag = false;
            }
        }
        Tree treeToReportAt = getEnclosingClass(symbol).declaration();
        if(!isEntity && faultyFlag){
            reportIssue(treeToReportAt, "Avoid the usage of pure setter-getter classes!");

        }

    }

    boolean isSetterMethod(Symbol symbol){
        if(symbol.isMethodSymbol()) {
            MethodTree methodTree = (MethodTree) symbol.declaration();
            return symbol.name().startsWith(SETTER) && methodTree.parameters().size() == 1 && methodTree.returnType().symbolType().isVoid();
        }
        return false;
    }

    boolean isGetterMethod(Symbol symbol){
        if(symbol.isMethodSymbol()) {
            MethodTree methodTree = (MethodTree) symbol.declaration();
            return symbol.name().startsWith(GETTER) && methodTree.parameters().isEmpty() && !methodTree.returnType().symbolType().isVoid();
        }
        return false;
    }

    private Symbol.TypeSymbol getEnclosingClass(Symbol symbol){
        return symbol.enclosingClass();
    }

}
