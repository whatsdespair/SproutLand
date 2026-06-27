package io.github.some_example_name.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.component.Move;
import io.github.some_example_name.component.Transform;

public class MoveSystem extends IteratingSystem {

    private final Vector2 normalizedDirection = new Vector2();

    public MoveSystem() {
        super(Family.all(Move.class, Transform.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Move move = Move.MAPPER.get(entity);
        if(move.isRooted() || move.getDirection().isZero()) {return;}

        normalizedDirection.set(move.getDirection()).nor();
        Transform transform = Transform.MAPPER.get(entity);
        Vector2 position = transform.getPosition();
        position.set(
            position.x + move.getDirection().x * move.getMaxSpeed() * deltaTime,
            position.y + move.getDirection().y * move.getMaxSpeed() * deltaTime
        );

    }
}
