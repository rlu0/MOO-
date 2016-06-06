import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DeathMatch extends gameMode{
	private static final int WIN_SCORE =30;
	
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
