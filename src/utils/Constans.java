package utils;

public class Constans {
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants{
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
			
			switch(player_action) {
				
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
