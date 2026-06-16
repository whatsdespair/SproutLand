package io.github.some_example_name.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import io.github.some_example_name.Main;
import io.github.some_example_name.asset.MapAssets;
import io.github.some_example_name.system.RenderSystem;
import io.github.some_example_name.tiled.TiledAshleyConfigurator;
import io.github.some_example_name.tiled.TiledService;
import java.util.function.Consumer;


public class GameScreen extends ScreenAdapter {
private  final Engine engine;
private final TiledService tiledService;
private final TiledAshleyConfigurator tiledAshleyConfigurator;

    public GameScreen(Main game){
        this.tiledService = new TiledService(game.getAssetService());
        this.engine = new Engine();
        this.tiledAshleyConfigurator = new TiledAshleyConfigurator(this.engine, game.getAssetService());

        this.engine.addSystem(new RenderSystem(game.getBatch(), game.getViewport(), game.getCamera()));
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
