package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.LevelCompletedOverlay;
import ui.PausedOverlay;
import utils.LoadSave;

public class Playing extends State implements Statemethods {

	private Player player;
	private LevelManager levelManager;
	private ObjectManager objectManager;
	private PausedOverlay pausedOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private boolean paused = false;
	private boolean lvlCompleted = false;

	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int lvlTileWide;
	private int maxTileOffset;
	private int maxLvlOffsetX;

	private int attackTick = 0;

	public Playing(Game game) {
		super(game);
		initClasses();
		calcLvlOffset();
		loadStartLevel();
	}

	private void initClasses() {

		levelManager = new LevelManager(game);
		objectManager = new ObjectManager(this);

		player = new Player(200, 300, (int) (110 * Game.SCALE), (int) (110 * Game.SCALE), this);
		player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

		pausedOverlay = new PausedOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
	}

	@Override
	public void update() {
		if (paused)
			pausedOverlay.update();
		else if (lvlCompleted)
			levelCompletedOverlay.update();
		else {
			levelManager.update();
			objectManager.update();
			player.update();
			checkCloseToBorder();
		}

	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		objectManager.draw(g, xLvlOffset);
		if (paused) {
			g.setColor(new Color(0, 0, 0, 140));
			g.fillRect(0, 0, game.GAME_WIDTH, game.GAME_HEIGHT);
			pausedOverlay.draw(g);
		} else if (lvlCompleted) {
			g.setColor(new Color(0, 0, 0, 140));
			g.fillRect(0, 0, game.GAME_WIDTH, game.GAME_HEIGHT);
			levelCompletedOverlay.draw(g);
		}
	}

	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
		calcLvlOffset();
	}

	private void loadStartLevel() {
		objectManager.loadObjects(levelManager.getCurrentLevel());
	}

	private void calcLvlOffset() {
		maxLvlOffsetX = levelManager.getCurrentLevel().getMaxLvlOffsetX();
	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;

		if (diff < leftBorder)
			xLvlOffset += diff - leftBorder;
		else if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;

		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;

	}

	public void checkPotionTouched(Rectangle2D.Float hitbox) {
		objectManager.checkObjectTouched(hitbox);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			player.setAttacking(true);
			attackTick++;
			if (attackTick >= 7) {
				attackTick = 0;
				lvlCompleted = true;
			}
		}

	}

	public void mouseDragged(MouseEvent e) {
		if (paused)
			pausedOverlay.mouseDragged(e);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (paused)
			pausedOverlay.mousePressed(e);
		else if (lvlCompleted)
			levelCompletedOverlay.mousePressed(e);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (paused)
			pausedOverlay.mouseReleased(e);
		else if (lvlCompleted)
			levelCompletedOverlay.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (paused)
			pausedOverlay.mouseMoved(e);
		else if (lvlCompleted)
			levelCompletedOverlay.mouseMoved(e);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(true);
				break;
			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(true);
				break;
			case KeyEvent.VK_W:
				player.setJump(true);
				break;
			case KeyEvent.VK_BACK_SPACE:
				Gamestate.state = Gamestate.MENU;
				break;
			case KeyEvent.VK_ESCAPE:
				paused = !paused;
				break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				break;
			case KeyEvent.VK_W:
				player.setJump(false);
				break;
		}

	}

	public void resetAll() {
		unpauseGame();
		lvlCompleted = false;
		player.resetAll();
		objectManager.resetAll();
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}

	public void unpauseGame() {
		paused = false;
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public void checkObjectHit(Rectangle2D.Float attackBox) {
		objectManager.checkObjectHit(attackBox);
	}

	public void checkSpikeTouched(Float hitbox) {
		objectManager.checkSpikesTouched(hitbox);
	}

	public LevelManager getLevelManager() {
		return levelManager;
	}

}
