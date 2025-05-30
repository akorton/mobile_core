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
                    Commands.IF_BODY_START -> {
                        val fl = buffer.removeLast() as ConstantElement

                        // if fl == 1 then just go into the if body which is the next block in program
                        if (fl.value == 0) {
                            idx = skipIfBody(idx, inversePolishNotation)
                        }
                    }
                    Commands.IF_BODY_END -> {
                        // If we are here then if condition was true, so we need to skip through
                        // all the if-else and else

                        idx = skipUntilIfEnd(idx, inversePolishNotation)
                    }
                    Commands.IF_END -> {
                        // nothing to do, this is just indicator block for skipping through else-if and else
                    }
                }
            } else {
                buffer.add(currentElement)
            }

            idx++
        }
    }

    private fun skipIfBody(idx: Int, program: List<NotationElement>): Int {
        var curIdx = idx
        while (curIdx != program.size) {
            if (program[curIdx] is CommandElement && (program[curIdx] as CommandElement).command == Commands.IF_BODY_END) {
                return curIdx
            }

            curIdx++
        }

        return curIdx
    }

    private fun skipUntilIfEnd(idx: Int, program: List<NotationElement>): Int {
        var curIdx = idx
        while (curIdx != program.size) {
            if (program[curIdx] is CommandElement && (program[curIdx] as CommandElement).command == Commands.IF_END) {
                return curIdx
            }

            curIdx++
        }

        return curIdx
    }
}