package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import main.GamePanel;
import utils.LoadSave;

public class LevelManager {

	private GamePanel gamePanel;
	private Game game;
	private BufferedImage[] levelSprite;
	private BufferedImage[] levelSpriteBackground;
	private Level levelOne;

	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprite();
		levelOne = new Level();
	}

	private void importOutsideSprite() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[56];

		for (int j = 0; j < 7; j++) {
			for (int i = 0; i < 8; i++) {
				int index = j * 8 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
		}
		img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_BACKGROUND);
		levelSpriteBackground = new BufferedImage[120];
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 15; i++) {
				int index = j * 15 + i;
				levelSpriteBackground[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
		}
	}

	public void draw(Graphics g, int xLvlOffset) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < levelOne.getLevelDataBG()[0].length; i++) {
				if (levelOne.levelDataBG[j][i] == -1)
					continue;
				g.drawImage(levelSpriteBackground[levelOne.levelDataBG[j][i]], Game.TILES_SIZE * i - xLvlOffset,
						Game.TILES_SIZE * j,
						Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
		}
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
				if (levelOne.levelData[j][i] == -1)
					continue;
				g.drawImage(levelSprite[levelOne.levelData[j][i]], Game.TILES_SIZE * i - xLvlOffset,
						Game.TILES_SIZE * j,
						Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
		}

	}

	public void update() {

	}

	public Level getCurrentLevel() {
		return levelOne;
	}

}
