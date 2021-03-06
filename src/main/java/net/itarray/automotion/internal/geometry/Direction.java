package net.itarray.automotion.internal.geometry;

import java.util.function.Function;

import static net.itarray.automotion.internal.geometry.Scalar.scalar;

public enum Direction implements ExtendGiving<Scalar> {
    DOWN(Vector::getY, 1) {
        public String beforeName() {
            return "Above";
        }

        @Override
        public Direction opposite() {
            return UP;
        }

        @Override
        public Scalar begin(Rectangle rectangle) {
            return rectangle.getOrigin().getY();
        }

        @Override
        public String beginName() {
            return "top";
        }

        @Override
        public String extendName() {
            return "height";
        }
    },
    UP(Vector::getY, -1) {
        public String beforeName() {
            return "Below";
        }

        @Override
        public Direction opposite() {
            return DOWN;
        }

        @Override
        public Scalar begin(Rectangle rectangle) {
            return rectangle.getCorner().getY();
        }

        @Override
        public String beginName() {
            return "bottom";
        }

        @Override
        public String extendName() {
            return "height";
        }

    },
    RIGHT(Vector::getX, 1) {
        public String beforeName() {
            return "Left";
        }

        @Override
        public Direction opposite() {
            return LEFT;
        }

        @Override
        public Scalar begin(Rectangle rectangle) {
            return rectangle.getOrigin().getX();
        }

        @Override
        public String beginName() {
            return "left";
        }

        @Override
        public String extendName() {
            return "width";
        }
    },
    LEFT(Vector::getX, -1) {
        public String beforeName() {
            return "Right";
        }

        @Override
        public Direction opposite() {
            return RIGHT;
        }

        @Override
        public Scalar begin(Rectangle rectangle) {
            return rectangle.getCorner().getX();
        }

        @Override
        public String beginName() {
            return "right";
        }

        @Override
        public String extendName() {
            return "width";
        }

    };

    private final Function<Vector, Scalar> projection;
    private final Scalar u;

    public abstract Direction opposite();

    public abstract Scalar begin(Rectangle rectangle);

    public Scalar end(Rectangle rectangle) {
        return opposite().begin(rectangle);
    }

    public String beginName() {
        return "begin";
    }

    public String endName() {
        return opposite().beginName();
    }

    public abstract String beforeName();

    public String afterName() {
        return opposite().beforeName();
    }

    public abstract String extendName();

    @Override
    public Scalar signedDistance(Scalar p1, Scalar p2) {
        return p2.minus(p1).times(u);
    }

    @Override
    public Function<Vector, Scalar> transform() {
        return v -> projection.apply(v).times(u);
    }

    Direction(Function<Vector, Scalar> projection, int u) {
        this.projection = projection;
        this.u = scalar(u);
    }
}
