import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DeathMatch extends gameMode{
	private static final int WIN_SCORE =30;
	
void  kill(int killer, int victim){
recieveKiller(killer);
getVictim(victim);
	}
	
	void recieveKiller( int pos){
		addScore(pos)++;
		
	}
	
	void getVictim(int pos){
		addDeath(pos);
	}
	
	
	DeathMatch(Player[] players){
		setPlayers(players);
		boolean game= true;
	
		while (game){
			//Check if game is over
			for( int s: getScore())
			{
				if(s>=WIN_SCORE){
					game=false;
					break;				
				}
			}
			
			
			
			
			
			
			
			
		}
		
		
		
	}
	
	
}
