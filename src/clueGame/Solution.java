package clueGame;

public class Solution {

	private Card room;
	private Card person;
	private Card weapon;
	
	public Solution(Card r, Card p, Card w) {
		room = r;
		person = p;
		weapon  = w;
	}
	
	public Card getRoomSolution() {
		return room;
	}
	public Card getPersonSolution() {
		return person;
		}
	public Card getWeaponSolution() {
		return weapon;
	}
		
}
