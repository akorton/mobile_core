package notation

import Commands

class CommandElement(val command: Commands): NotationElement() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CommandElement

        return command == other.command
    }

    override fun hashCode(): Int {
        return command.hashCode()
    }
}