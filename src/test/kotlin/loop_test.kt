import blocks.*
import notation.VariableElement
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

fun fizz_buzz_test() {
    val program = listOf<Block>(
        InitializeBlock("i"),
        WhileBlock("i < 16"),
            IfBlock("i % 15 == 0"),
                PrintBlock(VariableElement("i")),
                PrintBlock(VariableElement("i")),
                PrintBlock(VariableElement("i")),
                CommandBlock(Commands.IF_BODY_END),
            ElseIfBlock("i % 5 == 0"),
                PrintBlock(VariableElement("i")),
                PrintBlock(VariableElement("i")),
                CommandBlock(Commands.IF_BODY_END),
            ElseIfBlock("i % 3 == 0"),
                PrintBlock(VariableElement("i")),
                CommandBlock(Commands.IF_BODY_END),
            CommandBlock(Commands.IF_END),
        AssignmentBlock("i", "i + 1"),
        CommandBlock(Commands.WHILE_END),
    )

    val inversePolishNotation = program.map { it.toPolishNotation() }.flatten()

    val interpreter = Interpreter()
    interpreter.run(inversePolishNotation)

    val expectedVariableTable = mutableMapOf<String, Int>(
        "i" to 16
    )

    val expectedOutput = "0\n" +
            "0\n" +
            "0\n" +
            "3\n" +
            "5\n" +
            "5\n" +
            "6\n" +
            "9\n" +
            "10\n" +
            "10\n" +
            "12\n" +
            "15\n" +
            "15\n" +
            "15\n"

    assertEquals(expectedVariableTable, interpreter.variablesTable)
    assert(interpreter.buffer.isEmpty())
    assertEquals(expectedOutput, interpreter.outputStream)
}
