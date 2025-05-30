package blocks

import notation.NotationElement

class ElseIfBlock(expression: String): IfBlock(expression) {

    override fun toPolishNotation(): List<NotationElement> {
        var notation = super.toPolishNotation()
        return notation.subList(1, notation.size)
    }
}