package io.github.some_example_name.input;

public interface ControllerState {
    void keyDown(Command command);
    default void keyUp(Command command) {

    }
}
