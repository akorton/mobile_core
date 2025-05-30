package blocks

import notation.NotationElement
import parser.ExpressionParser

class WhileBlock(var expression: String): Block() {

    override fun toPolishNotation(): List<NotationElement> {
        return CommandBlock(Commands.WHILE_START).toPolishNotation() +
                ExpressionParser.parseLogical(expression) +
                CommandBlock(Commands.WHILE_CHECK_CONDITION).toPolishNotation()
    }
}