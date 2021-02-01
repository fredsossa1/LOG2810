//package log2810;

public class Node {
	private boolean recharge_;
	private int numero_;
	private String nom_;
	
	Node()
	{
		recharge_ = false;
		numero_ = 0;
		nom_ = "";
	}

	Node(boolean recharge, int num)
	{
		recharge_ = recharge;
		numero_ = num;
		nom_ = trouverNom(num);
	}
	
	public String getNom()
	{
		return nom_;
	}
	
	public int getNumero()
	{
		return numero_;
	}
	
	public boolean getRecharge()
	{
		return recharge_;
	}
	
	public void setRecharge(boolean rech)
	{
		recharge_ = rech;
	}
	
	/**
	 * Permet d'associer le nom des stations a leur numero respectif
	 * @param num
	 * @return
	 */
	public String trouverNom(int num)
	{
		//lorsque on associe un numero, on lui donne son nom associe
		switch(num)
		{
			case 1:
				return "Ahuntsic-Cartierville";
			case 2:
				return "Anjou";
			case 3:
				return "Côte-des-Neiges–Notre-Dame-de-Grâce";
			case 4:
				return "Lachine";
			case 5:
				return "LaSalle";
			case 6:
				return "Le Plateau-Mont-Royal";
			case 7:
				return "Le Sud-Ouest";
			case 8:
				return "L’Île-Bizard–Sainte-Geneviève";
			case 9:
				return "Mercier–Hochelaga-Maisonneuve";
			case 10:
				return "Montréal-Nord";
			case 11:
				return "Outremont";
			case 12:
				return "Pierrefonds-Roxboro";
			case 13:
				return "Rivière-des-Prairies–Pointe-aux-Trembles";
			case 14:
				return "Rosemont–La Petite-Patrie";
			case 15:
				return "Saint-Laurent";
			case 16:
				return "Saint-Léonard";
			case 17:
				return "Verdun";
			case 18:
				return "Ville-Marie";
			case 19:
				return "Villeray–Saint-Michel–Parc-Extension";
		}
		return "";
	}
	
	public void setNumero(int num)
	{
		numero_ = num;
		nom_ = trouverNom(num);
	}
}
