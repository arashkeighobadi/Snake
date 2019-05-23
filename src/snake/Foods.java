package snake;

import java.util.ArrayList;

/**
 *
 * @author Arash
 */
public class Foods {
    
    private final int FOOD_WIDTH = 20;
    ArrayList<Food> list;
    
    public Foods() {
        list = new ArrayList<>();
    }
    
    public void addFood(Food food) {
        list.add(food);
    }
    
    public ArrayList<Food> getList() {
        return list;
    }
    
    public boolean gotEaten(Snake snake) { //Snake collides with a Food
        Food eatenFood = null;
        for (Food food : list) {
            if (snake.collides(food)) {
                eatenFood = food;
                snake.grow();
                break;
            }
        }
        if (eatenFood != null) {
            list.remove(eatenFood);
            return true;
        } else {
            return false;
        }
    }
    
    public int getFoodWidth() {
        return FOOD_WIDTH;
    }
    
    
}
