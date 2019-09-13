import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox;
import javafx.scene.control.Button
import tornadofx.*
import Operator.*;

class Calculator : View() {
    override val root: VBox by fxml()

    // Use lateinit to ensure this var isn't set before it's been initialised
    @FXML lateinit var display: Label

    init {
        // Set application title
        title = "KT Calc"

        // Grab all button elements (not class button?) and attach a mouse click
        // handler to them, this will cast the button text into a string and send
        // it onto the operator() method which will then work out what it needs to do with it
        root.lookupAll(".button").forEach { b ->
            b.setOnMouseClicked {
                operator((b as Button).text)
            }
        }

        // Handle keyboard input, if the return key is pushed we'll remap this to an equals input
        root.addEventFilter(KeyEvent.KEY_TYPED) {
            operator(it.character.toUpperCase().replace("\r", "="))
        }
    }

    // The current state of the calculator, this will be updated by the operator method on input
    var state: Operator = Addition(0)

    // This method is called by the callbacks applied to each button on the calculator within operator(),
    // the state is set to the method that needs to be called when "equals" is hit and the display text
    // is emptied while waiting for the second part of the calculation
    fun onAction(fn: Operator) {
        state = fn
        display.text = ""
    }

    // The value for the displayValue shown on the calculator
    val displayValue: Long
        get() = when(display.text) {
            "" -> 0
            else -> display.text.toLong()
        }

    // Figure out what action should be executed in response to a button input,
    // Once this has been figured out it'll be passed to the onAction method which basically
    // queues up the operations ready to go when the user hits equals.
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