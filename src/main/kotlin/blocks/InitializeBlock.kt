package blocks

import Commands
import buffer.BufferElement
import buffer.CommandElement
import buffer.VariableElement

class InitializeBlock(var variableName: String): Block() {

    override fun toPolishNotation(): List<BufferElement> {
        return listOf(VariableElement(variableName), CommandElement(Commands.INITIALIZE))
    }
}