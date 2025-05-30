package blocks

import notation.CommandElement
import notation.NotationElement
import parser.ExpressionParser

open class IfBlock(var expression: String): Block() {
    override fun toPolishNotation(): List<NotationElement> {
        return ExpressionParser.parseLogical(expression) + listOf(CommandElement(Commands.IF_BODY_START))
    }
}