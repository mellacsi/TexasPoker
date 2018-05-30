package ch.supsi.texas;

import javafx.scene.control.TextArea;

public class ConsoleMediatorTextArea {

    private TextArea textArea = new TextArea();


    public void setId(String id) {
        this.textArea.setId(id);
    }

    public void setEditable(boolean editable) {
        this.textArea.setEditable(editable);
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void clear() {
        this.textArea.clear();
    }

    public void appendText(String s) {
        textArea.appendText(s);
    }
}
