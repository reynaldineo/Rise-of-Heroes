package ui;

import static utils.Constans.ObjectConstants.APPLE;
import static utils.Constans.ObjectConstants.BLUE_POTION;
import static utils.Constans.ObjectConstants.RED_POTION;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;

public class InventoryOverlay {

    private Playing playing;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;
    private ArrayList<InventoryItem> items;
    private InventoryItem selectedItem;
    private boolean selected = false;
    private UseButton useButton;
    private int selectedIndex;

    public InventoryOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initInventory();
//        addItem(RED_POTION);
//        addItem(BLUE_POTION);
//        addItem(APPLE);

    }

    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.INVENTORY_IMG);
        bgW = (int) (img.getWidth() * Game.SCALE);
        bgH = (int) (img.getHeight() * Game.SCALE);
        bgX = (Game.GAME_WIDTH / 2 - bgW / 2);
        bgY = (int) ((Game.GAME_HEIGHT * Game.SCALE) / 2 - (img.getHeight() * Game.SCALE) / 2);
    }

    public void update() {
        useButton.update();
    }

    public void draw(Graphics g) {
        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        drawItem(g);
        if (selected) {
            selectedItem.drawInfo(g);
            useButton.draw(g);
        }
    }

    private void drawItem(Graphics g) {
        int startX = 334;
        int startY = 191;
        int index = 0;
        for (InventoryItem i : items) {
            i.draw(g, startX, startY);
            if (index % 5 == 0 && index != 0) {
                startX = 334;
                startY += 58;
            } else {
                startX += 60;
            }
            index++;
        }
    }

    private void initInventory() {
        items = new ArrayList<>();
        useButton = new UseButton();
    }

    public void addItem(int objType) {
        items.add(new InventoryItem(objType, playing));
        System.out.println("Tambah Item :" + objType);
    }

    private boolean isIn(MouseEvent e, InventoryItem i) {
        return (i.getBounds().contains(e.getX(), e.getY()));

    }

    public void mouseMoved(MouseEvent e) {
        for (InventoryItem i : items) {
            i.setMouseOver(false);
            if (isIn(e, i))
                i.setMouseOver(true);

        }

        if (selected) {
            useButton.setMouseOver(false);
            if (isInButton(e, useButton))
                useButton.setMouseOver(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (selected)
            if (isInButton(e, useButton)) {
                useButton.setMousePressed(false);
                selectedItem.useItem();
                selectedItem = null;
                items.remove(selectedIndex);
                selected = false;
            }
        selected = false;
        int index = 0;
        for (InventoryItem i : items) {
            if (isIn(e, i)) {
                selectedItem = i;
                selected = true;
                selectedIndex = index;
                continue;
            }
            i.resetBools();
            index++;
        }
    }

    public void mousePressed(MouseEvent e) {
        for (InventoryItem i : items) {
            if (isIn(e, i)) {
                i.setMousePressed(true);
            }
        }
        if (selected)
            if (isInButton(e, useButton))
                useButton.setMousePressed(true);
    }

    private boolean isInButton(MouseEvent e, UseButton i) {
        return (i.getBounds().contains(e.getX(), e.getY()));

    }
}
