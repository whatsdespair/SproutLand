package io.github.some_example_name;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.asset.AssetService;
import io.github.some_example_name.asset.MapAssets;
import io.github.some_example_name.system.RenderSystem;
import io.github.some_example_name.tiled.TiledAshleyConfigurator;
import io.github.some_example_name.tiled.TiledService;

import java.util.function.Consumer;


public class GameScreen extends ScreenAdapter {
private final Main game;
private final Batch batch;
private final AssetService assetService;
private final Viewport viewport;
private final OrthographicCamera camera;
private  final Engine engine;
private final TiledService tiledService;
private final TiledAshleyConfigurator tiledAshleyConfigurator;

    public GameScreen(Main game){
        this.game = game;
        this.assetService = game.getAssetService();
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this .batch = game.getBatch();
        this.tiledService = new TiledService(this.assetService);
        this.engine = new Engine();
        this.tiledAshleyConfigurator = new TiledAshleyConfigurator(this.engine, this.assetService);

        this.engine.addSystem(new RenderSystem(this.batch, this.viewport, this.camera));
    }

    @Override
    public void show() {
        Consumer<TiledMap> renderConsumer = this.engine.getSystem(RenderSystem.class)::setMap;
        this.tiledService.setMapChangeConsumer(renderConsumer);
        this.tiledService.setLoadObjectConsumer(this.tiledAshleyConfigurator::onLoadObject);

        TiledMap tiledMap = this.tiledService.loadMap(MapAssets.MAIN);
        this.tiledService.setMap(tiledMap);
    }

    @Override
    public void hide() {
        this.engine.removeAllEntities();
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1 / 30f);
        this.engine.update(delta);


    }

    @Override
    public void dispose() {
        for(EntitySystem system : this.engine.getSystems()){
            if(system instanceof Disposable disposableSystem){
                disposableSystem.dispose();
            }
        }

    }
}
