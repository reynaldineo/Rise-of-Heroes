package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;
import static utils.Constans.UI.PausedButtons.*;
import static utils.Constans.UI.UrmButtons.*;
import static utils.Constans.UI.VolumeButtons.*;

public class PausedOverlay {

    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sfxButton;
    private UrmButton menuB, replayB, unpauseB;
    private VolumeButton volumeButton;

    public PausedOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (354 * Game.SCALE);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createUrmButtons() {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpausesX = (int) (462 * Game.SCALE);
        int bY = (int) (403 * Game.SCALE);
        menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButton(unpausesX, bY, URM_SIZE, URM_SIZE, 0);

    }

    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (217 * Game.SCALE);
        int sfxY = (int) (263 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);

    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSED_BACKGROUND);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = (Game.GAME_WIDTH / 2 - bgW / 2);
        bgY = (int) (100 * Game.SCALE);

    }

    public void update() {
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();

        volumeButton.update();
    }

    public void draw(Graphics g) {
        // background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null, null);

        // sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        // urm buttons
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        // volume button
        volumeButton.draw(g);

    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMousePressed(true);
        else if (isIn(e, menuB))
            menuB.setMousePressed(true);
        else if (isIn(e, replayB)) {
            playing.resetAll();
            replayB.setMousePressed(true);
        } else if (isIn(e, unpauseB))
            unpauseB.setMousePressed(true);
        else if (isIn(e, volumeButton))
            volumeButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());
        } else if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
                playing.unpauseGame();
            }
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed())
                System.out.println("replay lvl");
        } else if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed()) {
                playing.unpauseGame();
                playing.setInventoryOpen(false);
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);
        else if (isIn(e, menuB))
            menuB.setMouseOver(true);
        else if (isIn(e, replayB))
            replayB.setMouseOver(true);
        else if (isIn(e, unpauseB))
            unpauseB.setMouseOver(true);
        else if (isIn(e, volumeButton))
            volumeButton.setMouseOver(true);
    }

    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed())
            volumeButton.changeX(e.getX());
    }

    private boolean isIn(MouseEvent e, PausedButton b) {
        return (b.getBounds().contains(e.getX(), e.getY()));

    }
}
