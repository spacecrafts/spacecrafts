package se.jbee.game.any.process;

public interface Player {

	/**
	 * notifies a waiting player to now continue doing its move.
	 */
	void move();
	
	/**
	 * instructs the player to stop (terminate)
	 */
	void quit();

	/**
	 * move all...
	 */
	static void move(Iterable<Player> players) {
		for (Player p : players) {
			p.move();
		}
	}

	/**
	 * quit all... 
	 */
	static void quit(Iterable<Player> players) {
		for (Player p : players) {
			p.quit();
		}
	}

	static Thread daemon(Runnable r, String name) {
		Thread t = new Thread(r, name);
		t.setDaemon(true);
		return t;
	}

}
