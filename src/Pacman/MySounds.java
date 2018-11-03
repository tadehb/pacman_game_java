/* Tadeh Boghoz Ahmad Abadi
 * Class: Java Programming
 * Instructor: Zareh Gorjian
 * Date:04/26/2015
 * Assignment14
 */
package Pacman;



import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


//import sun.audio.*;


public class MySounds {

	protected static final String AudioPlayer = null;
	
	public Clip PacBeginning  = loadClip("/Sounds/pacman_beginning.wav");
	public Clip PacChomp      = loadClip("/Sounds/pacman_chomp.wav");
	public Clip Pacdeath      = loadClip("/Sounds/pacman_death.wav");
	public Clip eatfruit      = loadClip("/Sounds/pacman_eatfruit.wav");
	public Clip eatGhost      = loadClip("/Sounds/pacman_eatGhost.wav");
	public Clip extrapac      = loadClip("/Sounds/pacman_extrapac.wav");
	
	/**
	 * Create the frame.
	 */
	public MySounds() {
		
	} // MySounds constructor

	public Clip loadClip(String filename)
	{
		Clip clip = null;
		
		try
		{
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(filename));
			clip = AudioSystem.getClip();
			clip.open( audioIn );
		}// try
		catch (Exception e)
		{
			e.printStackTrace();
		}// catch
		
		return(clip);
		
	} // Clip
	
	public void playClip( int index )
	{
		if (index == 1){
		   stopClip(1);
		   PacBeginning.start();
		   
		}else if (index==2){
			
		}if (!PacChomp.isRunning()){
			      
			      stopClip(2);
			      PacChomp.start();
			      
	     } else if (index == 3){				 
				 if (!Pacdeath.isRunning()){					      
					    stopClip(2);
					    stopClip(3);
					    Pacdeath.start();}
				 
	     } else if (index==4){
	    	 if (!eatfruit.isRunning()){					      
				    stopClip(2);
				    stopClip(3);
				    stopClip(4);
				    eatfruit.start();}
	     } else if (index==5){
	    	 
	    	 if (!eatGhost.isRunning()){					      
				    stopClip(2);
				    stopClip(3);
				    stopClip(4);
				    stopClip(5);
				    eatGhost.start();}
	    	 
	     } else if (index==6){
	    	 if (!extrapac.isRunning()){					      
				    stopClip(2);
				    stopClip(3);
				    stopClip(4);
				    stopClip(5);
				    stopClip(6);
				    extrapac.start();
				   }
	    	 
	     }
	}
					      
					   
					// playClip
	
	public void stopClip( int index )
	{
		if (index == 1)
		   {
		   if (PacBeginning.isRunning() )
			   PacBeginning.stop();
		   PacBeginning.setFramePosition(0);
		   }
		else if (index == 2)
		   {
		   if (PacChomp.isRunning() )
			   PacChomp.stop();
		   PacChomp.setFramePosition(0);
		   }
		else if (index == 3)
		   {
		   if (Pacdeath.isRunning() )
			   Pacdeath.stop();
		   Pacdeath.setFramePosition(0);
		   }
		else if (index == 4)
		   {
		   if (eatfruit.isRunning() )
			   eatfruit.stop();
		   eatfruit.setFramePosition(0);
		   }
		else if (index == 5)
		   {
		   if (eatGhost.isRunning() )
			   eatGhost.stop();
		   eatGhost.setFramePosition(0);
		   }
		else if (index == 6)
		   {
		   if (extrapac.isRunning() )
			   extrapac.stop();
		   extrapac.setFramePosition(0);
		   }
		
		
		
	} // stopClip
	
} // MySounds class
