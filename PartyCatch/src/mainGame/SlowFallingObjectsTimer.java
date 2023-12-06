package mainGame;

public class SlowFallingObjectsTimer extends Thread {
	
	public String name;
	private SlowFallingObjects SFO;
	private int time;
	private final static int UPGRADED_TIME = 10; //<== edit this for power up time
	public boolean alive;
	
	SlowFallingObjectsTimer(SlowFallingObjects SFO){
		this.SFO = SFO;
		this.time = SlowFallingObjectsTimer.UPGRADED_TIME;	
		this.name = "Bomb Slow: "; //<== change for different name; different power up
		this.alive = true;
	}
	
	/*DoubleScoreTimer(DoubleScore doubleScore){
		this.doubleScore = doubleScore;
		this.time = DoubleScoreTimer.UPGRADED_TIME;
	}*/
	
	

	void refresh(){
		this.time = SlowFallingObjectsTimer.UPGRADED_TIME;
	}

	/*
     * Counts down and deactivate the power-up
     * */
	private void countDown(){
		while(this.time!=0){
			GameTimer.times = this.time;
			GameTimer.pUpNames = this.name; //<== name to be print on powerup name
			try{
				Thread.sleep(1000);
				this.time--;
			}catch(InterruptedException e){
				System.out.println(e.getMessage());
			}
		}
		this.SFO.deactivate();
		GameTimer.pUpNames = ""; //<== reset name and time after countdown
		GameTimer.times = 0; //^^
	}

	@Override
	public void run(){
		this.countDown();
	}
}
