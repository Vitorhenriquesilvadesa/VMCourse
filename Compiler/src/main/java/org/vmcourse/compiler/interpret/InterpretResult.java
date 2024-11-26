package org.vmcourse.compiler.interpret;

import org.vmcourse.compiler.component.IOComponent;

public class InterpretResult extends IOComponent<InterpretResult> {

    private int code;

    public InterpretResult(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
