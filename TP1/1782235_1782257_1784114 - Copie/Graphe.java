
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graphe
{
    protected int matrice[][];
    protected Node chemin[];
    protected Node[] toutNoeud;
    
    private Node[] parcoursParRecharge;
	private int tempsParcoursParRecharge;
	private int tempsDepuisDerniereRecharge;
    
	
    Graphe()
    {
    	matrice = new int[1][1];
    	toutNoeud = new Node[1];
		toutNoeud[0] = new Node();
    }

    
    /**
     * Permet d'assigner au attributs les valeurs correpondantes apartir du fichier donnee en parametre
     * @param nomFichier
     * @throws IOException
     */
    public void creerGraphe(String nomFichier) throws IOException
    {
        FileReader fichier = new FileReader(System.getProperty("user.dir") + "/" + nomFichier);
        BufferedReader br = new BufferedReader(fichier);

        String line;
        int index = 1;
        //on recupere les informations de recharge des stations
        line = br.readLine();
        while (!line.isEmpty())
        {
        	String[] tab = line.split(",");
        	index = Integer.parseInt(tab[0]);
        	int value = Integer.parseInt(tab[1]);
        	
        	//on alloue de la memoire si le tahbleau est trop petit
        	if (index >= toutNoeud.length)
        		allocate();
        	
        	//on attribue le numero et le nom de chaque noeud
        	toutNoeud[index].setNumero(index);
        	
        	//on affecte les valeur de recharge au stations
        	if (value == 1)
        		toutNoeud[index].setRecharge(true);
        	else
        		toutNoeud[index].setRecharge(false);
        	
        	line = br.readLine();
        }
        //on passe la ligne vide 
        line = br.readLine();
        
        //on creer la matrice a partir de la taille deja connu. on rajoute +1 car on commence a 1
        matrice = new int[index+1][index+1];
        
        //on recupere les informations de la matrice avec des noeuds        
        while (line != null)
        {
        	
        	String[] tab = line.split(",");
        	
        	int row = Integer.parseInt(tab[0]);
        	int col = Integer.parseInt(tab[1]);
        	int value = Integer.parseInt(tab[2]);
        	
        	matrice[row][col] = value;
        	matrice[col][row] = value;
        	
        	line = br.readLine();
        }
        br.close();
    }
    
    
    /**
     * Lit le graphe selon le modèle demandée
     */
    public void lireGraphe() 
    {
    	for (int i = 1; i < matrice.length; i++)
    	{
    		System.out.println("(" + toutNoeud[i].getNom() + ", " + toutNoeud[i].getNumero() + ", (" + afficherVoisins(i) + "))");
    		System.out.println();
    	}
    }
    
    /**
     * Determine le plus court chemin sécuritaire
     * @param colis
     * @param depart
     * @param arrivee
     */
    public void PlusCourtChemin(int colis, Node depart, Node arrivee)
    {
    	Dijkstra dijkstra = new Dijkstra(this, depart, arrivee);
    	dijkstra.realiserDijkstra();
    	PrendreDecision(colis, dijkstra);
    }
    
    /**
     * Affiche un noeud 
     * @param num
     * @return
     */
    private String afficherVoisins(int num)
    {
    	String voisins = "";
    	for (int i = 0; i < matrice.length; i++)
    	{
    		if (matrice[i][num] != 0)
    		{
    			voisins += "(" + toutNoeud[i].getNom() + ", " + matrice[i][num] + "), ";
    		}
    	}
    	return voisins;
    }
    
    /**
     * on utilise des tableaux simple, il faut donce alloue la memoire lorsquon lui ajoute un element
     * (Prendre des vecteurs aurait ete plus pertinants)
     */
    private void allocate(){
    	int newCapacity = toutNoeud.length+1;
    	Node[] temp = new Node[newCapacity];
    	for (int i = 0; i < newCapacity; i++)
    	{
    		if (i < toutNoeud.length)
    			temp[i] = toutNoeud[i];
    		else //on rempli le reste du tableau avec des noeuds par defaut pour eviter les null
    			temp[i] = new Node();
    	}
    	toutNoeud = new Node[newCapacity];
    	toutNoeud = temp;
    	temp = null;
    }
    
    /**
     * Realise le choix du drone qui realisera le parcours
     * @param colis
     * @param dijkstra
     */
    private void PrendreDecision(int colis, Dijkstra dijkstra)
	{
		Drone v3 = new Drone(3,colis);
		Drone v5 = new Drone(5,colis);
		Affichage affichage = new Affichage();
		
		// drone 3.3, trajet direct
		if (v3.getAutonomieMax() > dijkstra.tempsMaxSansRecharge)
		{
			v3.calculBatterie(dijkstra.tempsDepuisDerniereRecharge);
			affichage.afficherPlusCourtChemin(v3, dijkstra.tempsParcours, dijkstra.parcours);
		}
		// drone 3.3, trajet via une station de recharge
		else if (DroneParRecharge(v3, dijkstra.noeudDebut, dijkstra.noeudFin) != -1)
		{
			v3.calculBatterie(tempsDepuisDerniereRecharge);
			affichage.afficherPlusCourtChemin(v3, tempsParcoursParRecharge, parcoursParRecharge);
		}
		// drone 5.0, trajet direct
		else if (v5.getAutonomieMax() > dijkstra.tempsMaxSansRecharge)
		{
			v5.calculBatterie(dijkstra.tempsDepuisDerniereRecharge);
			affichage.afficherPlusCourtChemin(v5, dijkstra.tempsParcours, dijkstra.parcours);
		}
		// drone 5.0, trajet via une station de recharge
		else if (DroneParRecharge(v5, dijkstra.noeudDebut, dijkstra.noeudFin) != -1)
		{
			v5.calculBatterie(tempsDepuisDerniereRecharge);
			affichage.afficherPlusCourtChemin(v5, tempsParcoursParRecharge, parcoursParRecharge);
		}
		// trajet impossible
		else
		{
			affichage.afficherExcuses();
		}
	}
	
    /**
     * Pour prendre une decision, on essaie lors d'un projet impossible de faire :
     * depart --> stationRecharge puis stationRecharge-->destination
     * 
     * Cela pour chacune des stations de recharge
     * @param drone
     * @param depart
     * @param arrivee
     * @return
     */
	private int DroneParRecharge(Drone drone, Node depart, Node arrivee)
	{
		//on regarde si le drone peut effectuer le trajet en passant par une des recharges
		for(int i = 0; i < toutNoeud.length; i++)
		{
			if(toutNoeud[i].getRecharge())
			{
				//on realise dijkstra du point de depart jusqua une recharge
				Dijkstra DepartaRecharge = new Dijkstra(this, depart, toutNoeud[i]);
		    	DepartaRecharge.realiserDijkstra();
		    	
		    	//on realise dijkstra de la recharge jusquau point d'arrivee
		    	Dijkstra RechargeaArrivee = new Dijkstra(this, toutNoeud[i], arrivee);
		    	RechargeaArrivee.realiserDijkstra();
		    	
		    	tempsDepuisDerniereRecharge = RechargeaArrivee.tempsDepuisDerniereRecharge;
		    	tempsParcoursParRecharge = DepartaRecharge.tempsParcours + RechargeaArrivee.tempsParcours + 20;
		    	parcoursParRecharge = new Node[DepartaRecharge.parcours.length + RechargeaArrivee.parcours.length-1];//-1 pour pas compter 2fois le meme noeuds
		    	
		    	//on rempli le tableau qui est dans le sens inverse
		    	for(int k = 0; k < RechargeaArrivee.parcours.length; k++)
		    	{
		    		parcoursParRecharge[k] = RechargeaArrivee.parcours[k];
		    	}
		    	for(int k = 1; k < DepartaRecharge.parcours.length; k++)
		    	{
		    		parcoursParRecharge[k + RechargeaArrivee.parcours.length-1] = DepartaRecharge.parcours[k];
		    	}
		    	
		    	
		    	//on enregistre le nouveau plus grand tempsMaxSansRecharge 
				int tempsMaxSansRecharge = DepartaRecharge.tempsMaxSansRecharge;
				if(DepartaRecharge.tempsMaxSansRecharge < RechargeaArrivee.tempsMaxSansRecharge)
				{
					tempsMaxSansRecharge = RechargeaArrivee.tempsMaxSansRecharge;
				}
				
		    	//on regarde si le drone a assez d'autonomie pour faire le parcours via la recharge
				if (drone.getAutonomieMax() > tempsMaxSansRecharge)
				{
					return toutNoeud[i].getNumero();
				}
			}
		}
		//si on ne trouve pas de chemin que le drone puisse faire, en passant par les recharge
		return -1;
	}
}