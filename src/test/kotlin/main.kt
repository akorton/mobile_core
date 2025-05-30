import kotlin.math.pow

fun main() {
    assignment_test_variable_already_exists()
    assignment_test_variable_not_initialized()
    assignment_test_success()
    println("Assignment tests passed!")
    simple_condition_test(true, false)
    simple_condition_test(true, true)
    simple_condition_test(false, true)
    simple_condition_test(false, false)

    for (i in 0..<(1 shl 6)) {
        val flags = mutableListOf<String>()

        for (j in 0..5) {
            if (i and (1 shl j) != 0) flags.add("1")
            else flags.add("0")
        }

        nested_condition_test(flags)
    }
    println("Condition tests passed!")
}