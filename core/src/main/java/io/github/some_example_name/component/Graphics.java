package io.github.some_example_name.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Graphics implements Component {
    public  static final ComponentMapper<Graphics> MAPPER = ComponentMapper.getFor(Graphics.class);

    private TextureRegion region;
    private final Color color;

    public Graphics(TextureRegion region, Color color) {
        this.region = region;
        this.color = color;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public Color getColor() {
        return color;
    }
}
