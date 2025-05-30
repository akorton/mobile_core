enum class Commands(val commandName: String) {
    INITIALIZE("init"),
    ASSIGN("="),
    IF_START("if_start"),
    IF_BODY_START("if_body_start"),
    IF_BODY_END("if_body_end"),
    IF_END("if_end"),
    WHILE_START("while_start"),
    WHILE_CHECK_CONDITION("while_check"),
    WHILE_END("while_end"),
    OP_PLUS("op_plus"),
    OP_MINUS("op_minus"),
    OP_MODULO("op_modulo"),
    OP_MULTIPLY("op_multiply"),
    OP_DIV("op_div"),
    OP_UNARY_MINUS("op_unary_minus"),
    GET_VALUE("get_value"),
    EQUALS("eq"),
    NOT_EQUALS("neq"),
    LESS("l"),
    GREATER("g"),
    LESS_OR_EQUAL("leq"),
    GREATER_OR_EQUAL("geq"),
    PRINT("print")
}