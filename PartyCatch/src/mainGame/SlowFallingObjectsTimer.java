package mainGame;

public class SlowFallingObjectsTimer extends Thread {
	
	public String name;
	private SlowFallingObjects SFO;
	private int time;
	private final static int UPGRADED_TIME = 10; //<== edit this for power up time
	
	SlowFallingObjectsTimer(SlowFallingObjects SFO){
		this.SFO = SFO;
		this.time = SlowFallingObjectsTimer.UPGRADED_TIME;	
		this.name = "Bomb Slow: "; //<== change for different name; different power up
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
			GameTimer.time = this.time;
			GameTimer.pUpName = this.name; //<== name to be print on powerup name
			try{
				Thread.sleep(1000);
				this.time--;
			}catch(InterruptedException e){
				System.out.println(e.getMessage());
			}
		}
		this.SFO.deactivate();
		GameTimer.pUpName = ""; //<== reset name and time after countdown
		GameTimer.time = 0; //^^
	}

	@Override
	public void run(){
		this.countDown();
	}
}
