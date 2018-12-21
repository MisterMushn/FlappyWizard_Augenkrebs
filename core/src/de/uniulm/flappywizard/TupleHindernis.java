package de.uniulm.flappywizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class TupleHindernis {

    public final Body TurmBody;
    public final Body DementorBody;

    //private final float heightTurm;
    //private final float heightDementor;

    private float PosX = Gdx.graphics.getWidth();

    public Sprite TurmSprite, DementorSprite;

    private float SCALE = 0.2f;

    public TupleHindernis(Body turmB, Body demB, Sprite turmS, Sprite demS) {

        //this.heightTurm = TurmHeightPoint;
        //this.heightDementor = heightTurm + Abstand;

        this.TurmBody = turmB;
        this.DementorBody = demB;

        this.TurmSprite = turmS;
        this.DementorSprite = demS;

        this.SCALE = SCALE;



        TurmBody.setType(BodyDef.BodyType.KinematicBody);
        DementorBody.setType(BodyDef.BodyType.KinematicBody);

        TurmBody.setAwake(true);
        DementorBody.setAwake(true);

        syncronicePositionSpriteToBody();
    }

    public float getPosX() {
        return PosX;
    }

    public void setPosX(float posX) {
        PosX = posX;
    }


    public void syncronicePositionSpriteToBody(){
        TurmSprite.setPosition(TurmBody.getPosition().x * GameScreen.PPM,TurmBody.getPosition().y * GameScreen.PPM);
        DementorSprite.setPosition(DementorBody.getPosition().x * GameScreen.PPM, DementorBody.getPosition().y * GameScreen.PPM);

        PosX = TurmBody.getPosition().x;

        System.out.println("A"  + TurmSprite.getX());
        System.out.println("B" + TurmBody.getPosition().x);
    }
}

