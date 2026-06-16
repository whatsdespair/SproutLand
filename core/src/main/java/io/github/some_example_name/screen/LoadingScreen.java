package io.github.some_example_name.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import io.github.some_example_name.Main;
import io.github.some_example_name.asset.AssetService;
import io.github.some_example_name.asset.AtlasAsset;

public class LoadingScreen extends ScreenAdapter {
    private final Main game;
    private final AssetService assetService;

    public LoadingScreen(Main game, AssetService assetService) {
        this.game = game;
        this.assetService = assetService;

    }

    @Override
    public void show() {
        for (AtlasAsset atlas : AtlasAsset.values()) {
            assetService.queue(atlas);
        }
    }

    @Override
    public void render(float delta) {
       if(this.assetService.update()){
           Gdx.app.debug("LoadingScreen", "Assets loaded");
           createScreens();
           this.game.removeScreen(this);
           this.dispose();
           this.game.setScreen(GameScreen.class);
       }
    }

    private void createScreens(){
    this.game.addScreen(new GameScreen(this.game));
    }
}
