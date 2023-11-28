package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import main.GamePanel;
import utils.LoadSave;

public class LevelManager {
	
	private GamePanel gamePanel;
	private BufferedImage[] levelSprite;
	private Level levelOne;
	
	public LevelManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
//		levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
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
	}
	
	

	public void draw (Graphics g) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
	        for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
	        	if(levelOne.levelData[j][i] == -1 ) continue;
	        	g.drawImage(levelSprite[levelOne.levelData[j][i]],Game.TILES_SIZE*i,Game.TILES_SIZE*j,Game.TILES_SIZE,Game.TILES_SIZE,null);
			}

		}
		
	}
	
	public void update() {
		
	}
}
