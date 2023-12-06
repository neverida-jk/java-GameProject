package mainGame;

class DoubleScoreTimer extends Thread {
	public String name;
	private DoubleScore doubleScore;
	private int time;
	private final static int UPGRADED_TIME = 10; //<== edit this for power up time

	DoubleScoreTimer(DoubleScore doubleScore){
		this.doubleScore = doubleScore;
		this.time = DoubleScoreTimer.UPGRADED_TIME;
		this.name = "2x Score: "; //<== change for different name; different power up
	}

	void refresh(){
		this.time = DoubleScoreTimer.UPGRADED_TIME;
	}

	/*
     * Counts down and deactivate the power-up
     * */
	private void countDown(){
		while(this.time!=0){
			GameTimer.time = this.time;
			GameTimer.pUpName = this.name; //<== name to be print on powerup name
			try{
				Thread.sleep(1000);
				this.time--;
			}catch(InterruptedException e){
				System.out.println(e.getMessage());
			}
		}
		this.doubleScore.deactivate();
		GameTimer.pUpName = ""; //<== reset name and time after countdown
		GameTimer.time = 0; //^^
	}

	@Override
	public void run(){
		this.countDown();
	}

}
