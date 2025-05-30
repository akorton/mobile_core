import blocks.AssignmentBlock
import blocks.Block
import blocks.InitializeBlock
import exceptions.VariableAlreadyExistsException
import exceptions.VariableIsNotInitialized
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

fun assignment_test_success() {
    val program = listOf<Block>(
        InitializeBlock("a"),
        AssignmentBlock("a", "36"),
        InitializeBlock("b"),
        AssignmentBlock("b", "75"),
    )

    val inversePolishNotation = program.map { it.toPolishNotation() }.flatten()

    val interpreter = Interpreter()
    interpreter.run(inversePolishNotation)

    val expectedVariableTable = mutableMapOf<String, Int>(
        "a" to 36,
        "b" to 75
    )

    assertEquals(expectedVariableTable, interpreter.variablesTable)
    assert(interpreter.buffer.isEmpty())
}

fun assignment_test_variable_already_exists() {
    val program = listOf<Block>(
        InitializeBlock("a"),
        InitializeBlock("a"),
    )

    val inversePolishNotation = program.map { it.toPolishNotation() }.flatten()

    assertThrows<VariableAlreadyExistsException> {  Interpreter().run(inversePolishNotation) }
}

fun assignment_test_variable_not_initialized() {
    val program = listOf<Block>(
        InitializeBlock("b"),
        AssignmentBlock("a", "5"),
        InitializeBlock("a"),
    )

    val inversePolishNotation = program.map { it.toPolishNotation() }.flatten()

    assertThrows<VariableIsNotInitialized> {  Interpreter().run(inversePolishNotation) }
}
