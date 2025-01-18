package es.ulpgc.dis.model;

public class BouncingBallSimulator {
    private final double dt;
    private final Physics physics;

    public BouncingBallSimulator(double dt) {
        this.dt = dt;
        this.physics = new Physics(dt);
    }

    public BouncingBall simulate(BouncingBall ball) {
        double timeToBounce = physics.timeToBounceOf(ball.h(), ball.r(), ball.v());
        return new BouncingBall(
                ball.id(),
                ball.x(),
                ball.r(),
                physics.newHeightOf(ball.h(), ball.r(), ball.v(), ball.g(), timeToBounce, ball.cr()),
                physics.newVelocityOf(ball.v(), ball.g(), timeToBounce, ball.cr()),
                ball.g(),
                ball.cr()
        );
    }
}