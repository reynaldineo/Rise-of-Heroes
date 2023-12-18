package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.BufferedReader;

import javax.imageio.ImageIO;

public class LoadSave {

	public static final String PLAYER_ATLAS = "Char/generic_char/png/blue/char_blue_1.png";
	public static final String PLAYER_ATLAS_LEFT = "Char/generic_char/png/blue/char_blue_1_reverse.png";
	public static final String LEVEL_ATLAS_0 = "Enviroment/CastleTiles.png";
	public static final String LEVEL_ATLAS_0_BACKGROUND = "Background_IMG/Background_2.png";
	public static final String LEVEL_ATLAS_1 = "Enviroment/TX_Ground.png";
	public static final String LEVEL_ATLAS_1_BACKGROUND = "Enviroment/TX_Props.png";
	public static final String LEVEL_ONE = "LevelOneLong.txt";
	public static final String LEVEL_ONE_BACKGROUND = "LevelOneLongBG.txt";
	public static final String LEVEL_TWO = "LevelTwoLong.txt";
	public static final String LEVEL_TWO_BACKGROUND = "LevelTwoLongBG.txt";
	public static final String MENU_BUTTONS = "Menu/button_atlas.png";
	public static final String MENU_BACKGROUND = "Menu/menu_background.png";
	public static final String MENU_BACKGROUND_PICTURE = "Menu/Summer7.png";
	public static final String PAUSED_BACKGROUND = "Menu/pause_menu.png";
	public static final String SOUND_BUTTONS = "Menu/sound_button.png";
	public static final String URM_BUTTONS = "Menu/urm_buttons.png";
	public static final String VOLUME_BUTTONS = "Menu/volume_buttons.png";
	public static final String COMPLETED_IMG = "Menu/completed_sprite.png";
	public static final String OBJECT = "Object/object.png";
	public static final String CHEST = "Object/chest_sprites.png";
	public static final String CONTAINER = "Object/objects_sprites.png";
	public static final String CRABBY_SPRITE = "Enemy/crabby_sprite.png";
	public static final String INVENTORY_IMG = "Menu/inventory.png";
	public static final String ITEM = "Object/item.png";
	public static final String ITEM_CLICKED = "Object/item_clicked.png";
	public static final String ITEM_INFO = "Object/object.png";
	public static final String USE_BUTTON = "Menu/use_button.png";
	public static final String USE_BUTTON_HOVER = "Menu/use_button_hover.png";
	public static final String STATUS_BAR = "Char/health_power_bar.png";
	public static final String DEATH_SCREEN = "Menu/death_screen.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return img;
	}

	public static int[][] GetLevelData(String fileName) {
		String filePath = "/Map/" + fileName;

		List<int[]> result = new ArrayList<>();

		try (InputStream is = LoadSave.class.getResourceAsStream(filePath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {

			String line;
			while ((line = reader.readLine()) != null) {
				String[] numbers = line.split(",");
				int[] intArray = new int[numbers.length];

				for (int i = 0; i < numbers.length; i++) {
					intArray[i] = Integer.parseInt(numbers[i].trim());
				}

				result.add(intArray);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toArray(new int[0][]);
	}
}
