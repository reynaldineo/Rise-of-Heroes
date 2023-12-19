package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestate;
import main.Game;
import main.GamePanel;
import utils.LoadSave;

public class LevelManager {

	private GamePanel gamePanel;
	private Game game;
	private BufferedImage[] levelSprite;
	private BufferedImage[] levelSpriteBackground;
	private Level currentLevel;
	private ArrayList<Level> levels;
	private int lvlIndex = 0;
	private int maxLvl = 1;

	public LevelManager(Game game) {
		this.game = game;
		levels = new ArrayList<>();
		buildAllLevels();
		setCurrentLevel();

	}

	public void draw(Graphics g, int xLvlOffset) {

		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < currentLevel.getLevelDataBG2()[0].length; i++) {
				if (currentLevel.levelDataBG2[j][i] == -1) {
					continue;
				}
				g.drawImage(currentLevel.levelSpriteBackground2[currentLevel.levelDataBG2[j][i]],
						Game.TILES_SIZE * i - xLvlOffset,
						Game.TILES_SIZE * j,
						Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
		}
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < currentLevel.getLevelDataBG()[0].length; i++) {
				if (currentLevel.levelDataBG[j][i] == -1)
					continue;
				g.drawImage(currentLevel.levelSpriteBackground[currentLevel.levelDataBG[j][i]],
						Game.TILES_SIZE * i - xLvlOffset,
						Game.TILES_SIZE * j,
						Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
		}
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < currentLevel.getLevelData()[0].length; i++) {
				if (currentLevel.levelData[j][i] == -1 || currentLevel.levelData[j][i] == -2
						|| currentLevel.levelData[j][i] == -5 || currentLevel.levelData[j][i] == -3)
					continue;
				g.drawImage(currentLevel.levelSprite[currentLevel.levelData[j][i]],
						Game.TILES_SIZE * i - xLvlOffset,
						Game.TILES_SIZE * j,
						Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
		}
	}

	public void update() {

	}

	public void loadNextLevel() {
		lvlIndex++;
		if (lvlIndex > maxLvl) {
			lvlIndex = 0;
			System.out.println("Game completed");
			Gamestate.state = Gamestate.MENU;
		}
		setCurrentLevel();
		game.getPlaying().getPlayer().loadLevelData(currentLevel.getLevelData());
		game.getPlaying().getObjectManager().loadObjects(currentLevel);
		game.getPlaying().getEnemyManager().loadEnemy(currentLevel);
	}

	private void buildAllLevels() {
		for (int i = 0; i <= maxLvl; i++) {
			levels.add(new Level(i));
		}

	}

	public void setCurrentLevel() {
		currentLevel = levels.get(lvlIndex);
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public int getAmountLevel() {
		return levels.size();
	}

}
