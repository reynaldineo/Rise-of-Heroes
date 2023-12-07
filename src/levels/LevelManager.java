package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import main.GamePanel;
import utils.LoadSave;

public class LevelManager {
	
	private GamePanel gamePanel;
	private BufferedImage[] levelSprite;
	private BufferedImage[] levelSpriteBackground;
	private Level levelOne;
	
	public LevelManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		levelOne = new Level();
		importOutsideSprite();
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
	
	

	public void draw (Graphics g) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
	        for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
	        	if(levelOne.levelDataBG[j][i] == -1 ) continue;
	        	g.drawImage(levelSpriteBackground[levelOne.levelDataBG[j][i]],Game.TILES_SIZE*i,Game.TILES_SIZE*j,Game.TILES_SIZE,Game.TILES_SIZE,null);
			}
		}
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
	        for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
	        	if(levelOne.levelData[j][i] == -1 ) continue;
	        	g.drawImage(levelSprite[levelOne.levelData[j][i]],Game.TILES_SIZE*i,Game.TILES_SIZE*j,Game.TILES_SIZE,Game.TILES_SIZE,null);
			}
		}
		
	}
	
	public void update() {
		
		
	}
	
	public Level getCurrentLevel() {
		return levelOne;
	}
	
	
}
