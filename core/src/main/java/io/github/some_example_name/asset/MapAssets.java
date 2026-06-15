package io.github.some_example_name.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public enum MapAssets implements Asset<TiledMap>{
    MAIN("Main.tmx");

    private final AssetDescriptor<TiledMap> descriptor;

    MapAssets(String mapName){
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.projectFilePath = "TileMap/SproutLand.tiled-project";
        this.descriptor = new AssetDescriptor<>("TileMap/" + mapName, TiledMap.class, parameters);
    }
    @Override
    public AssetDescriptor<TiledMap> getDescriptor(){
        return this.descriptor;
    }
}
