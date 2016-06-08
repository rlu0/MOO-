import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DeathMatch extends gameMode{
	private static final int WIN_SCORE =30;
	
void  kill(int killer, int victim){
recieveKiller(killer);
getVictim(victim);
	}
	
	void recieveKiller( int pos){
		addScore(1,pos);
		
	}
	
	void getVictim(int pos){
		addDeath(pos);
	}
	
	boolean checkWinner(){
		for( int s: getScore())
		{
			if(s>=WIN_SCORE){
				return true;}
		}
		return false;
	}
	
	DeathMatch(){
	}
	DeathMatch(Player[] players){
		startup(players.length,players);					
		}
		
		
		
	}
	
	
}
