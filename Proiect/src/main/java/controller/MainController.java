package controller;

import launcher.ComponentFactory;
import view.MainView;

public class MainController {
    private final MainView mainView;

    public MainController(MainView mainView) {
        this.mainView = mainView;
        mainView.showScene(ComponentFactory.getLoginView().getScene());
    }

    public MainView getMainView() {
        return mainView;
    }
    public void setStageTitle(String text){
        mainView.setStageTitle(text);
    }
}
