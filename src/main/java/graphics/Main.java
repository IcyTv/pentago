package graphics;

import java.io.IOException;

import core.Constants;
import core.WindowManager;
import core.renderEngine.DisplayManager;

public class Main extends WindowManager {

	private View view;

	public Main() {
		view = new View(this);
		super.scenes.add(view);
		System.out.println(super.scenes);
	}

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
