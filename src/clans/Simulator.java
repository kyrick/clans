package clans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Kyrick
 *
 */
public class Simulator {
	
	private int maxAnimals;
	private int maxPlants;
	
	/**
	 * @param maxAnimals
	 * @param maxPlants
	 */
	public Simulator(int maxAnimals, int maxPlants) {
		super();
		this.maxAnimals = maxAnimals;
		this.maxPlants = maxPlants;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
		Simulator sim = new Simulator(30,30);
		Clan clan = new Clan();
		ArrayList<Plant> plants = new ArrayList<Plant>();
		ArrayList<Animal> animals = new ArrayList<Animal>();
		sim.populateWorld(clan, plants, animals);
		int generation = 1;
		while (clan.members.size() > 0 && clan.members.size() < 100){
			System.out.println("Generation: " + generation + " ----------------------------------");
			System.out.println("Clan size: " + clan.members.size());
			clan.oohlala();	
			ArrayList<Inu> party = new ArrayList<Inu>();
			sim.formParty(party, clan);
			sim.encounter(party, clan, plants, animals);
			sim.gatherFood(party, clan);
			party = new ArrayList<Inu>();
			clan.restEat();
			//clean up anyone who died from old age... so sad
			clan.mournDead();
			generation++;
		}
		
		if (clan.members.size() == 0){
			System.out.println("Everyone died");
		}
		if (clan.members.size() > 999){
			System.out.println("The clan thrived");
		}

	}
	
	/**
	 * @param clan
	 * @param plants
	 * @param animals
	 */
	public void populateWorld(Clan clan, ArrayList<Plant> plants, ArrayList<Animal> animals){
		Random random = new Random();
		int clanSize = random.nextInt(15) + 12;
		int maxAge = random.nextInt(3)+1;
		int maxIntel = random.nextInt(100)+1;
		int maxHorny = random.nextInt(100)+1;
		int maxHP = random.nextInt(100)+20;
		int maxAttack = random.nextInt(5)+1;
		int maxHostile = random.nextInt(100)+1;
		int maxMemory = random.nextInt(50)+30;
		
		//generate clan!
		for(int i=0; i<clanSize;i+=2){
			Inu inuMale = new Inu(maxHP,0,0,0,maxAttack,maxHostile,random.nextInt(maxAge)+1,maxAge,maxIntel,maxMemory,true,maxHorny);
			clan.members.add(inuMale);
			Inu inuFemale = new Inu(maxHP,0,0,0,maxAttack,maxHostile,random.nextInt(maxAge)+1,maxAge,maxIntel,maxMemory,false,maxHorny);
			clan.members.add(inuFemale);
		}
		
		//generate animals!
		for (int i=0; i<this.maxAnimals;i++){
			int hp = random.nextInt(100)+20;
			int attack = random.nextInt(20)+1;
			int poison = random.nextInt(201)-100;
			int food = random.nextInt(100)+1;
			int taste = random.nextInt(201)-100;
			int hostile = random.nextInt(201)-100;
			
			Animal animal = new Animal(hp,poison,food,taste,attack,hostile);
			
			animals.add(animal);
		}
		
		//generate plants!
		for (int i=0; i<this.maxPlants;i++){
			int hp = random.nextInt(100)+20;
			int attack = random.nextInt(10)+1;
			int poison = random.nextInt(201)-100;
			int food = random.nextInt(100)+1;
			int taste = random.nextInt(201)-100;
			int hostile = random.nextInt(201)-100;
			
			Plant plant = new Plant(hp,poison,food,taste,attack,hostile);
			
			plants.add(plant);
		}
	}
	
	/**
	 * gather collected food back into the stores
	 * @param party
	 * @param clan
	 */
	public void gatherFood(ArrayList<Inu> party, Clan clan){
		Food food = null;
		Iterator<Inu> itr = party.iterator();
		while(itr.hasNext()){
			Inu inu = itr.next();
			if (inu.getHp() != 0){
				food = new Food();
				food.food = inu.collectedFood.food;
				food.poison = inu.collectedFood.poison;
				food.taste = inu.collectedFood.taste;
			}
		}
		
		if (food != null){
			clan.stores.food = food.food;
			clan.stores.poison = food.poison;
			clan.stores.taste = food.taste;
		}
		else{
			clan.stores.food = 0;
			clan.stores.poison = 0;
			clan.stores.taste = 0;
		}
	}
	
	/**
	 * form party of curious Inu
	 * @param party
	 * @param clan
	 */
	public void formParty(ArrayList<Inu> party, Clan clan){
		Random random = new Random();
		int clanSize = clan.members.size();
		int partySize = random.nextInt(4)+2;
		
		if (clanSize > 10){
			for(int i = 0; i<partySize;i++){
				int thisMember = random.nextInt(clanSize);
				while (party.contains(clan.members.get(thisMember))){
					thisMember = random.nextInt(clanSize);
				}
				party.add(clan.members.get(thisMember));
			}
		}
		//if we have too few clan members we might as well get this over with
		//it's not genocide! It's their only hope!
		else{
			Iterator<Inu> itr = clan.members.iterator();
			while (itr.hasNext()) party.add(itr.next());
		}
	}

	/**
	 * @param party
	 * @param clan
	 * @param plants
	 * @param animals
	 */
	public void encounter(ArrayList<Inu> party, Clan clan, ArrayList<Plant> plants, ArrayList<Animal> animals){
		//encounter animal
		Random random = new Random();
		int randomAnimal = random.nextInt(animals.size());
		Animal newAnimal = null;
		newAnimal = this.newAnimal(animals.get(randomAnimal));
		Memory oldMemory = null;
		System.out.println("Encounter");
		//check memory
		Iterator<Inu> partyMember = party.iterator();
		while (partyMember.hasNext()){
			Inu inu = partyMember.next();
			if (inu.memory != null){
				Integer value = null;
				Iterator<Memory> itr = inu.memory.iterator();
				while(itr.hasNext()){
					Memory memory = itr.next();
					if (memory.target == animals.get(randomAnimal)){
						value = inu.valueMemory(memory);
						System.out.println("The party rembered the " + memory.targetName);
						oldMemory = memory;
						//memory discovered, break loop and deal with the encounter
						break;
					}
				}
				if (value != null){
					//animal is not worth attacking... RUN!!
					if (value < 0 && value != null){
						System.out.println("They ran away!");
						break;
					}
					//animal is worth attacking!
					else if (value >= 0 && value != null){
						//run combat leave for next encounter
						System.out.println("They decided to attack!");
						this.combat(party, clan, newAnimal, true, oldMemory.targetName);
						break;
					}
				}
			}
		}
		
		if (totalHP(party) == 0){
			return;
		}
		
		//check animal hostility
		if (newAnimal.getHostile() > 20 && newAnimal.hp > 0){
			this.combat(party, clan, newAnimal, false, null);
		}
		//if animal is not hostile than Inu decide if they wish to attack
		else{
			int avgHostile = 0;
			partyMember = party.iterator();
			while(partyMember.hasNext()){
				Inu inu = partyMember.next();
				avgHostile += inu.getHostile();
			}
			avgHostile /= party.size();
			if (avgHostile > 20){
				this.combat(party, clan, newAnimal, true, null);
			}
		}
		
		
		//TODO plant encounter
	}
	
	/**
	 * run combat scenario
	 * @param party 
	 * @param clan 
	 * @param target 
	 * @param partyFirst 
	 */
	public void combat(ArrayList<Inu> party, Clan clan, Genome target, boolean partyFirst, String oldName){
		int totalDamage = 0, totalDead = 0, totalFood = 0, totalTaste = 0, totalPoison = 0, partyHP = 0;
		int startingSize = party.size();
		if (oldName == null){
			oldName = clan.nameGenome();
		}
		Iterator<Inu> combatParty = party.iterator();
		
		//check if monster gets first move
		if (partyFirst == false){
			while(combatParty.hasNext()){
				Inu inu = combatParty.next();
				inu.setHp(inu.getHp()-target.attack, clan.members);
			}
			combatParty = party.iterator();
		}
		//total hp
		partyHP = this.totalHP(party);
		
		//main combat loop.... ALL OUT ATTACK!
		while (partyHP > 0 && target.hp > 0){
			//party attacks mob... FOR SPARTA!!!
			combatParty = party.iterator();
			while(combatParty.hasNext()){
				Inu inu = combatParty.next();
				target.setHp(target.getHp()-inu.attack);
			}
			//mob attacks
			if (target.hp > 0){
				combatParty = party.iterator();
				while(combatParty.hasNext()){
					Inu inu = combatParty.next();
					inu.setHp(inu.getHp()-target.attack, clan.members);
				}
			}
			//check if party has been wiped out yet
			partyHP = this.totalHP(party);
		}
		
		//find dead members
		totalDead = startingSize - party.size();
		
		//generate new memories, gather food
		if (target.hp == 0){
			totalDamage = target.attack;
			totalFood = target.food;
			totalPoison = target.poison;
			totalTaste = target.taste;
			combatParty = party.iterator();
			while(combatParty.hasNext()){
				Inu inu = combatParty.next();
				if (inu.hp > 0){
					inu.formMemory(target, totalDamage, totalFood, totalPoison, totalTaste, totalDead, oldName);
				}
			}
			//TODO remove after testing
			System.out.println("The " + oldName + " was slain by a party of " + party.size() + " Inu.");
			System.out.println("It was harvested for " + target.food + " food, " + target.poison + " poison, and " + target.taste + " taste.");
			combatParty = party.iterator();
			while(combatParty.hasNext()){
				Inu inu = combatParty.next();
				if (inu.hp != 0){
					inu.collectedFood.food = target.getFood();
					inu.collectedFood.poison = target.getPoison();
					inu.collectedFood.taste = target.getTaste();
				}
			}
		}
		else{
			System.out.println("The mighty " + oldName + " killed a party of " + startingSize + " Inu");
		}
	}

	/**
	 * @param party
	 * @return total party HP
	 */
	public int totalHP(ArrayList<Inu> party){
		int totalHP = 0;
		Iterator<Inu> itr = party.iterator();
		while (itr.hasNext()){
			Inu inu = itr.next();
			totalHP += inu.hp;
		}
		
		return totalHP;
	}
	
	/**
	 * instantiate a new copy of an existing animal
	 * @param newAnimal
	 * @param oldAnimal
	 */
	public Animal newAnimal(Animal oldAnimal){
		Animal newAnimal = new Animal(oldAnimal.getHp(),oldAnimal.getPoison(), oldAnimal.getFood(),oldAnimal.getTaste(), oldAnimal.getTaste(), oldAnimal.getHostile());
		return newAnimal;
	}
	

	/**
	 * instantiate a new copy of an existing plant
	 * @param newPlant
	 * @param oldPlant
	 */
	public void newPlant(Plant newPlant, Plant oldPlant){
		//TODO instantiate new plant object from old plant
	}
}
