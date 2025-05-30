package blocks

import buffer.BufferElement

abstract class Block {

    abstract fun toPolishNotation(): List<BufferElement>
}