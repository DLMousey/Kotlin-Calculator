import javafx.stage.Stage
import tornadofx.*

class Application: App() {
    // Set the primary view to be our Calculator class, should open on startup
    override val primaryView = Calculator::class

    // Override the start method which is our boot sequence
    override fun start(stage: Stage) {
        importStylesheet("/style.css")
        stage.isResizable = true
        super.start(stage)
    }
}