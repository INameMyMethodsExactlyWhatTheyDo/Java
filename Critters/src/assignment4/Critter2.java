/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Kyle Zhou
 * KZ3528
 * This is the random critter he is lazy and he likes to walk mostly
 * and when he goes he goes by a random direction.
 * 
 * He avoids his own kind and don't fight
 * He eats algae unconditionally
*/
package assignment4;
import java.util.*;

public class Critter2 extends Critter{
    int state = 0;
	@Override
	public void doTimeStep() {
        state = Critter.getRandomInt(4);
        if(state == 0){
            //rest
        }else if(state == 1){
            run(Critter.getRandomInt(8));
        }else{
            walk(Critter.getRandomInt(8));
        }
        try{
            Class c = this.getClass();
            Critter offspring = (Critter)c.newInstance();
            this.reproduce(offspring, Critter.getRandomInt(8));
		}catch( InstantiationException | IllegalAccessException e){
		}
	}

	@Override
	public boolean fight(String opponent) {
        if(opponent.equals("@")){
            return true;
        }
        if(state >= 2){
            return true;
        }else{
            walk(Critter.getRandomInt(8));
            return false;
        }
	}
	
	public String toString() {
		return "2";
	}
}