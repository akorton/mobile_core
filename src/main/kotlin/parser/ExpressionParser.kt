package parser

import notation.ConstantElement
import notation.NotationElement

class ExpressionParser {

    companion object {
        fun parse(expression: String): List<NotationElement> {
            return listOf(ConstantElement(expression.toInt()))
        }
    }
}