package ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import utils.LoadSave;

public class UseButton {
    private BufferedImage[] imgs;
    private int index;
    private int x, y;
    private boolean mouseOver, mousePressed;

    public UseButton() {
        this.index = 0;
        loadImgs();
    }

    private void loadImgs() {
        imgs = new BufferedImage[2];
        imgs[0] = LoadSave.GetSpriteAtlas(LoadSave.USE_BUTTON);
        imgs[1] = LoadSave.GetSpriteAtlas(LoadSave.USE_BUTTON_HOVER);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 1;
    }

    public void draw(Graphics g) {
    	this.x = 175;
    	this.y = 400;
        g.drawImage(imgs[index], x, y, 98,42, null);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        g.drawString("use", x+23, y+28);
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, imgs[0].getWidth(), imgs[0].getHeight());
    }
}
