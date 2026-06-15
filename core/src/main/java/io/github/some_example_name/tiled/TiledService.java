package io.github.some_example_name.tiled;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.utils.GdxRuntimeException;
import io.github.some_example_name.asset.AssetService;
import io.github.some_example_name.asset.MapAssets;

import java.util.function.Consumer;

public class TiledService {
    private static final String MAP_ASSET_PROPERTY = "mapAsset";
    private static final String OBJECTS_LAYER_NAME = "objects";

    private final AssetService assetService;

    private TiledMap currentMap;

    private Consumer<TiledMap> mapChangeConsumer;
    private Consumer<TiledMapTileMapObject> loadObjectConsumer;

    public TiledService(AssetService assetService) {
        this.assetService = assetService;
        this.mapChangeConsumer = null;
        this.loadObjectConsumer = null;
        this.currentMap = null;
    }

    public TiledMap loadMap(MapAssets mapAsset) {
        TiledMap tiledMap = this.assetService.load(mapAsset);
        tiledMap.getProperties().put(MAP_ASSET_PROPERTY, mapAsset);
        return tiledMap;
    }

    public void setMap(TiledMap map) {
        if (this.currentMap != null && this.currentMap != map) {
            MapAssets currentMapAsset = this.currentMap.getProperties().get(MAP_ASSET_PROPERTY, MapAssets.class);
            if (currentMapAsset != null) {
                this.assetService.unload(currentMapAsset);
            }
        }

        this.currentMap = map;
        loadMapObjects(map);
        if (this.mapChangeConsumer != null) {
            this.mapChangeConsumer.accept(map);
        }
    }

    private void loadMapObjects(TiledMap tiledMap) {
        Consumer<TiledMapTileMapObject> objectConsumer = this.loadObjectConsumer;
        if (objectConsumer == null) {
            return;
        }

        for (MapLayer layer : tiledMap.getLayers()) {
            if (OBJECTS_LAYER_NAME.equals(layer.getName())) {
                loadObjectLayer(layer, objectConsumer);
            }
        }
    }

    private void loadObjectLayer(MapLayer objectLayer, Consumer<TiledMapTileMapObject> objectConsumer) {
        for (MapObject mapObject : objectLayer.getObjects()) {
            if (mapObject instanceof TiledMapTileMapObject tileMapObject) {
                objectConsumer.accept(tileMapObject);
            } else {
                throw new GdxRuntimeException("Unsupported object: " + mapObject.getClass().getSimpleName());
            }
        }
    }

    public void setMapChangeConsumer(Consumer<TiledMap> mapChangeConsumer) {
        this.mapChangeConsumer = mapChangeConsumer;
    }

    public void setLoadObjectConsumer(Consumer<TiledMapTileMapObject> loadObjectConsumer) {
        this.loadObjectConsumer = loadObjectConsumer;
    }
}
