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

public class Menu {

	private int STATE;
	private GUI menu;
	
	private View view;
	
	public Menu(View view) {
		STATE = 0;
			
		this.view = view;

		FontType font = new FontType(Loader.loadTexture("fonts/segoeUI"), new File("res/fonts/segoeUI.fnt"));


		menu = new GUI(new Vector2f(0, 0), new Vector2f(1, 1));
		
		GUIText mtext = new GUIText("Pentago", 3f, font, new Vector2f(0f, 0f), 1f, true);
		mtext.setColor(1, 1, 1);

		menu.addText(mtext);
		
		GUIText mcont = new GUIText("Press [Enter] to continue!", 3f, font, new Vector2f(0f, 0.75f), 1f, true);
		mcont.setColor(1, 1, 1);

		menu.addText(mcont);

		//guis.add(new GUIImage("gui/black", new Vector2f(0, 0), new Vector2f(1, 1)));

		menu.addImage("gui/black", new Vector2f(0, 0), new Vector2f(1, 1));
	}
	
	public void renderCurrent() {
		switch(STATE) {
		case 0:
			renderMenu();
		}
	}
	
	public void renderMenu() {

		GUIImage.render(menu.getImages());
		TextMaster.render(menu.getRenderMap());
		DisplayManager.updateDisplay();
	}
}
