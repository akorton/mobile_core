package blocks

import notation.CommandElement
import notation.NotationElement

class ElseBlock: Block() {
    override fun toPolishNotation(): List<NotationElement> {
        return listOf()
    }
}