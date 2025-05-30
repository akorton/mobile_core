package blocks

import notation.NotationElement
import notation.CommandElement
import notation.VariableElement
import parser.ExpressionParser

class AssignmentBlock(var variableName: String, var expression: String): Block() {

    override fun toPolishNotation(): List<NotationElement> {
        return listOf(VariableElement(variableName)) + ExpressionParser.parse(expression) +
                listOf(CommandElement(Commands.ASSIGN))
    }
}