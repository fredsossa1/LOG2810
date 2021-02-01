

public class Drone {
	private int autonomieMax;//en minutes
	private int batterie;//en %
	private int version_;
	private int colis_;
	
	Drone(int version, int colis)
	{
		//version 3.3 = 3 ; 
		//version 5.0 = 5
		version_ = version;
		
		//plume = 1
		//moyen = 2
		//lourd = 3
		colis_ = colis;
		
		if (version == 3)
		{
			if(colis == 1)
				autonomieMax = 80; //(0.8*(100/10)*10)
			else if(colis == 2)
				autonomieMax = 40; //(0.8*(100/20)*10)
			else if(colis == 3)
				autonomieMax = 20; //(0.8*(100/40)*10)
		}
		else if (version == 5)
		{
			if(colis == 1)
				autonomieMax = 80; //(0.8*(100/10)*10)
			else if(colis == 2)
				autonomieMax = 53; //(0.8*(100/15)*10) //53.33, precaution 
			else if(colis == 3)
				autonomieMax = 32; //(0.8*(100/25)*10)
		}
	}

	public int getAutonomieMax()
	{
		return autonomieMax;
	}

	public int getVersion()
	{
		return version_;
	}
	
	public int getColis()
	{
		return colis_;
	}

	public int getBatterie()
	{
		return batterie;
	}
	
	/**
	 * A partir du temps depuis le dernier point de recharge on calcul la batterie restante
	 * @param temps
	 */
	public void calculBatterie(int temps)
	//on converti les temps en minutes en pourcentage de batterie
	{
		if (version_ == 3)
		{
			if(colis_ == 1)
				batterie = 100 - (temps*1);
			else if(colis_ == 2)
				batterie = 100 - (temps*2);
			else if(colis_ == 3)
				batterie = 100 - (temps*4); 
		}
		else if (version_ == 5)
		{
			if(colis_ == 1)
				batterie = 100 - (temps*1);
			else if(colis_ == 2)
				batterie = 100 - (int)(temps*1.5);
			else if(colis_ == 3)
				batterie = 100 - (int)(temps*2.5);
		}
	}
}
