package clans;

/**
 * @author Kyrick
 *
 */
public abstract class Genome {
	protected int hp;
	/**
	 * hurts if positive, heals if negative
	 */
	protected int poison;
	/**
	 * food value of object. Should only be 0+
	 */
	protected int food;
	/**
	 * how much the Inu enjoys eatings this
	 */
	protected int taste;
	/**
	 * attack should only be 0+. At 0, object can't attack.
	 */
	protected int attack;
	/**
	 * value of hostility. Positive, hostile. Zero, passive.
	 */
	protected int hostile;
	protected int maxHP;
	
	/**
	 * @param hp
	 * @param poison
	 * @param food
	 * @param taste
	 * @param attack
	 * @param hostile
	 */
	public Genome(int hp, int poison, int food, int taste, int attack,
			int hostile) {
		super();
		this.hp = hp;
		this.poison = poison;
		this.food = food;
		this.taste = taste;
		this.attack = attack;
		this.hostile = hostile;
		this.maxHP = hp;
	}
	
	/**
	 * @return attack value
	 */
	public int getAttack() {
		return attack;
	}
	/**
	 * @return food value
	 */
	public int getFood() {
		return food;
	}
	/**
	 * @return hp
	 */
	public int getHostile() {
		return hostile;
	}
	/**
	 * @return hp
	 */
	public int getHp() {
		return hp;
	}
	/**
	 * @return poison value
	 */
	public int getPoison() {
		return poison;
	}
	/**
	 * @return taste value
	 */
	public int getTaste() {
		return taste;
	}
	/**
	 * @param hostile
	 */
	public void setHostile(int hostile) {
		this.hostile = hostile;
	}
	/**
	 * @param hp
	 */
	public void setHp(int hp) {
		if (hp < 0)hp = 0;
		if (hp > this.maxHP)hp = this.maxHP;
		this.hp = hp;
	}

}
