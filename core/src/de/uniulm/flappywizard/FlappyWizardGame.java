package de.uniulm.flappywizard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class FlappyWizardGame extends Game {
	
	public SpriteBatch batch;
	
	public Texture hermine;
	public Texture harry, luna, malfoy, cedrig;
	public Texture item_blau, item_gruen, item_rot, item_silber;
	public Texture dementor, turm_gryffindor, turm_huffelpuff, turm_ravenclaw, turm_slytherin;

	public World world;

	public BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		hermine = new Texture("png/hermine.png");
		
		harry = new Texture("png/harry.png");
		luna = new Texture("png/luna.png");
		malfoy = new Texture("png/malfoy.png");
		cedrig = new Texture("png/cedrig.png");
		
		item_blau = new Texture("png/item_blau.png");
		item_gruen = new Texture("png/item_gruen.png");
		item_rot = new Texture("png/item_rot.png");
		item_silber = new Texture("png/item_silber.png");
		
		dementor = new Texture("png/dementor.png");
		turm_gryffindor = new Texture("png/turm_gryffindor.png");
		turm_huffelpuff = new Texture("png/turm_huffelpuff.png");
		turm_ravenclaw = new Texture("png/turm_ravenclaw.png");
		turm_slytherin = new Texture("png/turm_slytherin.png");

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		
		hermine.dispose();

		harry.dispose();
		luna.dispose();
		malfoy.dispose();
		cedrig.dispose();

		item_blau.dispose();
		item_gruen.dispose();
		item_rot.dispose();
		item_silber.dispose();

		dementor.dispose();
		turm_gryffindor.dispose();
		turm_huffelpuff.dispose();
		turm_ravenclaw.dispose();
		turm_slytherin.dispose();
	}
}
