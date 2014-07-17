package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.combat.ChargeAttack;
import com.genericgames.samurai.inventory.Inventory;
import com.genericgames.samurai.model.state.living.combatable.Combatable;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.weapon.Weapon;
import com.genericgames.samurai.model.weapon.WeaponInventory;
import com.genericgames.samurai.physics.PhysicalWorldFactory;
import com.genericgames.samurai.utility.ImageCache;

import java.util.Map;

public class PlayerCharacter extends Combatable {

    private Inventory inventory = new Inventory();
    private WeaponInventory weaponInventory = new WeaponInventory();
    private Map<State, Animation> animationMap;

    public PlayerCharacter(World world, float x, float y){
        super(x, y);
        body = PhysicalWorldFactory.createPlayer(this, world);
        this.setSpeed(DEFAULT_SPEED);
        this.addAttack(new Attack(8, 0, 30, State.LIGHT_ATTACKING));
        this.addAttack(new ChargeAttack(16, 0, 60, 50, State.HEAVY_ATTACKING));

        weaponInventory.addWeapon(Weapon.SWORD);
        weaponInventory.addWeapon(Weapon.BOW);

        animationMap = ImageCache.getAnimations().get(getClass());
    }

    public Inventory getInventory(){
        return inventory;
    }

    public void setPosition(float positonX, float positonY){
        setPositionX(positonX);
        setPositionY(positonY);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if(!isInvincible() || (getInvincibilityCounter()%10<5)){
            TextureRegion texture = animationMap.get(getState()).getKeyFrame(getStateTime(),
                    getState().isLoopingState());

            float tileSize = ImageCache.tileSize;
            float playerX = getX()-(tileSize/2);
            float playerY = getY()-(tileSize/2);

            batch.draw(texture, playerX, playerY,
                    0.5f,  0.5f, tileSize, tileSize, 1, 1, getRotationInDegrees());
        }
        //        System.out.println(debugInfo());
    }

    @Override
    public String debugInfo() {
        return "Player Character\nPos x: "+ getX() +"\nPos y : " + getY()+"\nRotation : " + getRotationInDegrees();
    }

    public WeaponInventory getWeaponInventory() {
        return weaponInventory;
    }

    public void setWeaponInventory(WeaponInventory weaponInventory) {
        this.weaponInventory = weaponInventory;
    }
}
