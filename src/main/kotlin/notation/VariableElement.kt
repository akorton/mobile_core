package notation

class VariableElement(val variableName: String): NotationElement() {

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