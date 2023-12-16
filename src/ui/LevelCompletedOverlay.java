package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;
import static utils.Constans.UI.UrmButtons.*;

public class LevelCompletedOverlay {

    private Playing playing;
    private UrmButton menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int y = (int) (354 * Game.SCALE);
        next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
        bgW = (int) (img.getWidth() * Game.SCALE);
        bgH = (int) (img.getHeight() * Game.SCALE);
        bgX = (Game.GAME_WIDTH / 2 - bgW / 2);
        bgY = (int) ((Game.GAME_HEIGHT * Game.SCALE) / 2 - (img.getHeight() * Game.SCALE) / 2);
    }

    public void update() {
        next.update();
        menu.update();
    }

    public void draw(Graphics g) {
        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }

    private boolean isIn(MouseEvent e, UrmButton b) {
        return (b.getBounds().contains(e.getX(), e.getY()));

    }

    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(e, menu))
            menu.setMouseOver(true);
        else if (isIn(e, next))
            next.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menu)) {
            if (menu.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
                playing.unpauseGame();
                playing.resetAll();
            }
        } else if (isIn(e, next)) {
            if (next.isMousePressed())
                playing.loadNextLevel();
        }

        menu.resetBools();
        next.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, menu)) {
            menu.setMousePressed(true);
        } else if (isIn(e, next)) {
            next.setMousePressed(true);
        }
    }

}
