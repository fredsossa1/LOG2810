
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;




public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		afficherMenuRecettes();
		
	}
	
	public static List<Integer> trouverNoeudsPrecedents(int indiceNoeud, int [][] matrice)
	{
		List<Integer> listeNoeudsSuivants = new LinkedList<Integer>();
		for(int i = 0; i < matrice[indiceNoeud].length; i++)
		{
			//Faire une liste de tous les noeuds suivants potentiels
			// et les retourner dans un tableau
			if(matrice[indiceNoeud][i] == 3)
				listeNoeudsSuivants.add(i);
			
		}
		
		return  listeNoeudsSuivants;
	}

	public static void afficherMenuRecettes() throws IOException
	{
		try
		{
		System.out.println("Veuillez entrer le nom du Fichier :");
			Scanner scanner = new Scanner(System.in);
			String nomFichier = scanner.nextLine();
			
			GrapheOriente grapheOriente = new GrapheOriente();
			grapheOriente.initialiser(nomFichier);
			
			boolean quitter = false; 
			do
			{
				System.out.println("--------MENU DÉJEUNER ET RECETTES---------");
				System.out.println("(a) Créer et afficher le graphe des recettes ");
				System.out.println("(b) Générer et Afficher le diagramme de Hasse ");
				System.out.println("(c) Quitter ");
				System.out.println("Veuillez choisir une option :");
				Scanner sc = new Scanner(System.in);
				String choix = sc.nextLine();
				
				switch (choix)
				{
				case "a" :
					grapheOriente.creerGrapheOriente();
					System.out.print("\n");
					break;
				
				case "b" :
					grapheOriente.printHasse();
					System.out.print("\n");
					break;
					
				case "c":
					quitter = true;
					break;
				
				default :
					System.out.println("Choix non valide!! Veuillez chosir une option correcte ");
					quitter = false;
					break;
					
				}
				
			}while(quitter==false);
		}
		catch(IOException e)
		{
			System.out.println("Fichier introuvable!! Veuillez réessayer.");
		}
		
	}
}
	
