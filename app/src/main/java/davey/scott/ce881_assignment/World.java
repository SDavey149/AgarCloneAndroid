package davey.scott.ce881_assignment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Davey on 15/02/2016.
 */
public class World implements Serializable {
    ArrayList<GameObject> objects;
    Player player;
    int worldWidth;
    int worldHeight;


    public World(int sizeX, int sizeY) {
        objects = new ArrayList<>();
        player = new Player(this, new Vector2D(sizeX/2,sizeY/2));
        worldHeight = sizeY;
        worldWidth = sizeX;
    }

    public void reset() {
        objects = new ArrayList<>();
        player = new Player(this, new Vector2D(worldWidth/2,worldHeight/2));
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public int countFood() {
        int count = 0;
        for (GameObject obj : objects) {
            if (obj instanceof FoodParticle) {
                count++;
            }
        }
        return count;
    }

    public Player getPlayer() {
        return player;
    }

    public void addGameObject(GameObject obj) {
        objects.add(obj);
    }

    public void addRandomParticles(int amount) {
        for (int i = 0; i < amount; i++) {
            spawnFood(objects);
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

    public void spawnFood(List<GameObject> list) {
        list.add(FoodParticle.randomParticle(worldWidth, worldHeight));
    }

    public void update(double delta) {
        ArrayList<GameObject> active = new ArrayList<>();
        player.update(delta);
        int foodSpawns = 0;
        for (GameObject obj : objects) {
            obj.update(delta);
            if (player.collidesWith(obj)) {
                obj.isActive = false;
                if (obj instanceof FoodParticle) {
                    player.addMass(1);
                    foodSpawns++;
                }
                else if (obj instanceof Cell) {
                    player.addMass(((Cell) obj).getMass());
                }

            }
            if (obj.isActive) {
                active.add(obj);
            }
        }
        playerWallCheck();

        for (int i = 0; i < foodSpawns; i++) {
            spawnFood(active);
        }
        //clear current objects
        synchronized (this) {
            objects.clear();
            objects.addAll(active);
        }

    }

    public void playerWallCheck() {
        Vector2D playerPos = player.getPosition();
        if (playerPos.x <= 0 || playerPos.x >= worldWidth) {
            Vector2D vel = player.getVelocity();
            vel.x *= -1;
            player.setVelocity(vel);
        }
        if (playerPos.y <= 0 || playerPos.y >= worldHeight) {
            Vector2D vel = player.getVelocity();
            vel.y *= -1;
            player.setVelocity(vel);
        }
    }

}
