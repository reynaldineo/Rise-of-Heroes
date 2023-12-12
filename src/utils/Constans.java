package utils;

import main.Game;

public class Constans {

	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}

		public static class PausedButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}

		public static class UrmButtons {
			public static final int URM_SIZE_DEFAULT = 56;
			public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * Game.SCALE);
		}

		public static class VolumeButtons {
			public static final int VOLUME_SIZE_DEFAULT_WIDTH = 28;
			public static final int VOLUME_SIZE_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_SIZE_DEFAULT_WIDTH = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_SIZE_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_SIZE_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_SIZE_DEFAULT_WIDTH * Game.SCALE);
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int ATTACK = 1;
		public static final int RUNNING = 2;
		public static final int JUMP = 3;
		public static final int FALLING = 4;
		public static final int DAMAGE = 5;
		public static final int DEATH = 6;
		public static final int SPELL = 8;
		public static final int CROUCH = 9;
		public static final int SHIELD = 10;

		public static int GetSpriteAmount(int player_action) {

			switch (player_action) {

				case IDLE:
					return 6;
				case ATTACK:
					return 8;
				case RUNNING:
					return 8;
				case JUMP:
					return 8;
				case FALLING:
					return 8;
				case DAMAGE:
					return 4;
				case DEATH:
					return 8;
				case SPELL:
					return 8;
				case CROUCH:
					return 3;
				case SHIELD:
					return 3;
				default:
					return 1;
			}
		}
	}
}
