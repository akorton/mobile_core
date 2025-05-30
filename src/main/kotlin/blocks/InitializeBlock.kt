package blocks

import Commands
import notation.NotationElement
import notation.CommandElement
import notation.VariableElement

class InitializeBlock(var variableName: String): Block() {

    override fun toPolishNotation(): List<NotationElement> {
        return listOf(VariableElement(variableName), CommandElement(Commands.INITIALIZE))
    }
}