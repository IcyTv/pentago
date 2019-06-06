package graphics;

import java.io.File;

import org.joml.Vector2f;

import core.font.FontType;
import core.font.GUIText;
import core.font.TextMaster;
import core.gui.GUI;
import core.gui.GUIImage;
import core.loaders.Loader;
import core.renderEngine.DisplayManager;

/**
 * The Class Menu.
 *	Class for storing GUIs
 */
public class Menu {

	/** The menu. */
	private GUI menu;
	
	/** The tutorial 1. */
	private GUI tutorial1; //Used for selecting panels
	
	/** The tutorial 2. */
	private GUI tutorial2; //Setting pieces
	
	/** The tutorial 3. */
	private GUI tutorial3; //Rotating Panels

	/** The view. */
	private View view;

	/**
	 * Constructor for GUI storage.
	 *
	 * @param view the view
	 */
	public Menu(View view) {

		this.view = view;

		FontType font = new FontType(Loader.loadTexture("fonts/segoeUI"), new File("res/fonts/segoeUI.fnt"));

		menu = new GUI(new Vector2f(0, 0), new Vector2f(1, 1));

		GUIText mtext = new GUIText("Pentago", 3f, font, new Vector2f(0f, 0f), 1f, true);
		mtext.setColor(1, 1, 1);

		menu.addText(mtext);

		GUIText mcont = new GUIText("Press [Enter] to continue!", 3f, font, new Vector2f(0f, 0.75f), 1f, true);
		mcont.setColor(1, 1, 1);

		menu.addText(mcont);

		menu.addImage("gui/black", new Vector2f(0, 0), new Vector2f(1, 1));
	
	
		tutorial1 = new GUI(new Vector2f(0, 0), new Vector2f(1, 1));
	
		GUIText tmp = new GUIText("Use the Numpad to control the game", 3f, font, new Vector2f(0, 0), 1, true);
		tmp.setColor(0, 0, 0);
		
		tutorial1.addText(tmp);
		
		tutorial1.addImage("gui/tutorial1", new Vector2f(0, 0), new Vector2f(1, 1));
		
		
		tutorial2 = new GUI(new Vector2f(0, 0), new Vector2f(1, 1));
		
		GUIText tmp2 = new GUIText("Use the Numpad to place your piece", 3f, font, new Vector2f(0, 0), 1, true);
		tmp2.setColor(0, 0, 0);
		
		tutorial2.addText(tmp2);
		
		tutorial2.addImage("gui/tutorial3", new Vector2f(0, 0), new Vector2f(1, 1));
		
		
		tutorial3 = new GUI(new Vector2f(0, 0), new Vector2f(1, 1));
		
		GUIText tmp3 = new GUIText("Use the Numpad 4 or 6 to rotate the board left or right", 3f, font, new Vector2f(0, 0), 1, true);
		tmp2.setColor(0, 0, 0);
		
		tutorial3.addText(tmp3);
		tutorial3.addImage("gui/tutorial2", new Vector2f(0, 0), new Vector2f(1, 1));
	}

	/**
	 * Renders current GUI.
	 */
	public void renderCurrent() {
		switch (view.getTutorialStage()) {
		case 0:
			renderMenu();
			break;
		case 1:
			renderTutorial1();
			break;
		case 2:
			renderTutorial2();
			break;
		case 3:
			renderTutorial3();
			break;
		}
	}

	/**
	 * Render menu.
	 */
	public void renderMenu() {
		GUIImage.render(menu.getImages());
		TextMaster.render(menu.getRenderMap());
		DisplayManager.updateDisplay();
	}

	/**
	 * Render tutorial 1.
	 */
	public void renderTutorial1() {
		GUIImage.render(tutorial1.getImages());
		TextMaster.render(tutorial1.getRenderMap());
		DisplayManager.updateDisplay();
	}

	/**
	 * Render tutorial 2.
	 */
	public void renderTutorial2() {
		GUIImage.render(tutorial2.getImages());
		TextMaster.render(tutorial2.getRenderMap());
		DisplayManager.updateDisplay();
	}
	
	/**
	 * Render tutorial 3.
	 */
	public void renderTutorial3() {
		GUIImage.render(tutorial3.getImages());
		TextMaster.render(tutorial3.getRenderMap());
		DisplayManager.updateDisplay();
	}

}
