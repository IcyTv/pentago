package graphics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import control.Controller;
import control.CustomEvent;
import control.MenuController;
import core.Constants;
import core.Constants.COLOR;
import core.Scene;
import core.WindowManager;
import core.audio.AudioMaster;
import core.entities.Camera;
import core.entities.Entity;
import core.entities.EntityGroup;
import core.entities.Light;
import core.event.CallbackStackInterruption;
import core.event.Queue;
import core.font.Font;
import core.font.FontType;
import core.font.GUIText;
import core.font.TextMaster;
import core.gui.Button;
import core.gui.GUI;
import core.gui.GUIImage;
import core.loaders.Loader;
import core.loaders.OBJFileLoader;
import core.models.RawModel;
import core.models.TexturedModel;
import core.renderEngine.DisplayManager;
import core.textures.ModelTexture;
import model.Gamemaster;
import tools.Maths;

/**
 * Class for displaying the game
 */
public class View extends Scene {
	//Interval for the fps counter refresh
	private static final int FPS_REFRESH_RATE = 20;

	private float distance = 2;
	private int[] currentPanel;
	private EntityGroup<EntityGroup<Entity>> board;
	private Gamemaster master;
	private int fpsCounter;
	private Queue queue;

	private Menu menu;
	
	private Controller controller;
	private MenuController menuController;
	
	/**
	 * Constructor for View Class
	 * 
	 * @param manager theoretically used for switching windows. NOT IMPLEMENTED
	 */
	public View(WindowManager manager) {
		super(manager);
		master = new Gamemaster(9, 3, 4, new boolean[] { true, true, true, true });
		queue = new Queue();
		controller = new Controller(this);
	}

	/*
	 * Initialize function, called just before starting the game. Used to load assets
	 */
	public void init() {
		FontType font = new FontType(Loader.loadTexture("fonts/segoeUI"), new File("res/fonts/segoeUI.fnt"));
		
		GUIText text = new GUIText("This is some text!", 3f, font, new Vector2f(0f, 0f), 1f, true);
		text.setColor(1, 1, 1);
		
		mainGUI.addText(text);

		GUIText fps = new GUIText("", 2f, font, new Vector2f(0, 0), 1, false);
		fps.setColor(1, 0, 0);

		mainGUI.addText(fps);
		
		Light sun = new Light(new Vector3f(0, 1000, -700), Maths.rgbToVector(255, 255, 255));
		super.lights.add(sun);
		AudioMaster.setDistanceAttenuationMethod(1, true);

		board = new EntityGroup<EntityGroup<Entity>>(new Vector3f(0, 0, 0), master.getBSize() * master.getBSize());

		RawModel pieceModel = Loader.loadToVAO(OBJFileLoader.loadOBJ("entities/pentagoPiece"));
		RawModel ballModel = Loader.loadToVAO(OBJFileLoader.loadOBJ("entities/ball"));

		ModelTexture pieceTexture = new ModelTexture(Loader.loadTexture("entities/pentagoPiece"));
		ModelTexture ballTexture = new ModelTexture(Loader.loadTexture("entities/ball"));

		TexturedModel piece = new TexturedModel(pieceModel, pieceTexture);
		TexturedModel ball = new TexturedModel(ballModel, ballTexture);

		for (int i = 0; i < board.size(); i++) {
			EntityGroup<Entity> tmp = new EntityGroup<Entity>(
					new Vector3f(master.getBSize() * distance - (i % master.getBSize()) * distance, 0,
							master.getBSize() * distance - (i / master.getBSize()) * distance),
					2);
			Entity pieceE = new Entity(piece,
					new Vector3f(master.getBSize() * distance - (i % master.getBSize()) * distance, 0,
							master.getBSize() * distance - (i / master.getBSize()) * distance),
					1f);
			pieceE.setColor(COLOR.BLUE);
			tmp.set(pieceE, 0, false);
			Entity ballE = new Entity(ball,
					new Vector3f(master.getBSize() * distance - (i % master.getBSize()) * distance, 1.2f,
							master.getBSize() * distance - (i / master.getBSize()) * distance),
					1f);
			ballE.setHidden(true);
			tmp.set(ballE, 1, false);
			board.set(tmp, i, false);

		}

		for (EntityGroup<Entity> var : board.getEntities()) {
			for (Entity lel : var.getEntities()) {
				super.entities.add(lel);
			}
		}

		super.camera = new Camera(board.getEntities()
				.get((master.getBSize() / 2) * master.getBSize() + master.getBSize() / 2).getEntity(0));

		addCallback(controller);


		// Menu init
		
		menu = new Menu(this);
		
		
		menuController = new MenuController();
		addCallback(menuController);

	}

	/*
	 * Function that gets called every tick `(Constants.TICK_RATE)` per second 
	 */
	@Override
	public void tickGame() {
		if (Constants.state == Constants.STATE.MENU) {
			menu.renderCurrent();
		} else {
			super.camera.move();
			if (fpsCounter++ >= FPS_REFRESH_RATE) {
				mainGUI.getTexts().get(1).setText("" + (int) (1 / DisplayManager.getFrameTimeSeconds()));
				fpsCounter = 0;
			}
			if (!queue.isEmpty()) {
				try {
					queue.poll().invoke(new CustomEvent(this));
				} catch (CallbackStackInterruption e) {
					e.printStackTrace();
				}
			}

			if (currentPanel != null) {
				int pX = currentPanel[0];
				int pY = currentPanel[1];

				for (int i = 0; i < master.getPSize(); i++) {
					for (int n = 0; n < master.getPSize(); n++) {
						int x = pX * master.getPSize() + i;
						int y = pY * master.getPSize() + n;
						board.getEntity(y * master.getBSize() + x).getEntity(0).setColor(COLOR.GREEN);
					}
				}
			}

			for (int i = 0; i < master.getBSize(); i++) {
				for (int n = 0; n < master.getBSize(); n++) {
					if (master.get(i, n) != null) {
						Entity ball = board.getEntity(i + n * master.getBSize()).getEntity(1);
						ball.setHidden(false);
						ball.setColor(master.get(i, n).getColor());
					} else {
						board.getEntity(i + n * master.getBSize()).getEntity(1).setHidden(true);
					}
				}
			}

			GUIText text = mainGUI.getTexts().get(0);
			
			if (!text.getText().equals("Place a piece!") && master.currentTurnIsPlaceTurn() && !master.won()) {
				switch (master.getCurrentPlayer().getColor()) {
				case RED:
					text.setColor(1, 0, 0);
					break;
				case GREEN:
					text.setColor(0, 1, 0);
					break;
				case BLUE:
					text.setColor(0, 0, 1);
					break;
				case PURPLE:
					text.setColor(1, 0, 1);
					break;
				}
				text.setText("Place a piece!");
			} else if (!text.getText().equals("Turn a panel!") && !master.currentTurnIsPlaceTurn() && !master.won()) {
				switch (master.getCurrentPlayer().getColor()) {
				case RED:
					text.setColor(1, 0, 0);
					break;
				case GREEN:
					text.setColor(0, 1, 0);
					break;
				case BLUE:
					text.setColor(0, 0, 1);
					break;
				case PURPLE:
					text.setColor(1, 0, 1);
					break;
				}
				text.setText("Turn a panel!");
			} else if (master.won() && !text.getText().equals("Won!")) {
				switch (master.getCurrentPlayer().getColor()) {
				case RED:
					text.setColor(1, 0, 0);
					break;
				case GREEN:
					text.setColor(0, 1, 0);
					break;
				case BLUE:
					text.setColor(0, 0, 1);
					break;
				case PURPLE:
					text.setColor(1, 0, 1);
					break;
				}
				text.setText(master.getWinner().getName() + " has won!");
			}

			Light.sort(super.lights, super.camera);
			super.render();
		}

	}

	/*
	 * Used to reset Colors of the board
	 */
	public void resetBoardColor() {
		if (currentPanel != null) {
			int pX = currentPanel[0];
			int pY = currentPanel[1];

			for (int i = 0; i < master.getPSize(); i++) {
				for (int n = 0; n < master.getPSize(); n++) {
					int x = pX * master.getPSize() + i;
					int y = pY * master.getPSize() + n;
					board.getEntity(y * master.getBSize() + x).getEntity(0).setColor(COLOR.BLUE);
				}
			}
		}
	}

	/*
	 * Used for cleaning up. Called after closing game
	 */
	@Override
	public void cleanUp() {
	}

	/*
	 * Returns gamemaster
	 * @return master
	 */
	public Gamemaster getMaster() {
		return master;
	}

	/**
	 * @return Current panel location
	 */
	public int[] getCurrentPanel() {
		return currentPanel;
	}

	/**
	 * @param cP current panel location (format: int[]{x, y})
	 */
	public void setCurrentPanel(int[] cP) {
		currentPanel = cP;
	}

	/**
	 * @return current Queue
	 */
	public Queue getQueue() {
		return queue;
	}

	/**
	 * @return controller
	 */
	public Controller getController() {
		return controller;
	}

}