package parser

import notation.ConstantElement
import notation.NotationElement

class ExpressionParser {

    companion object {
        fun parseArithmetic(expression: String): List<NotationElement> {
            return listOf(ConstantElement(expression.toInt()))
        }

        fun parseLogical(expression: String): List<NotationElement> {
            return listOf(ConstantElement(expression.toInt()))
        }
    }
}