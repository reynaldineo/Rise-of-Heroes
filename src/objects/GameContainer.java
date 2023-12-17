package objects;

import static utils.Constans.ObjectConstants.*;

import main.Game;

public class GameContainer extends GameObject {

    public GameContainer(int x, int y, int objType) {
        super(x, y, objType);
        createHitbox();
    }

    private void createHitbox() {
        if (objType == BOX) {
            initHitbox(38, 27);
            xDrawOffset = (int) (10 * Game.SCALE);
            yDrawOffset = (int) (19 * Game.SCALE);
        } else if (objType == BARREL) {
            initHitbox(34, 38);
            xDrawOffset = (int) (13 * Game.SCALE);
            yDrawOffset = (int) (6 * Game.SCALE);
        }
    }

    public void update() {
        if (doAnimation)
            updateAnimationTick();
    }

}
