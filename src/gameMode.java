
public abstract class gameMode {
private int numPlayers;
	private int[] score =  new int[getNumPlayers()];
	private int[] deaths=  new int[getNumPlayers()];
	private Player[] players=  new Player[getNumPlayers()];
	private int[] maps;

	
	void startup(int numPlayers, Player[] players){
		setNumPlayers(numPlayers);
		setPlayers(players);
		
	}
	
	
	Player[] getPlayers() {
		return players;
	}

	void setPlayers(Player[] players) {
		this.players = players;
	}

	int[] getMaps() {
		return maps;
	}

	void setMaps(int[] maps) {
		this.maps = maps;
	}

	int[] getScore() {
		return score;
	}

	void setScore(int[] score) {
		this.score = score;
	}

	void setSingleScore(int score, int pos) {
		this.score[pos] = score;
	}
	void addScore(int score, int pos) {
		this.score[pos] += score;
	}

	 int[] getDeaths() {
		return deaths;
	}

	void setDeaths(int[] deaths) {
		this.deaths = deaths;
	}

	 void addDeath(int pos) {
		deaths[pos]++;
	}


	 int getNumPlayers() {
		return numPlayers;
	}


	 void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
}
