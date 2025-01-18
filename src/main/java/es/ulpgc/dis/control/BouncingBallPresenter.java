package es.ulpgc.dis.controller;

import es.ulpgc.dis.model.BouncingBall;
import es.ulpgc.dis.model.BouncingBallSimulator;
import es.ulpgc.dis.view.BouncingBallDisplay;
import es.ulpgc.dis.view.BouncingBallDisplay.Circle;

import java.util.*;

public class BouncingBallPresenter {
    public static final Timer timer = new Timer();
    private static final double dt = 0.001;
    private static final int period = (int) (1000 * dt);
    private final BouncingBallDisplay ballDisplay;
    private final BouncingBallSimulator simulator;
    private List<BouncingBall> balls;
    private BouncingBall grabbedBall;

    public BouncingBallPresenter(BouncingBallDisplay ballDisplay, BouncingBallSimulator simulator) {
        this.ballDisplay = ballDisplay;
        this.simulator = simulator;
        this.ballDisplay.on(ballGrabbed());
        this.ballDisplay.off(ballReleased());
        this.balls = new ArrayList<>();
    }

    private BouncingBallDisplay.Released ballReleased() {
        return _ -> grabbedBall = null;
    }

    private BouncingBallDisplay.Grabbed ballGrabbed() {
        return c -> grabbedBall = toBall(c);
    }

    private BouncingBall toBall(Circle c) {
        BouncingBall ball = findBall(c.id());
        return new BouncingBall(
                ball.id(),
                toMeters(c.x()),
                ball.r(),
                toMeters(c.y()),
                0,
                ball.g(),
                ball.cr());
    }

    private BouncingBall findBall(String id) {
        return balls.stream()
                .filter(b -> b.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public es.ulpgc.dis.controller.BouncingBallPresenter add(BouncingBall ball) {
        this.balls.add(ball);
        return this;
    }

    public void execute() {
        timer.schedule(task(), 0, period);
    }

    private TimerTask task() {
        return new TimerTask() {
            @Override
            public void run() {
                simulate();
                draw();
            }

            private void draw() {
                ballDisplay.draw(toCircles(balls));
            }
        };
    }

    private List<Circle> toCircles(List<BouncingBall> balls) {
        return balls.stream()
                .map(this::map)
                .toList();
    }

    private static final double PixelsPerMeter = 5 / 0.2;

    private Circle map(BouncingBall b) {
        return new Circle(b.id(), toPixels(b.x()), toPixels(b.h()), toPixels(b.r()));
    }

    private static int toPixels(double b) {
        return (int) (b * PixelsPerMeter);
    }

    private static double toMeters(double b) {
        return b / PixelsPerMeter;
    }

    private void simulate() {
        balls = balls.stream()
                .map(this::simulateBall)
                .toList();
    }

    private BouncingBall simulateBall(BouncingBall ball) {
        if (grabbedBall != null && ball.id().equals(grabbedBall.id())) return grabbedBall;
        return simulator.simulate(ball);
    }
}