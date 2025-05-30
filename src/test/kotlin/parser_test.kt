import notation.CommandElement
import notation.ConstantElement
import notation.NotationElement
import notation.VariableElement
import parser.ExpressionParser
import kotlin.test.assertEquals

private fun test(expression: String, expected: List<NotationElement>, expectedValue: Int,
                 program: List<NotationElement> = listOf()
) {
    val res = ExpressionParser.parseArithmetic(expression)
    assertEquals(expected, ExpressionParser.parseArithmetic(expression))
    val interpreter = Interpreter()
    interpreter.run(program + res)
    assertEquals(expectedValue, (interpreter.buffer.last() as ConstantElement).value)
}

fun simple_arithmetic_parser_test() {
    test("3+2-4",
        listOf(ConstantElement(3), ConstantElement(2), CommandElement(Commands.OP_PLUS),
            ConstantElement(4), CommandElement(Commands.OP_MINUS)), 1)
}

fun operation_order_test() {
    test("3+2*5", listOf(ConstantElement(3), ConstantElement(2), ConstantElement(5),
        CommandElement(Commands.OP_MULTIPLY), CommandElement(Commands.OP_PLUS)), 13)
}

fun hard_test() {
    test("(3 +  2) * -(8 - 5 /4) + (-1 - 3) % 5", listOf(ConstantElement(3), ConstantElement(2),
        CommandElement(Commands.OP_PLUS), ConstantElement(8), ConstantElement(5), ConstantElement(4),
        CommandElement(Commands.OP_DIV), CommandElement(Commands.OP_MINUS), CommandElement(Commands.OP_UNARY_MINUS),
        CommandElement(Commands.OP_MULTIPLY), ConstantElement(1), CommandElement(Commands.OP_UNARY_MINUS),
        ConstantElement(3), CommandElement(Commands.OP_MINUS), ConstantElement(5), CommandElement(Commands.OP_MODULO),
        CommandElement(Commands.OP_PLUS)), -39
    )
}

fun variables_test() {
    test("a + 3 * b", listOf(VariableElement("a"), CommandElement(Commands.GET_VALUE),
        ConstantElement(3), VariableElement("b"), CommandElement(Commands.GET_VALUE),
        CommandElement(Commands.OP_MULTIPLY), CommandElement(Commands.OP_PLUS)),
        7,
        listOf(VariableElement("a"), CommandElement(Commands.INITIALIZE), VariableElement("a"),
            ConstantElement(1), CommandElement(Commands.ASSIGN),
            VariableElement("b"), CommandElement(Commands.INITIALIZE), VariableElement("b"),
            ConstantElement(2), CommandElement(Commands.ASSIGN))
    )
}
