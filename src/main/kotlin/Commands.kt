enum class Commands(val commandName: String) {
    INITIALIZE("init"),
    ASSIGN("="),
    IF_START("if_start"),
    IF_BODY_START("if_body_start"),
    IF_BODY_END("if_body_end"),
    IF_END("if_end"),
    WHILE_START("while_start"),
    WHILE_CHECK_CONDITION("while_check"),
    WHILE_END("while_end")
}