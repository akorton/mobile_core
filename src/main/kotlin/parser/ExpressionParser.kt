package parser

import notation.CommandElement
import notation.ConstantElement
import notation.NotationElement
import notation.VariableElement

class ExpressionParser {

    companion object {
        var expression: String = ""
        var pos = 0
        var tokenType: TokenType = TokenType.END
        var curToken: String = ""

        private fun isDigit(ch: Char): Boolean {
            return ch in '0'..'9'
        }

        private fun isLetter(ch: Char): Boolean {
            return (ch in 'a'..'z') || (ch in 'A'..'Z')
        }

        private fun isDelimiter(ch: Char): Boolean {
            return ch in "+-*/%()[]"
        }

        private fun getToken() {
            while (pos < expression.length && expression[pos] == ' ') pos++

            if (pos == expression.length) {
                tokenType = TokenType.END
                return
            }

            val curChar = expression[pos]
            curToken = ""

            if (isDelimiter(curChar)) {
                curToken = curChar.toString()
                pos++

                when (curChar) {
                    '+' -> tokenType = TokenType.OP_PLUS
                    '-' -> tokenType = TokenType.OP_MINUS
                    '%' -> tokenType = TokenType.OP_MODULO
                    '*' -> tokenType = TokenType.OP_MULTIPLY
                    '/' -> tokenType = TokenType.OP_DIV
                    '(' -> tokenType = TokenType.OPEN_ROUND_BRACKET
                    ')' -> tokenType = TokenType.CLOSE_ROUND_BRACKET
                    '[' -> tokenType = TokenType.OPEN_SQUARE_BRACKET
                    ']' -> tokenType = TokenType.CLOSE_SQUARE_BRACKET
                }

                return
            } else if (isDigit(curChar)) {
                while (pos < expression.length && isDigit(expression[pos])) {
                    curToken += expression[pos]
                    pos++
                }

                tokenType = TokenType.NUMBER
                return
            } else if (isLetter(curChar)) {
                while (pos < expression.length && (isLetter(expression[pos])|| isDigit(expression[pos]))) {
                    curToken += expression[pos]
                    pos++
                }

                tokenType = TokenType.VAR_NAME
                return
            }

            throw ParserError("Token not found at position $pos with char $curChar")
        }

        private fun parseTerm(): MutableList<NotationElement> {
            var ans = parseFactor()

            while (true) {
                when (tokenType) {
                    TokenType.OP_PLUS -> {
                        getToken()
                        ans += parseFactor()
                        ans.add(CommandElement(Commands.OP_PLUS))
                    }
                    TokenType.OP_MINUS -> {
                        getToken()
                        ans += parseFactor()
                        ans.add(CommandElement(Commands.OP_MINUS))
                    }
                    else -> {
                        break
                    }
                }
            }

            return ans
        }

        private fun parseFactor(): MutableList<NotationElement> {
            var ans = parseUnaryMinus()

            while (true) {
                when (tokenType) {
                    TokenType.OP_MULTIPLY -> {
                        getToken()
                        ans += parseUnaryMinus()
                        ans.add(CommandElement(Commands.OP_MULTIPLY))
                    }
                    TokenType.OP_DIV -> {
                        getToken()
                        ans += parseUnaryMinus()
                        ans.add(CommandElement(Commands.OP_DIV))
                    }
                    TokenType.OP_MODULO -> {
                        getToken()
                        ans += parseUnaryMinus()
                        ans.add(CommandElement(Commands.OP_MODULO))
                    }
                    else -> {
                        break
                    }
                }
            }

            return ans
        }

        private fun parseUnaryMinus(): MutableList<NotationElement> {
            var ans = mutableListOf<NotationElement>()

            if (tokenType == TokenType.OP_MINUS) {
                getToken()
                ans = parsePrimitive()
                ans += CommandElement(Commands.OP_UNARY_MINUS)
            } else {
                ans = parsePrimitive()
            }

            return ans
        }

        private fun parsePrimitive(): MutableList<NotationElement> {
            var ans = mutableListOf<NotationElement>()

            when (tokenType) {
                TokenType.NUMBER -> {
                    ans = mutableListOf(ConstantElement(curToken.toInt()))
                    getToken()
                }
                TokenType.VAR_NAME -> {
                    // TODO add arrays
                    ans = mutableListOf(VariableElement(curToken), CommandElement(Commands.GET_VALUE))
                    getToken()
                }
                TokenType.OPEN_ROUND_BRACKET -> {
                    getToken()
                    ans = parseTerm()

                    if (tokenType != TokenType.CLOSE_ROUND_BRACKET) {
                        throw ParserError("Mismatched rounds brackets at pos $pos")
                    }

                    getToken()
                }
                else -> throw ParserError("Unexpected token at pos $pos")
            }

            return ans
        }

        fun parseArithmetic(expression: String): List<NotationElement> {
            this.expression = expression
            this.pos = 0
            this.curToken = ""

            getToken()
            if (tokenType == TokenType.END) throw ParserError("Empty expression")

            val ans = parseTerm()

            if (tokenType != TokenType.END) throw ParserError("Expression end was not reached")

            return ans
        }

        fun parseLogical(expression: String): List<NotationElement> {
            return parseArithmetic(expression)
        }
    }
}