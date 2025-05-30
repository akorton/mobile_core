import exceptions.VariableAlreadyExistsException
import exceptions.VariableIsNotInitialized
import notation.CommandElement
import notation.ConstantElement
import notation.NotationElement
import notation.VariableElement

class Interpreter {
    val variablesTable = mutableMapOf<String, Int>()
    val buffer = mutableListOf<NotationElement>()

    fun run(inversePolishNotation: List<NotationElement>) {
        var idx = 0

        while (idx != inversePolishNotation.size) {
            val currentElement = inversePolishNotation[idx]

            if (currentElement is CommandElement) {
                val currentCommand = currentElement.command

                when (currentCommand) {
                    Commands.INITIALIZE -> {
                        val variable = buffer.removeLast() as VariableElement

                        if (variablesTable.containsKey(variable.variableName)) {
                            throw VariableAlreadyExistsException()
                        }

                        variablesTable[variable.variableName] = 0
                    }
                    Commands.ASSIGN -> {
                        val value = buffer.removeLast() as ConstantElement
                        val variable = buffer.removeLast() as VariableElement

                        if (!variablesTable.containsKey(variable.variableName)) {
                            throw VariableIsNotInitialized()
                        }

                        variablesTable[variable.variableName] = value.value
                    }
                }
            } else {
                buffer.add(currentElement)
            }

            idx++
        }
    }
}