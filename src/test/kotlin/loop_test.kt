import blocks.*
import kotlin.test.assertEquals

fun while_false_test() {
    val program = listOf<Block>(
        InitializeBlock("a"),
        WhileBlock("0"),
            AssignmentBlock("a", "1"),
        CommandBlock(Commands.WHILE_END),
    )

    val inversePolishNotation = program.map { it.toPolishNotation() }.flatten()

    val interpreter = Interpreter()
    interpreter.run(inversePolishNotation)

    val expectedVariableTable = mutableMapOf<String, Int>(
        "a" to 0
    )

    assertEquals(expectedVariableTable, interpreter.variablesTable)
    assert(interpreter.buffer.isEmpty())
}
