package graphics;

import java.io.File;

import org.joml.Vector2f;

import control.MenuController;
import core.WindowManager;
import core.font.FontType;
import core.font.GUIText;
import core.gui.Menu;
import core.loaders.Loader;

public class MainMenu extends Menu {

	private GUIText text;
	private GUIText cont;
	
	private MenuController controller;
	
	public MainMenu(WindowManager manager) {
		super(manager);
		
		controller = new MenuController(manager);
	}

	@Override
	public void init() {
		FontType font = new FontType(Loader.loadTexture("fonts/segoeUI"), new File("res/fonts/segoeUI.fnt"));
        text = new GUIText("Pentago", 3f, font, new Vector2f(0f, 0f), 1f, true);
        text.setColor(1, 1, 1);
        
        cont = new GUIText("Press [Enter] to continue!", 3f, font, new Vector2f(0f, 0.75f), 1f, true);
        cont.setColor(1, 1, 1);
        
        addCallback(controller);
	}

	@Override
	public void tick() {
		super.render();
		
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

}
