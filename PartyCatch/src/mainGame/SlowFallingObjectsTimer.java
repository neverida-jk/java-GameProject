package mainGame;

public class SlowFallingObjectsTimer extends Thread {
	
	//private DoubleScore doubleScore;
	private SlowFallingObjects SFO;
	private int time;
	private final static int UPGRADED_TIME = 10; //<== edit this for power up time
	
	SlowFallingObjectsTimer(SlowFallingObjects SFO){
		this.SFO = SFO;
		this.time = SlowFallingObjectsTimer.UPGRADED_TIME;	
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
			try{
				Thread.sleep(1000);
				this.time--;
			}catch(InterruptedException e){
				System.out.println(e.getMessage());
			}
		}
		this.SFO.deactivate();
	}

	@Override
	public void run(){
		this.countDown();
	}
}
