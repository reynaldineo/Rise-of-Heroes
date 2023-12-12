package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PausedOverlay;
import utils.LoadSave;

public class Playing extends State implements Statemethods {

	private Player player;
	private LevelManager levelManager;
	private PausedOverlay pausedOverlay;
	private boolean paused = false;

	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int lvlTileWide;
	private int maxTileOffset;
	private int maxLvlOffsetX;

	public Playing(Game game) {
		super(game);
		initClasses();
	}

	private void initClasses() {
		levelManager = new LevelManager(game);
		pausedOverlay = new PausedOverlay(this);
		player = new Player(300, 300, (int) (110 * Game.SCALE), (int) (110 * Game.SCALE));
		player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
		lvlTileWide = levelManager.getCurrentLevel().getLevelData()[0].length;
		maxTileOffset = lvlTileWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = maxTileOffset * Game.TILES_SIZE;
	}

	@Override
	public void update() {
		if (!paused) {
			levelManager.update();
			player.update();
			checkCloseToBorder();
		} else
			pausedOverlay.update();

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

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		if (paused)
			pausedOverlay.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			player.setAttacking(true);
		}

	}

	public void mouseDragged(MouseEvent e) {
		if (paused)
			pausedOverlay.mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (paused) {
			pausedOverlay.mousePressed(e);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (paused) {
			pausedOverlay.mouseReleased(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (paused) {
			pausedOverlay.mouseMoved(e);
		}

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

	public void windowFocusLost() {
		player.resetDirBoooleands();
	}

	public Player getPlayer() {
		return player;
	}

	public void unpauseGame() {
		paused = false;
	}

}
