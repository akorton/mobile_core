import blocks.*
import kotlin.test.assertEquals

fun simple_condition_test(ifFlVal: Boolean, elseIfFlVal: Boolean) {
    val program = listOf<Block>(
        InitializeBlock("a"),
        IfBlock(if (ifFlVal) "1" else "0"),
        AssignmentBlock("a", "1"),
        CommandBlock(Commands.IF_BODY_END),
        ElseIfBlock(if (elseIfFlVal) "1" else "0"),
        AssignmentBlock("a", "2"),
        CommandBlock(Commands.IF_BODY_END),
        ElseBlock(),
        AssignmentBlock("a","3"),
        CommandBlock(Commands.IF_BODY_END),
        CommandBlock(Commands.IF_END)
    )

    val inversePolishNotation = program.map { it.toPolishNotation() }.flatten()

    val interpreter = Interpreter()
    interpreter.run(inversePolishNotation)

    var aValue: Int

    if (ifFlVal) aValue = 1
    else if (elseIfFlVal) aValue = 2
    else aValue = 3

    val expectedVariableTable = mutableMapOf<String, Int>(
        "a" to aValue
    )

    assertEquals(expectedVariableTable, interpreter.variablesTable)
    assert(interpreter.buffer.isEmpty())
}