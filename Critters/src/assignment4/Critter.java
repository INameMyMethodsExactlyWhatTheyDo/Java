package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Kyle Zhou
 * KZ3528
 */
import java.util.List;
/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */

public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static int w = Params.world_width;
	private static int h = Params.world_height;


	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return "C"; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private boolean moved = false;	//if the critter has moved this turn
	
	/**
	 * Moves teh critter in direction by 1 spot
	 */
	protected final void walk(int direction) {
		if(!moved){
			switch(direction){
				case 0:		//up
					y_coord--;
					if(y_coord < 0){
						y_coord += h;
					}
					break;
				case 1:		//upright
					y_coord--;
					x_coord++;
					if(y_coord < 0){
						y_coord += h;
					}
					x_coord %= w;
					break;
				case 2:		//right
					x_coord++;
					x_coord %= w;
					break;
				case 3:		//downright
					y_coord++;
					x_coord++;
					y_coord %= h;
					x_coord %= w;
					break;
				case 4:		//down
					y_coord++;
					y_coord %= h;
					break;
				case 5:		//downleft
					y_coord++;
					y_coord %= h;
					x_coord--;
					if(x_coord < 0){
						x_coord += w;
					}
					break;
				case 6:		//left
					x_coord--;
					if(x_coord < 0){
						x_coord += w;
					}
					break;
				case 7:		//upleft
					x_coord--;
					y_coord--;
					if(x_coord < 0){
						x_coord += w;
					}
					if(y_coord < 0){
						y_coord += h;
					}
					break;
				}
		}
		moved = true;
		this.energy -= Params.walk_energy_cost;
		
	}
	/**
	 * Moves the Critter in said direction by 2 spots
	 */
	protected final void run(int direction) {
		if(!moved){
			switch(direction){
				case 0:		//up
					y_coord -= 2;
					if(y_coord < 0){
						y_coord += h;
					}
					break;
				case 1:		//upright
					y_coord -= 2;
					x_coord += 2;
					if(y_coord < 0){
						y_coord += h;
					}
					x_coord %= w;
					break;
				case 2:		//right
					x_coord += 2;
					x_coord %= w;
					break;
				case 3:		//downright
					y_coord += 2;
					x_coord += 2;
					y_coord %= h;
					x_coord %= w;
					break;
				case 4:		//down
					y_coord += 2;
					y_coord %= h;
					break;
				case 5:		//downleft
					y_coord += 2;
					y_coord %= h;
					x_coord -= 2;
					if(x_coord < 0){
						x_coord += w;
					}
					break;
				case 6:		//left
					x_coord -= 2;
					if(x_coord < 0){
						x_coord += w;
					}
					break;
				case 7:		//upleft
					x_coord -= 2;
					y_coord -= 2;
					if(x_coord < 0){
						x_coord += w;
					}
					if(y_coord < 0){
						y_coord += h;
					}
					break;
			}
		}
		moved = true;
		this.energy -= Params.run_energy_cost;
		
	
	}
	/**
	 * This method will check to see if a critter has enough 
	 * energy to make babies
	 * if so then it will add the baby to the babies list
	 */
	protected final void reproduce(Critter offspring, int direction) {
		
		if(this.energy > Params.min_reproduce_energy ){
			if(this.energy %2 == 1){
				offspring.energy = this.energy/2;
				this.energy = this.energy/2 + 1;

			}else{
				offspring.energy = this.energy/2;
				this.energy = this.energy/2;
			}
			offspring.x_coord = this.x_coord;
			offspring.y_coord = this.y_coord;
			offspring.walk(direction);
			babies.add(offspring);
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			Class C =Class.forName(critter_class_name);
			Critter crit = (Critter)C.newInstance();
			crit.energy = Params.start_energy;
			crit.x_coord = Critter.getRandomInt(w);
			crit.y_coord = Critter.getRandomInt(h);
			Critter.population.add(crit);
		}catch(ClassNotFoundException | InstantiationException | IllegalAccessException e1){
			throw new InvalidCritterException(critter_class_name);
		}catch(ClassCastException e){
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try{
			for(Critter x : Critter.population){
				if(x.getClass() == Class.forName(critter_class_name)){
					result.add(x);
				}
			}	
		}catch(ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		Critter.population.clear();
		Critter.babies.clear();
	}
	
	/**
	 * this method will do these following things 
	 * doTimeStep for all critters
	 * remove dead critters
	 * Fight critters
	 * reduce energy of critters at rest
	 * remove dead critters
	 * produce off spring
	 * refresh the algae
	 * import the babies
	 */
	public static void worldTimeStep() {
		//do time step 
		for(Critter x : Critter.population){
			x.doTimeStep();
		}
		//remove dead critters
		cleanBodies();
		//fight, this cleans bocies internally
		while(conflict()){
		}
		//process rest energy ie if critter did not move
		for(Critter x: Critter.population){
			if(!x.moved){
				x.energy -= Params.rest_energy_cost;
			}
		}
		cleanBodies();	
		//perform offspring make critter same calss as babie
		
		//add those alea in the areaa 
		refresh();
		//now add those babies in the arena 
		Critter.population.addAll(babies);
		babies.clear();	
	}
	/**
	 * Helper method, this method will find all critters with 
	 * engergy less than or equal to zero
	 * and remove them from this world.
	 */
	private static void cleanBodies(){
		for(int i = 0; i < Critter.population.size(); i++){
			if(isDead(Critter.population.get(i))){
				i--;
			}
		}
	}

	/**
	 * This is a method that resolves conflics from critters
	 * such they if there is an overlap it will return true
	 * and also resolve that over lap
	 * Usually should be called in a while loop
	 * so that it'll remove conflics untill there are none
	 */
	private static boolean conflict(){
		int[][] check = new int[w][h];
		int[][] index = new int[w][h];
		int attacka = 0;
		int attackb = 0;
		//boolean fight = true;
		Critter y;
		for(Critter x: Critter.population){
			check[x.x_coord][x.y_coord] += 1;
			if(check[x.x_coord][x.y_coord] == 2){
				//System.out.println("Fight!!!! at" +x.x_coord  + ", " +x.y_coord );
				y = Critter.population.get(index[x.x_coord][x.y_coord]);			
				// x deciding 
				if(x.fight(y.toString())){
					if(x.energy > 0){
						attacka = Critter.getRandomInt(x.energy);
					}
				}else{
					attacka = 0;
				}
				//y deciding
				if(y.fight(x.toString())){
					if(y.energy > 0){
						attackb = Critter.getRandomInt(y.energy);
					}
				}else{
					attackb = 0;
				}
				//fight
				if(x.x_coord ==  y.x_coord && x.y_coord == y.y_coord){
					if(attacka > attackb){
						x.energy += y.energy/2;
						Critter.population.remove(y);
					}else{
						y.energy += x.energy/2;
						Critter.population.remove(x);
					}
				}
				//cleanBodies();
				return true;
			}
			index[x.x_coord][x.y_coord] = Critter.population.indexOf(x);
		}
		return false;
	}
	/**
	 * Checks if a Critter has energy below 0;
	 * If so than the critter will be removed
	 */
	private static boolean isDead(Critter x){
		if(x.getEnergy() <= 0){
			Critter.population.remove(x);
			return true;
		}
		return false;
	}
	/**
	 * This method will count the number of algae
	 * and then create algae untill we raach the minimun
	 */
	private static void refresh(){
		int run = 0;
		for(Critter x : Critter.population){
			if(x.toString().equals("@")){
				run++;
			}
		}
		for(int i = 0; i < Params.refresh_algae_count; i++){
			try{
				Critter.makeCritter("assignment4.Algae");
			}catch(InvalidCritterException e){
			}
		}
		for(Critter x : Critter.population){
			x.moved = false;
		}
	}
	/**
	 * This method will draw out the whole map
	 */
	public static void displayWorld() {
		// Complete this method.
		boolean empty = true;
		System.out.print("+");
		for(int i = 0; i < w; i++){
			System.out.print("-");
		}
		System.out.println("+");
		for(int i = 0; i < h; i++){
			for(int k = 0; k < w; k++){
				if(k == 0){
					System.out.print("|");
				}
				for(Critter x: Critter.population){
					if(x.x_coord == k && x.y_coord == i){
						System.out.print(x.toString());
						empty = false;
					}	
				}
				if(empty){
					System.out.print(" ");
				}
				empty = true;
			}
			System.out.println("|");
		}
		System.out.print("+");
		for(int i = 0; i < w; i++){
			System.out.print("-");
		}
		System.out.println("+");
	}

	public static void displayWorldDebug(){
		// Complete this method.
		boolean empty = true;
		System.out.print("+");
		for(int i = 0; i < w; i++){
			System.out.print("-");
		}
		System.out.println("+");
		for(int i = 0; i < h; i++){
			for(int k = 0; k < w; k++){
				if(k == 0){
					System.out.print("|");
				}
				for(Critter x: Critter.population){
					if(x.x_coord == k && x.y_coord == i){
						System.out.print(x.toString());
						empty = false;
					}	
				}
				if(empty){
					System.out.print(" ");
				}
				empty = true;
			}
			System.out.println("|");
		}
		System.out.print("+");
		for(int i = 0; i < w; i++){
			System.out.print("-");
		}
		System.out.println("+");

		for(int i = 0 ; i < Critter.population.size(); i++){
			System.out.println("Critter " + Critter.population.get(i).toString() +
			" has " + Critter.population.get(i).energy );
		}
	}
}
