package levels;

import static utils.HelpMethods.GetPlayerSpawn;

import java.awt.Point;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;
import utils.HelpMethods;

public class Level {

	public int[][] levelData;
	public int[][] levelDataBG;
	public int levelNow;

	public BufferedImage[] levelSprite;
	public BufferedImage[] levelSpriteBackground;
	private BufferedImage img, imgBG;
	private Point playerSpawn;

	private int lvlTileWide;
	private int maxTileOffset;
	private int maxLvlOffsetX;

	public Level(int levelNow) {
		this.levelNow = levelNow;
		getData();
		importSprite();
		calcLvlOffsets();
		calcPlayerSpawn();
	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(levelData);
	}

	private void calcLvlOffsets() {
		lvlTileWide = levelData[0].length;
		maxTileOffset = lvlTileWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = maxTileOffset * Game.TILES_SIZE;

		maxTileOffset = lvlTileWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = maxTileOffset * Game.TILES_SIZE;
	}

	public void getData() {
		switch (levelNow) {
			case 0:
				levelData = LoadSave.GetLevelData(LoadSave.LEVEL_ONE);
				levelDataBG = LoadSave.GetLevelData(LoadSave.LEVEL_ONE_BACKGROUND);
				img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_0);
				imgBG = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_0_BACKGROUND);
				levelSprite = new BufferedImage[56];
				levelSpriteBackground = new BufferedImage[120];
				break;
			case 1:
				levelData = LoadSave.GetLevelData(LoadSave.LEVEL_TWO);
				levelDataBG = LoadSave.GetLevelData(LoadSave.LEVEL_TWO_BACKGROUND);
				img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_1);
				imgBG = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_1_BACKGROUND);
				levelSprite = new BufferedImage[256];
				levelSpriteBackground = new BufferedImage[1024];
				break;
			default:
				levelData = LoadSave.GetLevelData(LoadSave.LEVEL_ONE);
				levelDataBG = LoadSave.GetLevelData(LoadSave.LEVEL_ONE_BACKGROUND);
				img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_0);
				imgBG = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_0_BACKGROUND);
				levelSprite = new BufferedImage[56];
				levelSpriteBackground = new BufferedImage[120];
				break;

		}
	}

	private void importSprite() {

		for (int j = 0; j < img.getHeight() / 32; j++) {
			for (int i = 0; i < img.getWidth() / 32; i++) {
				int index = j * (img.getWidth() / 32) + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
		}

		for (int j = 0; j < imgBG.getHeight() / 32; j++) {
			for (int i = 0; i < imgBG.getWidth() / 32; i++) {
				int index = j * (imgBG.getWidth() / 32) + i;
				levelSpriteBackground[index] = imgBG.getSubimage(i * 32, j * 32, 32, 32);
			}
		}

	}

	public int[][] getLevelData() {
		return levelData;
	}

	public int[][] getLevelDataBG() {
		return levelDataBG;
	}

	public int getLvlTileWide() {
		return lvlTileWide;
	}

	public int getMaxTileOffset() {
		return maxTileOffset;
	}

	public int getMaxLvlOffsetX() {
		return maxLvlOffsetX;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}
}
