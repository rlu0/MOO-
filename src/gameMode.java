
public abstract class gameMode {

	private int [] score;
	private Player [] players;
	private int[] maps;
	// Things relating to movement
		static boolean wPressed = false;
		static boolean aPressed = false;
		static boolean sPressed = false;
		static boolean dPressed = false;
	
	
	
	Player [] getPlayers() {
		return players;
	}
	 void setPlayers(Player [] players) {
		this.players = players;
	}
	 int[] getMaps() {
		return maps;
	}
	 void setMaps(int[] maps) {
		this.maps = maps;
	}
	int [] getScore() {
		return score;
	}
	void setScore(int [] score) {
		this.score = score;
	}
	void setSingleScore(int score, int pos)
	{
		this.score[pos]=score;
	}
	void addScore(int score, int pos)
	{
		this.score[pos]+=score;
	}
	
	
	
}
