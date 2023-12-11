/**********************************************************
 * The SlowFallingObjectsTimer class has the following functions:
 *
 * Represents a timer for the "Slow Falling Objects" power-up in the game.
 * Extends Thread class to run the timer concurrently.
 * Manages the countdown and deactivation of the power-up.
 ***********************************************************/

package mainGame;

public class SlowFallingObjectsTimer extends Thread {
	
	public String name;
	private SlowFallingObjects SFO;	//SlowFallingObjects object associated with the timer
	private int time;	//Integer representing the remaining time for the power-up effect
	private final static int UPGRADED_TIME = 10; //Static variable for the total duration of the power-up effect
	public boolean alive; //Boolean indicating whether the timer is currently active
	
	// Constructor - Initializes the timer with a given SlowFallingObjects object
	// Sets the initial time and name of the power-up
	SlowFallingObjectsTimer(SlowFallingObjects SFO){
		this.SFO = SFO;
		this.time = SlowFallingObjectsTimer.UPGRADED_TIME;	
		this.name = "Bomb Slow: "; //<== change for different name; different power up
		this.alive = true; //Boolean indicating whether the timer is currently active
	}
	
	/*DoubleScoreTimer(DoubleScore doubleScore){
		this.doubleScore = doubleScore;
		this.time = DoubleScoreTimer.UPGRADED_TIME;
	}*/
	
	
	// Resets the timer to its initial time
	void refresh(){
		this.time = SlowFallingObjectsTimer.UPGRADED_TIME;
	}

	//Concurrently counts down the timer and deactivates the power-up when the time is up
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

	//Overrides the run method of the Thread class, starting the timer countdown
	@Override
	public void run(){
		this.countDown();
	}
}
