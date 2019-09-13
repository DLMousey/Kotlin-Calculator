import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox;
import javafx.scene.control.Button
import tornadofx.*
import Operator.*;


class Calculator : View() {
    override val root: VBox by fxml()

    @FXML lateinit var display: Label

    init {
        title = "KT Calc"

        root.lookupAll(".button").forEach { b ->
            b.setOnMouseClicked {
                operator((b as Button).text)
            }
        }

        root.addEventFilter(KeyEvent.KEY_TYPED) {
            operator(it.character.toUpperCase().replace("\r", "="))
        }
    }

    var state: Operator = Addition(0)

    fun onAction(fn: Operator) {
        state = fn
        display.text = ""
    }

    val displayValue: Long
        get() = when(display.text) {
            "" -> 0
            else -> display.text.toLong()
        }

    private fun operator(x: String) {
        if (Regex("[0-9]").matches(x)) {
            display.text += x
        } else {
            when(x) {
                "+" -> onAction(Addition(displayValue))
                "-" -> onAction(Subtraction(displayValue))
                "/" -> onAction(Division(displayValue))
                "%" -> { onAction(Addition(displayValue / 100)); operator("=") }
                "x" -> onAction(Multiplication(displayValue))
                "C" -> onAction(Addition(0))
                "+/-" -> { onAction(Addition(-1* displayValue)); operator("=") }
                "=" -> display.text = state.calculate(displayValue).toString()
            }
        }
    }
}