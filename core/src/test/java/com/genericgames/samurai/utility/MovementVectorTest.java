package com.genericgames.samurai.utility;

import com.badlogic.gdx.math.Vector2;
import org.junit.Assert;
import org.junit.Test;

public class MovementVectorTest {

    private MovementVector movementVector;

    @Test
    public void testZeroVector(){
        movementVector = new MovementVector(new Vector2(0,0));
        Assert.assertEquals(0, movementVector.getDirectionVector().x, 0.1f);
        Assert.assertEquals(0, movementVector.getDirectionVector().y, 0.1f);
    }

    @Test
    public void testMoveForward(){
        movementVector = new MovementVector(new Vector2(0,1));
        movementVector.forwardMovement(3);
        Assert.assertEquals(0, movementVector.x, 0.1f);
        Assert.assertEquals(-3, movementVector.y, 0.1f);
        Assert.assertEquals(0, movementVector.getDirectionVector().x, 0.1f);
        Assert.assertEquals(1, movementVector.getDirectionVector().y, 0.1f);
    }
}
