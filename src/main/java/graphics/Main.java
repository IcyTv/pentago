package graphics;

import java.io.IOException;

import core.Constants;
import core.WindowManager;
import core.renderEngine.DisplayManager;

/**
 * The Class Main.
 */
public class Main extends WindowManager {

	/** The view. */
	private View view;

	/**
	 * Main Class.
	 */
	public Main() {
		view = new View(this);
		super.scenes.add(view);
		System.out.println(super.scenes);
	}

	/**
	 * Main Method.
	 *
	 * @param args the arguments
	 * @throws SecurityException a security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws SecurityException, IOException {

		// RUN CONFIG FOR DEBUGGING PURPOSES:
		// -javaagent:/home/michael/eclipse-workspace/engine/libs/debug/lwjglx-debug-1.0.0.jar

		System.setProperty("org.lwjgl.util.Debug", "true");
		DisplayManager.createDisplay();
		Constants.state = Constants.STATE.MENU;
		Main scene = new Main();
		scene.toScene(0);
		DisplayManager.closeDisplay();
	}
}
