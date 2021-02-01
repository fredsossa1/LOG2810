

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Affichage {
	
	/**
	 * Affiche l'execution de la partie Drone
	 * @throws IOException
	 */
	public void afficherInterfaceDrone() throws IOException
	{
		int selection;
		
		//on recupere les entree de l'utilisateur
		Scanner reader = new Scanner(System.in);
		Graphe general = new Graphe();
		
		general.creerGraphe(this.afficherMiseAJour());
		do
		{
			selection = this.afficherPremierMenuDrone();
			if(selection == 1)
			{
				general.creerGraphe(this.afficherMiseAJour());
				general.lireGraphe();
			}
			else if(selection == 2)
			{
				int choix[] = this.afficherDeuxiemeMenuDrone(general);
				general.PlusCourtChemin(choix[0], general.toutNoeud[choix[1]], general.toutNoeud[choix[2]]);
			}
		}
		while(selection != 3);
	}

	/**
	 * Affiche l'execution de la partie Recette
	 * @throws IOException
	 */
	public void afficherInterfaceRecettes() throws IOException
	{
			boolean existe = false;
			String nomFichier = "";
			Scanner scanner = new Scanner(System.in);
			while(!existe)
			{
				nomFichier = "";
				
				System.out.println("\n" + "Entrez le nom du fichier texte sans son extension.");
				System.out.println("Il doit se trouver dans le même dossier que les fichiers java.");
				System.out.print("  Nom du fichier : ");
				nomFichier = scanner.nextLine();
				nomFichier += ".txt";

				File f = new File(System.getProperty("user.dir") + "/" + nomFichier);
				//methode pour tester l'existence
				if ( f.exists() ) {
					existe=true;
				}
				else
				{
					System.out.println("// Votre fichier est introuvable. Veuillez reessayer. //");
				}
			}
			
			
			GrapheOriente grapheOriente = new GrapheOriente();
			grapheOriente.initialiser(nomFichier);
			
			boolean quitter = false; 
			do
			{
				System.out.println("\n" + "--------MENU DÉJEUNER ET RECETTES---------");
				System.out.println("Veuillez choisir une option :");
				System.out.println("  (a) Créer et afficher le graphe des recettes ");
				System.out.println("  (b) Générer et Afficher le diagramme de Hasse ");
				System.out.println("  (c) Quitter ");
				System.out.print("Votre choix :");
				
				Scanner sc = new Scanner(System.in);
				String choix = sc.nextLine();
				
				switch (choix)
				{
				case "a" :
					grapheOriente.creerGrapheOriente();
					break;
				
				case "b" :
					grapheOriente.genererHasse();
					break;
					
				case "c":
					quitter = true;
					break;
				
				default :
					System.out.println("// Choix non valide!! Veuillez chosir une option correcte //");
					quitter = false;
					break;
					
				}
				
			}while(quitter==false);
	}
	
	/**
	 * Dans la section Drones, affiche le menu du choix des options
	 * @return
	 */
	private int afficherPremierMenuDrone()
	{
		boolean quitter = false; 
		do
		{
			System.out.println("\n" + "---------- MENU DRONE ----------");
			System.out.println("Veuillez choisir une option :");
			System.out.println("  (a) Mettre à jour la carte ");
			System.out.println("  (b) Déterminer le plus court chemin sécuritaire ");
			System.out.println("  (c) Quitter ");
			System.out.print("Votre choix : ");
			
			Scanner sc = new Scanner(System.in);
			String choix = sc.nextLine();
			
			switch (choix)
			{
			case "a" :
				System.out.print("\n");
				return 1;
			
			case "b" :
				System.out.print("\n");
				return 2;
				
			case "c":
				quitter = true;
				break;
			
			default :
				System.out.println("Choix non valide!! Veuillez chosir une option correcte ");
				quitter = false;
				break;
				
			}
			
		}while(quitter==false);
		return 3;
	}

	/**
	 * Affiche l'execution de le determination du plus court chemin sécuritaire
	 * @param graphe
	 * @return
	 */
	private int[] afficherDeuxiemeMenuDrone(Graphe graphe)
	{
		int selectionColis;
		int pointDepart;
		int pointArrivee;

		Scanner reader = new Scanner(System.in);

		do
		{
			System.out.println("Selectionner le poids du colis a envoyer :");
			System.out.println("1 - Plume");
			System.out.println("2 - Moyen");
			System.out.println("3 - Lourd");
			selectionColis = Integer.parseInt(reader.nextLine());
			if (selectionColis != 3 && selectionColis != 2 && selectionColis != 1)
				System.out.println("Veuillez selectionner le bon colis");
		}
		while(selectionColis != 3 && selectionColis != 2 && selectionColis != 1);
		
		do
		{
			System.out.println("Selectionner le point de depart :");
			pointDepart = Integer.parseInt(reader.nextLine());
			if(pointDepart <= 0 || pointDepart >= graphe.toutNoeud.length)
				System.out.println("Veuillez entrer un numero de noeuds correspondant a une station existante.");
		}
		while(pointDepart <= 0 || pointDepart >= graphe.toutNoeud.length);
			
		do
		{
			System.out.println("Selectionner le point d'arrivee :");
			pointArrivee = Integer.parseInt(reader.nextLine());
			if(pointArrivee <= 0 || pointArrivee >= graphe.toutNoeud.length)
				System.out.println("Veuillez entrer un numero de noeuds correspondant a une station existante.");
			if(pointArrivee == pointDepart)
				System.out.println("Pas besoin de drone pour ce trajet ! Ressayez avec une nouvelle destination.");
		}
		while(pointArrivee <= 0 || pointArrivee >= graphe.toutNoeud.length || pointArrivee == pointDepart);
		
		int[] entreeUtilisateur = new int[3];
		entreeUtilisateur[0] = selectionColis;
		entreeUtilisateur[1] = pointDepart;
		entreeUtilisateur[2] = pointArrivee;
		
		//on retourne un tableau des choix de l'utilisateur
		return entreeUtilisateur;
	}

	/**
	 * Affiche le parcours optimisé qu'effectuera le colis
	 * @param parcours
	 */
	private void afficherParcours(Node[] parcours)
	{
		System.out.println("Votre colis suivera le chemin suivant : ");
		for (int i = parcours.length-1; i > 0; i--)
		{
			System.out.print(parcours[i].getNumero() + " --> ");
		
		}
		System.out.println(parcours[0].getNumero());
	}

	/**
	 * Affiche les donnees sur le parcours qu'effectuera le colis
	 * @param drone
	 * @param tempsParcours
	 * @param parcours
	 */
	public void afficherPlusCourtChemin(Drone drone, int tempsParcours, Node[] parcours)
	{

		System.out.println("Félicitation ! Votre livraison de " + parcours[parcours.length-1].getNom() + " à ");
		System.out.println(parcours[0].getNom() + " sera effectée.");
		
		if(drone.getVersion() == 3)
			System.out.println("Nous utiliserons un drone de version : 3.3 Amperes ");
		else if (drone.getVersion() == 5)
			System.out.println("Nous utiliserons un drone de version : 5.0 Amperes ");
		
		System.out.println("Le pourcentage prévu de batterie restant, du drone, est de : " + drone.getBatterie() + "%");
		
		this.afficherParcours(parcours);
		
		System.out.println("Le temps de livraison prevu est de : " + tempsParcours + "minutes");
	}
	
	/**
	 * Dans le cas ou il est impossible de realiser le parcours, peu importe le drone
	 */
	public void afficherExcuses()
	{
		System.out.println("Veuillez nous excuser, nous ne pouvons proceder a la demande.");
		System.out.println("Ressayez avec un autre chemin et/ou colis.");
	}

	/**
	 * Affiche l'execution de la mise a jour des donnees
	 * @return
	 */
	public String afficherMiseAJour()
	{
		boolean existe = false;
		String entree = "";
		Scanner reader = new Scanner(System.in);
		do
		{
			entree = "";
			
			System.out.println("\n" + "Entrez le nom du fichier texte sans son extension.");
			System.out.println("Il doit se trouver dans le même dossier que les fichiers java.");
			System.out.print("  Nom du fichier : ");
			entree = reader.nextLine();
			entree += ".txt";

			File f = new File(System.getProperty("user.dir") + "/" + entree);
			//methode pour tester l'existence
			if ( f.exists() ) {
				return entree;
			}
			else
			{
				System.out.println("// Votre fichier est introuvable. Veuillez reessayer. //");
			}
		}
		while(!existe);
		
		return entree;
		
		//return "arrondissements.txt";
	}

}
