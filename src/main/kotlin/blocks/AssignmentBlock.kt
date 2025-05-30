package blocks

import buffer.BufferElement
import buffer.CommandElement
import buffer.VariableElement
import parser.ExpressionParser

class AssignmentBlock(var variableName: String, var expression: String): Block() {

    override fun toPolishNotation(): List<BufferElement> {
        return listOf(VariableElement(variableName)) + ExpressionParser.parse(expression) +
                listOf(CommandElement(Commands.ASSIGN))
    }
}