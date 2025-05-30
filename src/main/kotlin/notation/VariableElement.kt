package notation

import parser.ExpressionParser

class VariableElement(val variableName: String): NotationElement() {

    companion object {
        fun checkName(variableName: String): Boolean {
            if (variableName.length == 0) return false

            if (!ExpressionParser.isLetter(variableName[0])) return false

            for (i in 1..<variableName.length) {
                if (!(ExpressionParser.isLetter(variableName[i]) || ExpressionParser.isDigit(variableName[i]))) {
                    return false
                }
            }

            return true
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VariableElement

        return variableName == other.variableName
    }

    override fun hashCode(): Int {
        return variableName.hashCode()
    }

    override fun toString(): String {
        return "VariableElement{variableName=$variableName}"
    }
}