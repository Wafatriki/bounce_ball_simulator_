package es.ulpgc.dis;

import es.ulpgc.dis.model.BouncingBall;
import es.ulpgc.dis.model.BouncingBallSimulator;
import es.ulpgc.dis.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        es.ulpgc.dis.controller.BouncingBallPresenter presenter = new es.ulpgc.dis.controller.BouncingBallPresenter(mainFrame.getBallDisplay(), new BouncingBallSimulator(0.001))
                .add(new BouncingBall("1", -10, 0.8, 25, 0, -9.8, 0.8))
                .add(new BouncingBall("2", 0, 0.8, 25, 0, -1.625, 0.5))
                .add(new BouncingBall("3", 10, 0.8, 1, 0, -24.79, 10));
        presenter.execute();
        mainFrame.setVisible(true);
    }
}