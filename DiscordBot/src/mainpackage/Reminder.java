package mainpackage;

public class Reminder implements Runnable
{
	public Reminder() 
	{
		start();
	}
	
	public void start() {
		Thread t=new Thread(this);
		t.start();
	}

	@Override
	public void run() 
	{
		while(true)
		{
			
		}
		
	}
}
