package io.github.some_example_name.tiled;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.asset.AssetService;

import io.github.some_example_name.asset.AtlasAsset;
import io.github.some_example_name.component.Graphics;
import io.github.some_example_name.component.Transform;

public class TiledAshleyConfigurator {
    private final Engine engine;
    private final AssetService assetService;

    public TiledAshleyConfigurator(Engine engine, AssetService assetService) {
        this.engine = engine;
        this.assetService = assetService;
    }

    public void onLoadObject(TiledMapTileMapObject tileMapObject) {
        Entity entity = this.engine.createEntity();
        TiledMapTile tile = tileMapObject.getTile();
        TextureRegion textureRegion = getTextureRegion(tile);
        int z = tile.getProperties().get("z", 1, Integer.class);
        entity.add(new Graphics(textureRegion, Color.WHITE.cpy()));
        addEntityTransform(
            tileMapObject.getX(), tileMapObject.getY(),z,
            textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
            tileMapObject.getScaleX(), tileMapObject.getScaleY(),
            entity);

        this.engine.addEntity(entity);

    }

    private void addEntityTransform(float x, float y, int z, float w, float h, float scaleX, float scaleY, Entity entity) {
   Vector2 position = new Vector2(x, y);
   Vector2 size = new Vector2(w, h);
   Vector2 scaling = new Vector2(scaleX, scaleY);
   position.scl(Main.UNIT_SCALE);
   size.scl(Main.UNIT_SCALE);
   entity.add(new Transform(position, z, size, scaling, 0f));



    }



    private TextureRegion getTextureRegion(TiledMapTile tile) {
        tile.getProperties().get("atlasAsset", AtlasAsset.OBJECTS.name(), String.class);

        return tile.getTextureRegion();
    }
}
