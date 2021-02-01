

public class Arbre {
	Etat racine_ = new Etat("");
	
	void AjouterCodePostal(String codePostal)
	{
		
		//on parcours chacune des lettres du code postal
		for(int i = 0; i < codePostal.length(); i++)
		{
			char lettre = codePostal.charAt(i);
			
			//on regarde si la lettre a deja une sortie associée
			boolean existe = false;
			if(racine_.getSorties() != null)
			{
				for(int j = 0; j < racine_.getSorties().length(); j++)
				{
					char sortie = racine_.getSorties().charAt(j);
					if (lettre == sortie)
					{
						existe = true;
					}
				}
			}
			
			//si il n y a pas de sortie associe, on ajoute un noeud a l arbre
			if (!existe)
			{
				racine_.ajouterEnfant(racine_.getNom(), lettre);
			}
			racine_ = racine_.getEnfant(racine_.getNom() + lettre);
		}
		//on remonte dans l'arbre pour que racine_ correspondent a epsilon
		while(!(racine_.estRacine()))
		{
			racine_ = racine_.getParent();
		}
	}
	
	void afficherH()
	{
		System.out.println(racine_.getEnfant("H").getSorties());
	}
}
