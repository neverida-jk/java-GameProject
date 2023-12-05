package mainGame;

class DoubleScoreTimer extends Thread {
	private DoubleScore doubleScore;
	private int time;
	private final static int UPGRADED_TIME = 5; //<== edit this for power up time

	DoubleScoreTimer(DoubleScore doubleScore){
		this.doubleScore = doubleScore;
		this.time = DoubleScoreTimer.UPGRADED_TIME;
	}

	void refresh(){
		this.time = DoubleScoreTimer.UPGRADED_TIME;
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
		this.doubleScore.deactivate();
	}

	@Override
	public void run(){
		this.countDown();
	}

}
