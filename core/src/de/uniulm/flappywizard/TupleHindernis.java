package de.uniulm.flappywizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import org.w3c.dom.Text;

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

        syncronicePositionSpriteToBody();
    }

    public float getPosX() {
        return PosX;
    }

    public void setPosX(float posX) {
        PosX = posX;
    }


    public void syncronicePositionSpriteToBody(){
        TurmSprite.setPosition(TurmBody.getPosition().x,TurmBody.getPosition().y);
        DementorSprite.setPosition(DementorBody.getPosition().x, DementorBody.getPosition().y);

        PosX = TurmBody.getPosition().x;
    }
}

