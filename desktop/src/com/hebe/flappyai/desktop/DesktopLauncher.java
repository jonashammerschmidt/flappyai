package com.hebe.flappyai.desktop;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hebe.flappyai.FlappyAI;

public class DesktopLauncher {

	public static int WIDTH = 900;
	public static int HEIGHT = 1600;

	public static void main(String[] arg) {
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension d = tk.getScreenSize();
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = WIDTH;
		config.height = HEIGHT;
		config.x = d.width / 2 - WIDTH / 2;
		config.y = d.height / 2 - HEIGHT / 2;
		config.fullscreen = false;
		config.vSyncEnabled = false; // Setting to false disables vertical sync
		config.foregroundFPS = 0; // Setting to 0 disables foreground fps throttling
		config.backgroundFPS = 0; // Setting to 0 disables background fps throttling
		LwjglApplicationConfiguration.disableAudio = true; // Disable audio
		new LwjglApplication(new FlappyAI(), config);
	}
}
