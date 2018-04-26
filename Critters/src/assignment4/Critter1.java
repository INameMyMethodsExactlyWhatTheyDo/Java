package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Kyle Zhou
 * KZ3528
 * 
 * This critter has a preset moves which lets him go int a 
 * circle but not exactly becuase he will vary when to run and walk
 * and sometiems skip a step as well
 * 
 * He avoids his own kind and feeds off algae easily
*/
import java.util.*;

public class Critter1 extends Critter {
    
    int dir = 0;
    int state = 0;

	@Override
	public void doTimeStep() {
        int ran = Critter.getRandomInt(100);
        dir += 1;
        dir %= 8;
        state = Critter.getRandomInt(3);
        if(state == 0){
            walk(dir);
        }else if(state == 1){
            run(dir);
        }else{
        }
        if(ran % 10 == 0){
            try{
                Class c = this.getClass();
                Critter offspring = (Critter)c.newInstance();
                this.reproduce(offspring, Critter.getRandomInt(8));
            }catch( InstantiationException | IllegalAccessException e){
            }
        }
	}

	@Override
	public boolean fight(String opponent) {
        if(opponent.equals("@")){
            return true;
        }
        if(opponent.equals("1")){
            run(Critter.getRandomInt(8));
            return false;
        }
        if (getEnergy() > 50){
            return true;
        }
        if(state == 0){
            run(Critter.getRandomInt(8));
        }else{
            walk(Critter.getRandomInt(8));
        }
		return false;
    }
    

	public String toString() {
		return "1";
	}
}