package davey.scott.ce881_assignment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public class World implements Serializable {
    public static final int DESIRED_AI_COUNT = 20;
    public static final int DESIRED_SPIKE_COUNT = 8;

    private ArrayList<GameObject> objects;
    private ArrayList<GameObject> pending;
    private ArrayList<FoodParticle> food;
    private Player player;
    int worldWidth;
    int worldHeight;
    private Random random;

    public World(int sizeX, int sizeY, int playerColor) {
        food = new ArrayList<>();
        random = new Random();
        worldHeight = sizeY;
        worldWidth = sizeX;
        player = new Player(this, new Vector2D(worldWidth/2,worldHeight/2),
                playerColor);
        reset();

    }

    public void reset() {
        player.isActive = true;
        player.getPosition().set(worldWidth/2, worldHeight/2);
        player.mass = Player.INITIAL_MASS;
        pending = new ArrayList<>();
        objects = new ArrayList<>();
        objects.add(player);


        for (int i =0; i < DESIRED_SPIKE_COUNT; i++) {
            SpikeBall sb = getNewSpikeBall();
            if (!player.collidesWith(sb)) {
                objects.add(getNewSpikeBall());
            }
        }

        for (int i = 0; i < DESIRED_AI_COUNT/2; i++) {
            FleeEnemy enemy = getNewFleeEnemy();
            if (!player.collidesWith(enemy)) {
                objects.add(enemy);
            }
        }

        for (int i = DESIRED_AI_COUNT/2; i < DESIRED_AI_COUNT; i++) {
            AttackEnemy enemy = getNewAttackEnemy();
            if (!player.collidesWith(enemy)) {
                objects.add(enemy);
            }
        }
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public List<FoodParticle> getFood() {
        return food;
    }

    public Player getPlayer() {
        return player;
    }

    public void addGameObject(GameObject obj) {
        pending.add(obj);
    }

    public void addRandomParticles(int amount) {
        for (int i = 0; i < amount; i++) {
            food.add(getFoodParticle());
        }
    }

    public int getPlayerRank() {
        return objects.indexOf(player);
    }

    public boolean objectInRegion(GameObject object, float minX, float maxX, float minY, float maxY) {
        double posX = object.getPosition().x;
        double posY = object.getPosition().y;
        if (posX > minX && posX < maxX && posY > minY && posY < maxY) {
            return true;
        }
        return false;
    }

    public boolean foodInRegion(FoodParticle object, float minX, float maxX, float minY, float maxY) {
        double posX = object.getPosition().x;
        double posY = object.getPosition().y;
        if (posX > minX && posX < maxX && posY > minY && posY < maxY) {
            return true;
        }
        return false;
    }

    public FoodParticle getFoodParticle() {
        return FoodParticle.randomParticle(this, worldWidth, worldHeight);
    }

    private int enemyCount() {
        int count = 0;
        for (GameObject obj : objects) {
            if (obj instanceof Enemy) {
                count++;
            }
        }
        return count;
    }

    private int spikeBallCount() {
        int count = 0;
        for (GameObject obj : objects) {
            if (obj instanceof SpikeBall) {
                count++;
            }
        }
        return count;
    }

    public FleeEnemy getNewFleeEnemy() {
        int color = FoodParticleRender.AVAILABLE_COLORS
                [random.nextInt(FoodParticleRender.AVAILABLE_COLORS.length)];
        return new FleeEnemy(this,
                new Vector2D(random.nextInt(worldWidth-30),
                        random.nextInt(worldHeight-30)),
                random.nextInt(50),
                color);

    }

    public AttackEnemy getNewAttackEnemy() {
        int color = FoodParticleRender.AVAILABLE_COLORS
                [random.nextInt(FoodParticleRender.AVAILABLE_COLORS.length)];
        return new AttackEnemy(this,
                new Vector2D(random.nextInt(worldWidth-30),
                        random.nextInt(worldHeight-30)),
                random.nextInt(50),
                color);

    }

    public SpikeBall getNewSpikeBall() {
        return new SpikeBall(this, new Vector2D(random.nextInt(worldWidth-30),
                random.nextInt(worldHeight-30)), 20);
    }

    public Enemy getRandomEnemy() {
        int n = random.nextInt(2);
        if (n == 0) {
            return getNewFleeEnemy();
        } else {
            return getNewAttackEnemy();
        }
    }

    public Player getClosestPredator(Player from) {
        double minDistance = Double.MAX_VALUE;
        Player minObject = null;
        for (GameObject obj : objects) {
            if (obj instanceof Player && ((Player) obj).mass > from.mass &&
                    from.getPosition().dist(obj.getPosition()) < minDistance) {
                minDistance = from.getPosition().dist(obj.getPosition());
                minObject = (Player)obj;
            }
        }

        return minObject;
    }

    public GameObject getClosestPrey(Player from) {
        double minDistance = Double.MAX_VALUE;
        GameObject minObject = null;
        for (GameObject obj : objects) {
            if ((obj instanceof Player || obj instanceof Cell) && obj.mass < from.mass &&
                    from.getPosition().dist(obj.getPosition()) < minDistance) {
                minDistance = from.getPosition().dist(obj.getPosition());
                minObject = obj;
            }
        }
        return minObject;
    }

    public void update(double delta) {
        ArrayList<GameObject> active = new ArrayList<>();
        ArrayList<FoodParticle> activeFood = new ArrayList<>();

        int foodSpawns = 0;
        for (GameObject obj : objects) {
            obj.update(delta);
            objectWallCheck(obj);
            if (obj.isActive) {
                active.add(obj);
            }
        }

        //check the collisions
        for (int x = 0; x < objects.size(); x++) {
            GameObject object1 = objects.get(x);
            for (int y = x+1; y < objects.size(); y++) {
                GameObject object2 = objects.get(y);

                if (object1.collidesWith(object2) && object1.isActive && object2.isActive) {
                    object1.collided(object2);
                    object2.collided(object1);
                }
            }
            for (FoodParticle f : food) {
                if (object1.collidesWith(f)) {
                    object1.mass++;
                    f.isActive = false;
                    foodSpawns++;
                }

            }
        }

        for (FoodParticle f : food) {
            if (f.isActive) {
                activeFood.add(f);
            }
        }

        for (int i = 0; i < foodSpawns; i++) {
            activeFood.add(getFoodParticle());
        }

        if (enemyCount() < DESIRED_AI_COUNT) {
            Enemy enemy = getRandomEnemy();
            if (!player.collidesWith(enemy)) {
                pending.add(enemy);
            }
        }

        if (spikeBallCount() < DESIRED_SPIKE_COUNT) {
            SpikeBall sb = getNewSpikeBall();
            if (!player.collidesWith(sb)) {
                pending.add(sb);
            }
        }

        //clear current objects
        synchronized (this) {
            objects.clear();
            food.clear();
            objects.addAll(active);
            objects.addAll(pending);
            food.addAll(activeFood);
            pending.clear();
            //sort the objects so larger masses are drawn last by the view, this is so they
            // overlap other smaller game objects
            Collections.sort(objects);
        }

    }

    public boolean isGameOver() {
        return !player.isActive;
    }


    public void objectWallCheck(GameObject object) {
        Vector2D playerPos = object.getPosition();
        if (playerPos.x <= 0 || playerPos.x >= worldWidth) {
            Vector2D pos = object.getPosition();
            if (playerPos.x <= 0)
                pos.set(1,pos.y);
            else
                pos.set(worldWidth-1, pos.y);
            object.setPosition(pos);
            Vector2D vel = object.getVelocity();
            vel.x *= -1;
            object.getVelocity().set(vel);
        }
        if (playerPos.y <= 0 || playerPos.y >= worldHeight) {
            Vector2D pos = object.getPosition();
            if (playerPos.y <= 0)
                pos.set(pos.x, 1);
            else
                pos.set(pos.x, worldHeight-1);
            object.setPosition(pos);
            Vector2D vel = object.getVelocity();
            vel.y *= -1;
            object.getVelocity().set(vel);
        }
    }

}
