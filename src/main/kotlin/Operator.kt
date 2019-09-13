sealed class Operator(val x: Long) { // constructor inline?
    abstract fun calculate(y: Long): Long // define an abstract function any sub classes must override

    class Addition(x: Long) : Operator(x) {
        override fun calculate(y: Long): Long {
            return x + y
        }
    }

    class Subtraction(x: Long) : Operator(x) {
        override fun calculate(y: Long): Long {
            return x - y
        }
    }

    class Multiplication(x: Long) : Operator(x) {
        override fun calculate(y: Long) : Long {
            return x * y
        }
    }

    class Division(x: Long) : Operator(x) {
        override fun calculate(y: Long): Long {
            return x / y
        }
    }
}