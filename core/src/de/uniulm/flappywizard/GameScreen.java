package de.uniulm.flappywizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.codeandweb.physicseditor.PhysicsShapeCache;

import java.util.ArrayList;
import java.util.List;


public class GameScreen implements Screen, ContactListener, InputProcessor {

	final FlappyWizardGame game;
	
	OrthographicCamera camera;


	Box2DDebugRenderer debugRenderer;

	//ExtendViewport viewport;


	boolean kont = false;


	Sprite lunaSprite;
	Body lunaBody;

	World world;

	TextureAtlas textureAtlas;
	PhysicsShapeCache physicsBodies;

	static final float STEP_TIME = 1f / 60f;
	static final int VELOCITY_ITERATIONS = 6;
	static final int POSITION_ITERATIONS = 2;
	float accumulator = 0;

	float velocity = 700f;




	List<TupleHindernis> TupleHindernisListe = new ArrayList<TupleHindernis>();
	int letzteElement = 0;


	//Settings
	public static final float PPM = 100;  //Pixel per Meter
	float ABSTAND = 450 / PPM;

	float SCALE_LUNA = 4f / PPM;
	float SCALE_LUNA_BODY = 0.1f / PPM;
	float SCALE_TURM = 0.25f / 10;
	float SCALE_TURM_BODY = 0.01f;
	float SCALE_DEMENTOR = 0.15f;
	float SCALE_DEMENTOR_BODY = 0.01f;

	float GESCHWINDIGKEITTURME = -3;



	float SPIELFELD_BREITE = 44;  // in Meter
	float SPIELFELD_HOHE = 25;    // auch

	
	public GameScreen(FlappyWizardGame game) {
		Box2D.init();
		debugRenderer = new Box2DDebugRenderer();



		this.game = game;

//		game.batch.setProjectionMatrix(camera.combined); // XX Nicht sicher ob brauch gekoppelt mit X1


		textureAtlas = new TextureAtlas("sprites.txt");
		physicsBodies = new PhysicsShapeCache("physics.xml");

		world = new World(new Vector2(0, -10), true);
		world.setContactListener(this);

		Gdx.input.setInputProcessor(this);

		lunaSprite = textureAtlas.createSprite("luna");
		lunaSprite.setSize(lunaSprite.getWidth() * SCALE_LUNA,lunaSprite.getHeight() * SCALE_LUNA);
		lunaSprite.setPosition(100 / PPM,200 / PPM);

		lunaBody = physicsBodies.createBody("luna", world, SCALE_LUNA_BODY , SCALE_LUNA_BODY); //besser mit body def
		lunaBody.setTransform(lunaSprite.getX() / PPM , lunaSprite.getY() / PPM, 0);

		TupleHindernisListe.add( createTuple(ABSTAND,10));
		TupleHindernisListe.get(0).setPosX(Gdx.graphics.getWidth() / PPM);



		camera = new OrthographicCamera();
		camera.setToOrtho(false, SPIELFELD_BREITE , SPIELFELD_HOHE);
		//viewport = new ExtendViewport(1280, 720, camera); // XX nicht safe ob brauche   gekoppelt mit X1
	}	

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		//game.batch.setProjectionMatrix(camera.combined); // XX Nicht sicher

		stepWorld(delta);


		if(kont){

			Gdx.gl.glClearColor(0, 0, 1, 1);
		}else{

			Gdx.gl.glClearColor(1, 0, 0, 1);
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		/**
		if(Gdx.input.isTouched()){
			//lunaBody.applyForceToCenter(0, -100f, false);
			//lunaBody.setLinearVelocity(0,1000f);
			lunaBody.setTransform(lunaBody.getPosition().x,lunaBody.getPosition().y + 20f,0 );
			velocity = 7f;
		}
		*/
		if(velocity>=0){
			velocity += 1f*delta;
		}else {
			velocity = 0f;
		}



		bewegeTurme();
		syncroniseAllSpriteWithBodyPosition();


		game.batch.begin();

		lunaSprite.draw(game.batch);
		TurmeZeichnen();
		DebugInterfaceHUD();

		game.batch.end();


		reloadTurme();

		debugRenderer.render(world, camera.combined);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

		//viewport.update(width, height, true);

		//game.batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

		textureAtlas.dispose();
		world.dispose();
		debugRenderer.dispose();
	}


	@Override
	public void beginContact(Contact contact) { // XXX
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		//if(fa == null || fb == null) return;
		//if(fa.getUserData() == null || fb.getUserData() == null) return;

		//if(isTutorialContact(fa, fb)) {
		//    GameScreen tba = (GameScreen) fa.getUserData();
		//    GameScreen tbb = (GameScreen) fb.getUserData();
//
		//    tbb.body.applyForceToCenter(-3000, 0, false);
		//}
		//System.out.println("" + fa.getBody().getFixtureList() + " " + lunaBody.getFixtureList());
		//Intersector.intersectPolygons(lunaBody.getFixtureList());
		//lunaBody.getFixtureList().get(1).get


		//FUNKT
		if(true){//Fälle
			// XXX wenn boden mit turm direkt abbrechen (return)
			//System.out.println(contact.getFixtureA().getBody().equals(lunaBody));
			//System.out.println(contact.getFixtureB().getBody().equals(lunaBody));
		}

		kont = true;


	}

	@Override
	public void endContact(Contact contact) { // XXX

		kont = false;

		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if(fa == null || fb == null) return;
		if(fa.getUserData() == null || fb.getUserData() == null) return;

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}


	@Override public boolean keyDown (int keycode) {
		return false;
	}

	@Override public boolean keyUp (int keycode) {
		return false;
	}

	@Override public boolean keyTyped (char character) {
		return false;
	}

	@Override public boolean scrolled (int amount) {
		return false;
	}

	@Override public boolean mouseMoved (int screenX, int screenY) {
		return false;
	}

	@Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		// ignore if its not left mouse button or first touch pointer

		lunaBody.applyForceToCenter(10f, 10f, true);
		//lunaBody.setLinearVelocity(0,1000000f);
		//lunaBody.setTransform(lunaBody.getPosition().x,lunaBody.getPosition().y + 20f,0 );
		//velocity = 7f;

		return true;
	}

	@Override public boolean touchDragged (int screenX, int screenY, int pointer) {

		return true;
	}

	@Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {

		return true;
	}




	private void stepWorld(float delta) {
		//float delta = Gdx.graphics.getDeltaTime();

		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= STEP_TIME) {

			accumulator -= STEP_TIME;
			world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}
	}

	public TupleHindernis createTuple(float Abstand, float TurmHeightPoint){

		Sprite TurmSprite = textureAtlas.createSprite("turm_gryffindor");
		Sprite DementorSprite = textureAtlas.createSprite("dementor");

		TurmSprite.setSize(TurmSprite.getWidth()*SCALE_TURM,TurmSprite.getHeight()*SCALE_TURM);
		DementorSprite.setSize(DementorSprite.getWidth()*SCALE_DEMENTOR,DementorSprite.getHeight()*SCALE_DEMENTOR);

		Body TurmBody = physicsBodies.createBody("turm_gryffindor", world, SCALE_TURM_BODY  , SCALE_TURM_BODY );
		Body DementorBody = physicsBodies.createBody("dementor", world, SCALE_DEMENTOR_BODY  ,SCALE_DEMENTOR_BODY );

		TurmBody.setTransform((100 + Gdx.graphics.getWidth()  - TurmSprite.getWidth()/2) / PPM , TurmHeightPoint / PPM , 0);
		DementorBody.setTransform((100 + Gdx.graphics.getWidth() - DementorSprite.getWidth()/2) / PPM, (TurmHeightPoint + Abstand) / PPM, 0);

		TurmSprite.setPosition(TurmBody.getPosition().x / PPM , TurmBody.getPosition().y / PPM);
		DementorSprite.setPosition(DementorBody.getPosition().x / PPM, DementorBody.getPosition().y / PPM);


		TurmBody.setLinearVelocity(-100,0);
		DementorBody.setLinearVelocity(-100,0);


		return new TupleHindernis(TurmBody, DementorBody, TurmSprite, DementorSprite);
	}

	public void syncroniseAllSpriteWithBodyPosition(){
		lunaSprite.setPosition(lunaBody.getPosition().x ,lunaBody.getPosition().y) ;

		for(int i = 0; i<TupleHindernisListe.size(); i++){
			TupleHindernisListe.get(i).syncronicePositionSpriteToBody();
		}
	}

	public void bewegeTurme(){
		for(int i = 0; i<TupleHindernisListe.size(); i++){
			TupleHindernisListe.get(i).TurmBody.setLinearVelocity(GESCHWINDIGKEITTURME,0);
			TupleHindernisListe.get(i).DementorBody.setLinearVelocity(GESCHWINDIGKEITTURME,0);
		}
	}

	public void TurmeZeichnen(){
		for(int i = 0; i<TupleHindernisListe.size(); i++){
			TupleHindernisListe.get(i).TurmSprite.draw(game.batch);
			TupleHindernisListe.get(i).DementorSprite.draw(game.batch);
		}
	}

	public void DebugInterfaceHUD(){
		//game.font.draw(game.batch, "Letzter \"Index\": " + letzteElement, 0, 480);
		game.font.draw(game.batch, "TubleMaxIndesx: " + TupleHindernisListe.size(), 0, 500);
		//game.font.draw(game.batch, "Position " + tester.getX() + " " + tester.getY() + " " + fml.getX()  + " " + fml.getY(), 0, 520);
	}

	public void reloadTurme(){
		System.out.println(TupleHindernisListe.get(letzteElement).getPosX());
		if((Gdx.graphics.getWidth()  - TupleHindernisListe.get(letzteElement).getPosX() * PPM > 500)){
			letzteElement += 1;
			TupleHindernisListe.add(createTuple(ABSTAND, 10));
		}

		if(TupleHindernisListe.get(0).getPosX() + TupleHindernisListe.get(0).DementorSprite.getWidth() <0){
			TupleHindernisListe.remove(0); //Index nicht Objekt
			letzteElement -= 1;
		}
	}
}
