package blocks

import Commands
import notation.CommandElement
import notation.NotationElement

class CommandBlock(var command: Commands): Block() {

    override fun toPolishNotation(): List<NotationElement> {
        return listOf(CommandElement(command))
    }
}