package clans;

import java.util.ArrayList;

/**
 * @author Kyrick
 *
 */
public class Inu extends Genome{
	
	private int age;
	public int getAge() {
		return age;
	}

	private int maxAge;
	public int getMaxAge() {
		return maxAge;
	}

	private int intelligence;
	private int memorySize;
	/**
	 * food collected during combat
	 */
	public Food collectedFood;
	
	/**
	 * memories of past encounters
	 */
	public ArrayList<Memory> memory;
	private boolean isMale;
	private int horny;
	public int getIntelligence() {
		return intelligence;
	}

	public int getMemorySize() {
		return memorySize;
	}

	public int getHorny() {
		return horny;
	}

	public int getHunger() {
		return hunger;
	}

	private int hunger;
	/**
	 * number of kills the Inu has
	 */
	public int kills;
	
	/**
	 * @param hp
	 * @param poison
	 * @param food
	 * @param taste
	 * @param attack
	 * @param hostile
	 * @param age
	 * @param maxAge
	 * @param intelligence
	 * @param memorySize
	 * @param isMale
	 * @param horny
	 */
	public Inu(int hp, int poison, int food, int taste, int attack,
			int hostile, int age, int maxAge, int intelligence, int memorySize, boolean isMale, int horny) {
		super(hp, poison, food, taste, attack, hostile);
		//TODO add randomize for stats
		this.age = age;
		this.maxAge = maxAge;
		this.intelligence = intelligence;
		this.memorySize = memorySize;
		this.isMale = isMale;
		this.horny = horny;
		this.collectedFood = new Food();
		this.memory = new ArrayList<Memory>();
	}

	/**
	 * advance age
	 */
	public void advanceAge(){
		this.age++;
	}
	
	/**
	 * @param memory
	 * @return value
	 */
	public int valueMemory(Memory memory){
		int value = 0;
		int damage = memory.damage;
		int gain = memory.gain;
		
		if (gain!= 0)gain -= this.intelligence;
		value = gain - damage;
				
		return value;
	}
	
	/**
	 * @param target
	 * @param damage
	 * @param gain
	 * @param name
	 */
	public void formMemory(Genome target,int damage,int food, int poison, int taste, int dead, String name){
		if (this.memory.size() < memorySize){
			this.memory.add(new Memory());
			this.memory.get(this.memory.size()-1).damage = damage + poison;
			this.memory.get(this.memory.size()-1).gain = food + taste;
			this.memory.get(this.memory.size()-1).target = target;
			this.memory.get(this.memory.size()-1).targetName = name;
		}
		//TODO rank memories, delete lowest
	}
	
	/**
	 * @param hp
	 * @param list
	 */
	public void setHp(int hp, ArrayList<Inu> list) {
		if (hp < 0 || hp == 0)list.remove(this);
		if (hp > this.maxHP)hp = this.maxHP;
		this.hp = hp;
	}
	
}
