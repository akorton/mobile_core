package notation

class ConstantElement(var value: Int): NotationElement() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConstantElement

        return value == other.value
    }

    override fun hashCode(): Int {
        return value
    }

    override fun toString(): String {
        return "ConstantElement{value=$value}"
    }
}