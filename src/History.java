import java.util.Stack;

public class History {
	public WorkSpace ws;
	private Stack<Command> undoStack = new Stack<Command>();
	private Stack<Command> redoStack = new Stack<Command>();

	public void execute(final Command cmd) {
		if (!undoStack.contains(cmd))
			undoStack.push(cmd);
	 	cmd.execute();
		ws.Undo.setDisable(false);
	}

	public void undo() {
		if (!undoStack.isEmpty()) {
			Command cmd = undoStack.pop();
			cmd.undo();
			redoStack.push(cmd);
			ws.Redo.setDisable(false);
			if (undoStack.isEmpty()) {
				ws.Undo.setDisable(true);
			}
		}
	}

	public void redo() {
		if (!redoStack.isEmpty()) {
			Command cmd = redoStack.pop();
			cmd.execute();
			undoStack.push(cmd);
			ws.Undo.setDisable(false);
			if (redoStack.isEmpty()) {
				ws.Redo.setDisable(true);
			}

		}

	}
}
