package blocks

import notation.CommandElement
import notation.NotationElement
import parser.ExpressionParser

open class IfBlock(var expression: String): Block() {
    override fun toPolishNotation(): List<NotationElement> {
        return CommandBlock(Commands.IF_START).toPolishNotation() +
                ExpressionParser.parseLogical(expression) +
                CommandBlock(Commands.IF_BODY_START).toPolishNotation()
    }
}