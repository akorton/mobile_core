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

fun nested_condition_test(flags: List<String>) {
    val program = listOf<Block>(
        InitializeBlock("a"),
        IfBlock(flags[0]),
            IfBlock(flags[1]),
            AssignmentBlock("a", "1"),
            CommandBlock(Commands.IF_BODY_END),
            ElseIfBlock(flags[2]),
            AssignmentBlock("a", "2"),
            CommandBlock(Commands.IF_BODY_END),
            CommandBlock(Commands.IF_END),
        CommandBlock(Commands.IF_BODY_END),
        ElseIfBlock(flags[3]),
        AssignmentBlock("a", "3"),
        CommandBlock(Commands.IF_BODY_END),
        ElseBlock(),
            IfBlock(flags[4]),
            AssignmentBlock("a", "4"),
            CommandBlock(Commands.IF_BODY_END),
            ElseIfBlock(flags[5]),
            AssignmentBlock("a", "5"),
            CommandBlock(Commands.IF_BODY_END),
            ElseBlock(),
            AssignmentBlock("a", "6"),
            CommandBlock(Commands.IF_END),
        CommandBlock(Commands.IF_END),
        InitializeBlock("b"),
        AssignmentBlock("b", "-1")
    )

    val inversePolishNotation = program.map { it.toPolishNotation() }.flatten()

    val interpreter = Interpreter()
    interpreter.run(inversePolishNotation)

    var aValue = 0

    if (flags[0].toInt() == 1) {
        if (flags[1].toInt() == 1) aValue = 1
        else if (flags[2].toInt() == 1) aValue = 2
    } else if (flags[3].toInt() == 1) aValue = 3
    else {
        if (flags[4].toInt() == 1) aValue = 4
        else if (flags[5].toInt() == 1) aValue = 5
        else aValue = 6
    }

    val expectedVariableTable = mutableMapOf<String, Int>(
        "a" to aValue,
        "b" to -1
    )

    assertEquals(expectedVariableTable, interpreter.variablesTable, flags.toString())
    assert(interpreter.buffer.isEmpty())
}
