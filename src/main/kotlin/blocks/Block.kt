package blocks

import notation.NotationElement

abstract class Block {

    abstract fun toPolishNotation(): List<NotationElement>
}