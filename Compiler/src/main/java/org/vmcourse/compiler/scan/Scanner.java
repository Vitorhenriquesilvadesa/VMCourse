package org.vmcourse.compiler.scan;

import org.vmcourse.compiler.pass.CompilationPass;
import org.vmcourse.util.TailFile;

import java.util.ArrayList;
import java.util.List;

public class Scanner extends CompilationPass<TailFile, ScannedData> {

    private List<Token> tokens;
    private String source;
    private int line;
    private int start;
    private int current;

    @Override
    public ScannedData pass(TailFile input) {

        resetInternalState(input);
        scanTokens();

        return new ScannedData(tokens);
    }

    private void resetInternalState(TailFile file) {
        line = 1;
        start = 0;
        current = 0;
        source = file.getSource();
        tokens = new ArrayList<>();
    }

    private void scanTokens() {
        while (!isAtEnd()) {
            syncCursors();
            scanToken();
        }

        makeToken(TokenType.EndOfFile, null, null);
    }

    private void syncCursors() {
        start = current;
    }

    private void scanToken() {
        char c = advance();

        switch (c) {
            case ' ':
            case '\t':
            case '\r':
                break;

            case '\n':
                line++;
                break;

            case '(':
                makeToken(TokenType.LeftParen);
                break;

            case ')':
                makeToken(TokenType.RightParen);
                break;

            case '+':
                makeToken(TokenType.Plus);
                break;

            case '-':
                makeToken(TokenType.Minus);
                break;

            case '*':
                makeToken(TokenType.Star);
                break;

            case '/':
                makeToken(TokenType.Slash);
                break;

            default:
                if (isDigit(c)) {
                    number();
                    break;
                } else {
                    throw new RuntimeException("Unexpected character: " + c);
                }
        }
    }

    private void number() {
        while (isDigit(peek())) {
            advance();
        }

        String lexeme = source.substring(start, current);
        Float number = Float.parseFloat(lexeme);
        makeToken(TokenType.Number, number);
    }

    private boolean isAlphanumeric(char c) {
        return isDigit(c) || isAlpha(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

    private void makeToken(TokenType type) {
        String lexeme = source.substring(start, current);
        makeToken(type, lexeme, null);
    }

    private void makeToken(TokenType type, Object literal) {
        String lexeme = source.substring(start, current);
        Token token = new Token(type, lexeme, literal, line);
        tokens.add(token);
    }

    private void makeToken(TokenType type, String lexeme, Object literal) {
        Token token = new Token(type, lexeme, literal, line);
        tokens.add(token);
    }

    private char advance() {
        return source.charAt(current++);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }


    @Override
    public Class<TailFile> getInputType() {
        return TailFile.class;
    }

    @Override
    public Class<ScannedData> getOutputType() {
        return ScannedData.class;
    }

    @Override
    public String getDebugName() {
        return "Lexer Pass";
    }
}
