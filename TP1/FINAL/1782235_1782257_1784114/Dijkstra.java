

public class Dijkstra {
	protected int[][] matriceSource;
	protected int[][] matriceDijkstra;
	protected int longueurMatrice;
	
	private Node[] noeudPrecedent;
	private Node[] noeudSuivant;
	
	protected Node noeudDebut;
	protected Node noeudFin;
	
	private Node[] chemins;
	protected Node[] parcours;
	protected int tempsParcours;
	protected int tempsMaxSansRecharge;
	protected int tempsDepuisDerniereRecharge;
	
	
	Dijkstra(Graphe graphe, Node depart, Node fin) 
	{
		//on initialise la matrice Dijkstra avec des -1, sauf pour le noeud de depart a 0
		longueurMatrice = graphe.matrice.length;
		
		matriceSource = new int[longueurMatrice][longueurMatrice];
		matriceSource = graphe.matrice;
		
		matriceDijkstra = new int[longueurMatrice][longueurMatrice];
		for (int i = 0; i < longueurMatrice; i++)
		{
			for (int j = 0; j < longueurMatrice; j++)
			{
				matriceDijkstra[i][j] = -1;
			}
			//le plus court chemin de NoeudDepart a NoeudDepart sera toujours 0
			matriceDijkstra[i][depart.getNumero()] = 0;
		}
		
		
		//on initilise la liste des noeuds precedents vide, et les noeuds suivant avec tous les noeuds
		noeudPrecedent = new Node[0];
		noeudSuivant = new Node[longueurMatrice];
		noeudSuivant = graphe.toutNoeud;
		
		//on recupere notre noeud de debut et darrivee
		noeudDebut = depart;
		noeudFin = fin;
		
		//chemins contient les noeuds predecesseurs de chacun. Appelé recursivement cela nous donne le parcours
		chemins = new Node[longueurMatrice];
		for (int i = 0; i < longueurMatrice; i++ )
		{
			chemins[i] = depart;
		}
	}

	/**
	 * Realise l'algorithme de Dijkstra a partir d'une matrice de distance
	 * Elle modifie les attributs
	 */
	public void realiserDijkstra()
	{
		initialiserDijkstra();
		while(noeudSuivant.length > 1)
		{
			Node prochain = trouverProchainNoeud();
			trouverDistanceMinimale(prochain);
			MiseAJourDonnees(prochain);
		}
		CreerLeParcours();
		MiseAJourTemps();
	}

	/**
	 * Permet de remplir la premiere ligne de la matrice servant a realiser Dijkstra
	 */
	private void initialiserDijkstra()
	{
		//on rempli la matrice dijkstra avec les voisins du noeud initial
		trouverDistanceMinimale(noeudDebut);
		
		MiseAJourDonnees(noeudDebut);
		
	}
	
	/**
	 * On actualise le nouveau temps correspondants au plus court temps pour rejoindre le noeud étudié
	 * @param noeud
	 */
	private void trouverDistanceMinimale(Node noeud) 
	{		
		for (int i = 0; i < longueurMatrice; i++)
		{
			int valeurVoisinSource = matriceSource[noeud.getNumero()][i];
			int valeurDijkstra = matriceDijkstra[noeudPrecedent.length][i];
			int valeurActuelPourNoeud = matriceDijkstra[noeudPrecedent.length][noeud.getNumero()];
			
			//si il existe un chemin direct entre les deux
			if(valeurVoisinSource != 0)
			{
				//si aucune valeur ne lui a deja ete associee
				if (valeurDijkstra == -1)
				{
					//on actualise la matrice de Dijkstra
					matriceDijkstra[noeudPrecedent.length+1][i] = valeurVoisinSource + valeurActuelPourNoeud;
					
					//on actualise le chemin possible
					chemins[i] = noeud;
				}
				//si le nouveau temps est plus petit que l'ancien temps
				else if (valeurVoisinSource + valeurActuelPourNoeud < valeurDijkstra)
				{
					matriceDijkstra[noeudPrecedent.length+1][i] = valeurVoisinSource + valeurActuelPourNoeud;
					chemins[i] = noeud;
				}
			}
		}
	}

	/**
	 * On actualise les temps pour chaque noeud
	 * @param prochain
	 */
	private void MiseAJourDonnees(Node prochain)
	{
		//on ajoute le nouveau noeud dans les precedent
		Node[] tempo =  new Node[noeudPrecedent.length + 1];
		for(int i = 0; i < noeudPrecedent.length; i++)
		{
			tempo[i] = noeudPrecedent[i];
		}
		tempo[tempo.length-1] = prochain;
		noeudPrecedent = new Node[noeudPrecedent.length + 1];
		noeudPrecedent = tempo;
		
		//on supprime le nouveau noeud dans les suivant
		Node[] temp = new Node[noeudSuivant.length - 1];
		int index = 0;
		for(int j = 0; j < noeudSuivant.length; j++)
		{
			if (prochain != noeudSuivant[j])
			{
				temp[index++] = noeudSuivant[j];
			}
		}
		noeudSuivant = new Node[noeudSuivant.length - 1];
		noeudSuivant = temp;
		
		//on recopie les elements qui n'ont pas ete modifie mais qui ont un chemin
		for (int i = 0; i < longueurMatrice; i++)
		{
			if (matriceDijkstra[noeudPrecedent.length][i] == -1)
			{
				if (matriceDijkstra[noeudPrecedent.length-1][i] != -1)
				{
					matriceDijkstra[noeudPrecedent.length][i] = matriceDijkstra[noeudPrecedent.length-1][i];
				}
			}
		}
	}
	
	/**
	 * Trouver le prochain noeud a étudié
	 * @return
	 */
	private Node trouverProchainNoeud()
	{
		//on cherche le noeud qui a une distance minimum avec le noeud de depart
		//De base on prend le premier noeud de la liste
		int minimum = matriceDijkstra[noeudPrecedent.length][noeudSuivant[0].getNumero()];
		Node prochain = new Node();
		//on parcours tous les noeuds restants
		for (int i = 0; i < noeudSuivant.length; i++)
		{		
			int val = matriceDijkstra[noeudPrecedent.length][noeudSuivant[i].getNumero()];
			//si il existe un chemin jusqu a ce noeud
			if (val != -1)
			{
				if (minimum == -1 ||  val < minimum)
				{
					minimum = val;
					prochain = noeudSuivant[i];
				}
			}
		}
		
		return prochain;
	}

	/**
	 * De facon recursive, on créé le parcours.
	 * On commence a la fin, et de proche en proche, on arrive au debut
	 */
	private void CreerLeParcours()
	{
		//Le parcours minimum contient le noeuds de depart et d'arriver seulement
		parcours = new Node[2];
		int nextIndex;
		

		//on appelle de facon recursive chemins (qui contient 1 predecesseur pour chaque noeud)
		
		parcours[0] = noeudFin;
		parcours[1] = chemins[noeudFin.getNumero()];
		nextIndex = parcours[1].getNumero();
		//on arrete la recursivite lorsque on arrive au debut de notre parcours
		while (chemins[nextIndex] != noeudDebut)
		{
			Node[] temp = new Node[parcours.length+1];
			for(int i = 0; i < parcours.length; i++)
			{
				temp[i]=parcours[i];
			}
			parcours = new Node[parcours.length+1];
			parcours=temp;
			
			//on ajoute le noeud predecesseur a notre parcours
			parcours[parcours.length-1] = chemins[nextIndex];
			//recursivite
			nextIndex = parcours[parcours.length-1].getNumero();
		}
		//il nous reste a ajoute le noeud du debut de parcours
		Node[] temp = new Node[parcours.length+1];
		for(int i = 0; i < parcours.length; i++)
		{
			temp[i]=parcours[i];
		}
		parcours = new Node[parcours.length+1];
		parcours=temp;
		
		parcours[parcours.length-1] = noeudDebut;
		
		//Dans le cas du parcours minimum
		if (parcours[parcours.length-1] == parcours[parcours.length-2])
		{
			parcours = new Node[2];
			parcours[0] = noeudFin;
			parcours[1] = noeudDebut;
		}
		
		
	}
	
	/**
	 * Toutes les données de temps sont actualisées
	 */
	private void MiseAJourTemps()
	{
		//on recupere la derniere ligne de Dijkstra qui contient le temps minimal de noeudDepart vers tous les noeuds
		int[] tempsMinimal = matriceDijkstra[longueurMatrice-1];
		
		//si on passe par aucune station de recharge
		tempsParcours = tempsMinimal[noeudFin.getNumero()];
		
		//A la base notre dernier point de recharge est simplement le noeudDebut
		int dernierPointDeRecharge = parcours[parcours.length-1].getNumero();
		
		//on cherche a savoir si il passe par une station de recharge
		tempsMaxSansRecharge = 0;
		for (int i = parcours.length-1; i < 1; i-- )
		//on exclu le noeud de debut et de fin
		{
			if (parcours[i].getRecharge())
			{
				//il sarrerte 20min
				tempsParcours += 20;
				
				if (tempsMaxSansRecharge < (tempsMinimal[i] - tempsMinimal[dernierPointDeRecharge]))
				{
					//on obtient le temps maximal sur batterie sur le trajet
					tempsMaxSansRecharge = tempsMinimal[i] - tempsMinimal[dernierPointDeRecharge];
				}
				dernierPointDeRecharge = parcours[i].getNumero();
				
			}
		}
		//pour le dernier noeud
		if (tempsMaxSansRecharge < (tempsMinimal[noeudFin.getNumero()] - tempsMinimal[dernierPointDeRecharge]))
		{
			tempsMaxSansRecharge = tempsMinimal[noeudFin.getNumero()] - tempsMinimal[dernierPointDeRecharge];
		}
		//si on ne passe par aucune station de recharge
		if (tempsMaxSansRecharge == 0)
		{
			tempsMaxSansRecharge = tempsParcours;
		}
		
		tempsDepuisDerniereRecharge = tempsMinimal[noeudFin.getNumero()] - tempsMinimal[dernierPointDeRecharge];
		
	}

}