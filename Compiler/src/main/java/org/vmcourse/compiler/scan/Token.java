package org.vmcourse.compiler.scan;

public record Token(TokenType type, String lexeme, Object literal, int line) {

    @Override
    public String toString() {
        return "<" + type.name() + ", " + lexeme + ">";
    }
}
