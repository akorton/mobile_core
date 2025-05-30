package blocks

import notation.CommandElement
import notation.NotationElement

class PrintBlock(var element: NotationElement): Block() {
    override fun toPolishNotation(): List<NotationElement> {
        return listOf(element, CommandElement(Commands.GET_VALUE), CommandElement(Commands.PRINT))
    }
}