package davey.scott.ce881_assignment;

import android.media.MediaPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public class World implements Serializable {
    private ArrayList<GameObject> objects;
    private ArrayList<GameObject> pending;
    private ArrayList<FoodParticle> food;
    private Player player;
    int worldWidth;
    int worldHeight;
    private int playerCount;
    private MediaPlayer mediaPlayer;


    public World(int sizeX, int sizeY) {
        objects = new ArrayList<>();
        pending = new ArrayList<>();
        food = new ArrayList<>();
        player = new Player(this, new Vector2D(sizeX/2,sizeY/2));
        worldHeight = sizeY;
        worldWidth = sizeX;
        objects.add(new SmarterEnemy(this,new Vector2D(sizeX/2+70,sizeY/2+70), 50));
        objects.add(player);
        playerCount = 2;
    }

    public void reset() {
        objects = new ArrayList<>();
        player = new Player(this, new Vector2D(worldWidth/2,worldHeight/2));
        objects.add(player);
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public List<FoodParticle> getFood() {
        return food;
    }

    public void setMediaPlayer(MediaPlayer m) {
        mediaPlayer = m;
    }

    public Player getPlayer() {
        return player;
    }

    public void addGameObject(GameObject obj) {
        pending.add(obj);
    }

    public void addRandomParticles(int amount) {
        for (int i = 0; i < amount; i++) {
            food.add(spawnFood());
        }
    }

    public boolean objectInRegion(GameObject object, float minX, float maxX, float minY, float maxY) {
        double posX = object.getPosition().x;
        double posY = object.getPosition().y;
        if (posX > minX && posX < maxX && posY > minY && posY < maxY) {
            return true;
        }
        return false;
    }

    public FoodParticle spawnFood() {
        return FoodParticle.randomParticle(this, worldWidth, worldHeight);
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

    public Player getClosestPrey(Player from) {
        int minDistance = Integer.MAX_VALUE;
        Player minObject = null;
        for (GameObject obj : objects) {
            if ((obj instanceof Player || obj instanceof Cell) && obj.mass < from.mass &&
                    from.getPosition().dist(obj.getPosition()) < minDistance) {
                minObject = (Player)obj;
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
        int x;
        for (x = 0; x < objects.size(); x++) {
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
            activeFood.add(spawnFood());
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
