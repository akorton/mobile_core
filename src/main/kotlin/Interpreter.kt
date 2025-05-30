import exceptions.DivisionByZeroException
import exceptions.IllegalVariableNameException
import exceptions.VariableAlreadyExistsException
import exceptions.VariableIsNotInitialized
import notation.CommandElement
import notation.ConstantElement
import notation.NotationElement
import notation.VariableElement

class Interpreter {
    val variablesTable = mutableMapOf<String, Int>()
    val buffer = mutableListOf<NotationElement>()
    var outputStream = ""

    fun run(inversePolishNotation: List<NotationElement>) {
        // TODO
        // mistake to block
        var idx = 0

        while (idx < inversePolishNotation.size) {
            val currentElement = inversePolishNotation[idx]

            if (currentElement is CommandElement) {
                val currentCommand = currentElement.command

                when (currentCommand) {
                    Commands.INITIALIZE -> {
                        val variable = buffer.removeLast() as VariableElement

                        if (variablesTable.containsKey(variable.variableName)) {
                            throw VariableAlreadyExistsException()
                        }

                        if (!VariableElement.checkName(variable.variableName)) {
                            throw IllegalVariableNameException()
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
                            idx = skipUntilWithBalance(idx, inversePolishNotation, Commands.IF_BODY_END,
                                Commands.IF_START, Commands.IF_END, false)
                        }
                    }
                    Commands.IF_BODY_END -> {
                        // If we are here then if condition was true, so we need to skip through
                        // all the if-else and else

                        idx = skipUntilWithBalance(idx, inversePolishNotation, Commands.IF_END,
                            Commands.IF_START, Commands.IF_END, false)
                    }
                    Commands.IF_END -> {
                        // nothing to do, this is just indicator block for skipping through else-if and else
                    }
                    Commands.IF_START -> {
                        // nothing to do, this is just indicator block for skipping through else-if and else
                    }
                    Commands.WHILE_START -> {
                        // nothing to do, this is just indicator block for skipping back to start of while
                    }
                    Commands.WHILE_CHECK_CONDITION -> {
                        val fl = buffer.removeLast() as ConstantElement

                        // if fl == 1 then just go into the if body which is the next block in program
                        if (fl.value == 0) {
                            idx = skipUntilWithBalance(idx, inversePolishNotation, Commands.WHILE_END,
                                Commands.WHILE_START, Commands.WHILE_END, false)
                        }
                    }
                    Commands.WHILE_END -> {
                        // if we are here we need to go back to the start of while

                        idx = skipUntilWithBalance(idx - 1, inversePolishNotation, Commands.WHILE_START,
                            Commands.WHILE_START, Commands.WHILE_END, true)
                    }
                    Commands.OP_UNARY_MINUS -> {
                        val value = buffer.removeLast() as ConstantElement
                        buffer.add(ConstantElement(-value.value))
                    }
                    Commands.OP_PLUS -> {
                        val right = (buffer.removeLast() as ConstantElement).value
                        val left = (buffer.removeLast() as ConstantElement).value

                        buffer.add(ConstantElement(right + left))
                    }
                    Commands.OP_MINUS -> {
                        val right = (buffer.removeLast() as ConstantElement).value
                        val left = (buffer.removeLast() as ConstantElement).value

                        buffer.add(ConstantElement(left - right))
                    }
                    Commands.OP_MULTIPLY -> {
                        val right = (buffer.removeLast() as ConstantElement).value
                        val left = (buffer.removeLast() as ConstantElement).value

                        buffer.add(ConstantElement(right * left))
                    }
                    Commands.OP_DIV -> {
                        val right = (buffer.removeLast() as ConstantElement).value
                        val left = (buffer.removeLast() as ConstantElement).value

                        if (right == 0) throw DivisionByZeroException()

                        buffer.add(ConstantElement(left / right))
                    }
                    Commands.OP_MODULO -> {
                        val right = (buffer.removeLast() as ConstantElement).value
                        val left = (buffer.removeLast() as ConstantElement).value

                        if (right == 0) throw DivisionByZeroException()

                        buffer.add(ConstantElement(left % right))
                    }
                    Commands.GET_VALUE -> {
                        val variableName = (buffer.removeLast() as VariableElement).variableName

                        if (!variablesTable.containsKey(variableName)) throw VariableIsNotInitialized()

                        buffer.add(ConstantElement(variablesTable[variableName]!!))
                    }
                    Commands.EQUALS -> {
                        val right = (buffer.removeLast() as ConstantElement).value
                        val left = (buffer.removeLast() as ConstantElement).value

                        buffer.add(ConstantElement((left == right).toInt()))
                    }
                    Commands.NOT_EQUALS -> {
                        val right = (buffer.removeLast() as ConstantElement).value
                        val left = (buffer.removeLast() as ConstantElement).value

                        buffer.add(ConstantElement((left != right).toInt()))
                    }
                    Commands.GREATER -> {
                        val right = (buffer.removeLast() as ConstantElement).value
                        val left = (buffer.removeLast() as ConstantElement).value

                        buffer.add(ConstantElement((left > right).toInt()))
                    }
                    Commands.GREATER_OR_EQUAL -> {
                        val right = (buffer.removeLast() as ConstantElement).value
                        val left = (buffer.removeLast() as ConstantElement).value

                        buffer.add(ConstantElement((left >= right).toInt()))
                    }
                    Commands.LESS -> {
                        val right = (buffer.removeLast() as ConstantElement).value
                        val left = (buffer.removeLast() as ConstantElement).value

                        buffer.add(ConstantElement((left < right).toInt()))
                    }
                    Commands.LESS_OR_EQUAL -> {
                        val right = (buffer.removeLast() as ConstantElement).value
                        val left = (buffer.removeLast() as ConstantElement).value

                        buffer.add(ConstantElement((left <= right).toInt()))
                    }
                    Commands.PRINT -> {
                        val element = (buffer.removeLast() as ConstantElement).value

                        outputStream += element.toString() + "\n"
                    }
                }
            } else {
                buffer.add(currentElement)
            }

            idx++
        }
    }

    private fun skipUntilWithBalance(idx: Int, program: List<NotationElement>, skipUntil: Commands,
                                     increaseBalance: Commands, decreaseBalance: Commands, goBackwards: Boolean): Int {
        var curIdx = idx
        var balance = 0

        while (true) {
            val curElement = program[curIdx]

            if (curElement is CommandElement) {
                if (curElement.command == skipUntil && balance == 0) return curIdx
                else if (curElement.command == increaseBalance) balance++
                else if (curElement.command == decreaseBalance) balance--
            }

            curIdx += (if (goBackwards) -1 else 1)
        }
    }

    private fun Boolean.toInt() = if (this) 1 else 0
}