package org.vmcourse.compiler.parse.expression;

public abstract class ExpressionNode {

    public abstract <R> R accept(ExpressionVisitor<R> visitor);
}
