package clans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Kyrick
 *
 */
public class Clan {
	/**
	 * 
	 */
	public ArrayList<Inu> members;
	/**
	 * food stores for clan
	 */
	public Food stores;
	

	/**
	 * 
	 */
	public Clan() {
		super();
		this.members = new ArrayList<Inu>();
		this.stores = new Food();
	}
	
	/**
	 * @param party 
	 */
	public void shareMemories(ArrayList<Inu> party){
		for (int i = 0; i < party.size();i++){
			//if (party.get(i).memory.get(party.get(i).memory.size()-1));
		}
	}
	
	/**
	 * @param clan
	 */
	public void oohlala(){ 
		int avgHorny = 0,avgMaxHP = 0,avgAttack = 0, avgHostile = 0, avgIntel = 0, avgMemory = 0, avgMaxAge = 0;
		
		Iterator<Inu> itr = this.members.iterator();
		while(itr.hasNext()){
			Inu inu = itr.next();
			avgHorny += inu.getHorny();
			avgMaxHP += inu.maxHP;
			avgAttack += inu.getAttack();
			avgIntel += inu.getIntelligence();
			avgMemory += inu.getMemorySize();
			avgHostile += inu.getHostile();
			avgMaxAge += inu.getMaxAge();
		}
		
		avgHorny /= this.members.size();
		avgMaxHP /= this.members.size();
		avgAttack /= this.members.size();
		avgIntel /= this.members.size();
		avgMemory /= this.members.size();
		avgHostile /= this.members.size();
		avgMaxAge /= this.members.size();
		
		Random bed = new Random();
		int sex = bed.nextInt(100)+1;
		if (sex < avgHorny){
			int breed = (int)((avgHorny/100.0)*members.size()+bed.nextInt(5));
			//TODO remove after testing
			System.out.println("New inu born: " + breed);
			for (int i = 0; i < breed; i++){
				int gender = bed.nextInt(1);
				if (gender == 1){
					Inu inuMale = new Inu(avgMaxHP,0,0,0,avgAttack,avgHostile,1,avgMaxAge,avgIntel,avgMemory,true,avgHorny);
					this.members.add(inuMale);				
				}
				else{
					Inu inuFemale = new Inu(avgMaxHP,0,0,0,avgAttack,avgHostile,1,avgMaxAge,avgIntel,avgMemory,false,avgHorny);
					this.members.add(inuFemale);		
				}

			}
		}
	}
	
	/**
	 * feed and rest every party member
	 */
	public void restEat(){
		int size = this.members.size();
		int originalSize = this.members.size();
		for (int i = 0; i < this.members.size(); i ++){
			//heal inu by 20% of the food value + 20% of their max health + the poison value
			int gain = (int)(.2*this.stores.food+.2*this.members.get(i).maxHP)-this.stores.poison;
			this.members.get(i).advanceAge();
			this.members.get(i).setHp(this.members.get(i).getHp()+gain,this.members);
			if (this.members.size() < size){
				size--;
				i--;
			}
		}
		System.out.println("Inu died of bad food: " + (originalSize-this.members.size()));
	}

	/**
	 * add food to stores
	 * @param food
	 */
	public void addToStore(Food food){
		this.stores.food = food.food;
		this.stores.poison = food.poison;
		this.stores.taste = food.taste;
	}
	
	/**
	 * remove dead Inus
	 */
	public void mournDead(){
		int originalSize = this.members.size();
		for (int i = 0; i < this.members.size(); i++){
			if (this.members.get(i).getAge() == this.members.get(i).getMaxAge()){
				this.members.remove(i);
				i--;
			}
		}
		//TODO remove after testing
		System.out.println("Inu died of old age: " + (originalSize - this.members.size()));
	}
	
	/**
	 * @return random name
	 */
	public String nameGenome(){
		//TODO add better generator rules, etc.
		String name = "";
		try {
			NameGenerator generator = new NameGenerator("name_rules_evles.txt");
			name = generator.compose(2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return name;
	}
}
