package controlsystem.model;


import trafficParticipants.street.Crossing;

import javax.persistence.*;
import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Extension of the {@link Crossing} base class.
 * Includes properties to visualize the nodes and mapping annotations.
 */
@Entity
@Table(name = "crossings")
public class Node extends Crossing implements GraphPart {

    /**
     * Average vehicle length in the real world.
     */
    public static final double AVG_CAR_LENGTH = 5d;

    public static final int R_OFFSET = 100;
    public static final int G_OFFSET = 100;
    public static final int B_OFFSET = 200;

    /** Estimator about the maximum number of lanes. */
    public static final int MAX_LANE_EST = 20;

    public static final int MAX_RADIUS = 10;
    public static final int MIN_RADIUS = 1;

    protected Node() {
        super();
    }

    public Node(int id, int x, int y) {
        super(id, x, y);
    }

    public int nLanes() {
        return getIn().size() + getIn().size();
    }

    public final int r() {
        final double factor = 1 - sizeFactor();

        return (int) max(MAX_RADIUS * factor, MIN_RADIUS);
    }

    public final Color color() {
        // calculate a color factor based on the transition size
        final double factor = 1 - sizeFactor();

        final int r = (int) (R_OFFSET * factor);
        final int g = (int) (G_OFFSET * factor);
        final int b = B_OFFSET;

        return new Color(r, g, b);
    }

    private double sizeFactor() {
        return min(1, nLanes() / MAX_LANE_EST);
    }

    @Override
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    public int getId() {
        return super.getId();
    }

    public int x() {
        return getPosition().x;
    }

    public int y() {
        return getPosition().y;
    }
}
