

import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class GrapheOriente {
	 private Ingredient [] ingredients_;
	 private  int [][] matrice_;
	 private int nbChemins_=0;
	 private List<String> listeChemins_ = new LinkedList<String>();

	/**
	 * Cette m�thode initialise les attributs de la classe � partir 
	 * des donn�es lues � partir du fichier fourni.
	 * @param nomFichier
	 * @throws IOException 
	 */
	public void initialiser(String nomFichier) throws IOException
	{
		
		ingredients_  = new Ingredient [1] ;		
		
		FileInputStream ips = new FileInputStream(System.getProperty("user.dir") + "/" + nomFichier);
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		String ligne = br.readLine();
		
		for(int i =1; !(ligne.isEmpty()) ;i++ )
		{
			String nom =ligne.substring(ligne.indexOf(',')+1) ;
			
			//allocation de memoire
			Ingredient[] temp = new Ingredient[ingredients_.length+1];
			for(int k = 1; k < ingredients_.length; k++)
			{
				temp[k] = ingredients_[k];
			}
			ingredients_ = new Ingredient[temp.length];
			ingredients_ = temp;
			
			ingredients_[i] = new Ingredient (nom,(i));
			ligne=br.readLine();		
		}
		//List<Ingredient> niveauZero = new LinkedList();

		
		matrice_ = new int [ingredients_.length][ingredients_.length];
		for(int j =1;((ligne=br.readLine())!=null);j++ )
		{
			String [] tab= ligne.split(",");
			int i1 = Integer.parseInt(tab[0]);
			int i2 = Integer.parseInt(tab[1]);
			
			matrice_[i1][i2] = 1;
			if(i1!=i2)
				matrice_[i2][i1] = -1;
			
		}
		
			
		
		
		//return ingredients;
	}

	/**
	 * Cette m�thode permet d'afficher le graphe orient� correspondant
	 * aux donn�es de la matrice lue � partir du fichier.
	 */
	public void creerGrapheOriente()
	{
		/**
		 * Cette m�thode permet de creer le graphe Oriente des Recettes
		 */
		System.out.print("\n");
		for(int i = 1; i < ingredients_.length; i++)
		{
			System.out.print("(" + ingredients_[i].getNom() + "," + ingredients_[i].getIndice() + ",(" );
			
			/*Si il existe un arc orient� entre cet ingredient et un autre 
			 et que j < � la taille de la ligne correspondante � cet ingredient dans la matrice*/
			
			for(int j =0; j <matrice_.length;j++)
				
			{
				if (matrice_[ingredients_[i].getIndice()][j] == 1)
				{
					System.out.print("(" + ingredients_[j].getNom() + "," + ingredients_[j].getIndice() + ")");
				}
			}
			
			System.out.print("))\n");
		}
	}
	
	/**
	 * Cette m�thode permet de trouver tous les minimaux du graphe � partir de la matrice des noeuds.
	 * @return List<Integer>
	 */
	public  List<Integer> trouverElementsNiveauZero()
	{
		List<Integer> liste = new LinkedList<Integer>();
		
		for(int i = 1; i < matrice_.length; i++)
		{
			Integer nbMoinsUn =0;
			for(int j = 1; j < matrice_.length;j++)
			{
				
				if(matrice_[i][j] == -1)
				{
					nbMoinsUn++;
					
				}
					
			}
			if(nbMoinsUn ==0)
				liste.add(i);
		}
		return liste;
	}

	/**
	 * Cette m�thode permet de trouver tous les noeuds suivants auquels
	 * le noued pass� en param�tre est li�
	 * @param indiceNoeud
	 * @return
	 */
	public  List<Integer> trouverNoeudsSuivants(int indiceNoeud)
	{

		List<Integer> listeNoeudsSuivants = new LinkedList<Integer>();
		for(int i = 0; i < matrice_[indiceNoeud].length; i++)
		{
			//Faire une liste de tous les noeuds suivants potentiels
			// et les retourner dans un tableau
			
			// On ne tient pas compte de la r�flexivit� des arcs
			if(matrice_[indiceNoeud][i] == 1 && indiceNoeud != i)
				listeNoeudsSuivants.add(i);
			
		}
		
		return  listeNoeudsSuivants;
	}
	
	
	/**
	 * Cette m�thode r�cursive permet d'afficher tous les chemins possibles du diagramme de Hasse
	 * On part des �l�ments du plus bas niveau, on trouve leur suivants et on fait l'affichage 
	 * au fur et � mesure jusqu'� parcourir tous les chemins.
	 */
	
	public void genererHasse()
	{
		System.out.print("\n");
		List<Integer> niveauZero = this.trouverElementsNiveauZero();
		int compteur = 1;
		for(int i = 0; i < niveauZero.size(); i++)
		{
			
			this.printHasse(niveauZero.get(i));
		}
		
		// Affichage au format recommand� du Diagramme de Hasse
		System.out.print("Liste "+ (compteur) +": ");
		compteur++;
		for(int i = 0; i< listeChemins_.size(); i++)
		{
			System.out.print(listeChemins_.get(i));
			if(listeChemins_.get(i) == "\n" && compteur <nbChemins_)
			{
				System.out.print("Liste "+ (compteur) +": ");
				compteur++;
			}
			
		}
		this.reinitialiser();
	}
	

	/**
	 * Fonction r�cursive qui affiche tous les chemins possibles du diagramme de Hasse
	 * en partant du noeud pass� en param�tre.
	 * @param indiceNoeud
	 */
	private void printHasse(int indiceNoeud)
	{
		int i =0, j=0;
		if(nbChemins_==0)
		{
			nbChemins_++;
		}
			
		
		List<Integer> temp = this.trouverNoeudsSuivants(indiceNoeud);

		
		if(temp.size() !=0 )
		{
			
			for( i = 0; i <temp.size(); i++)
			{
				
				
				listeChemins_.add(ingredients_[indiceNoeud].getNom());
				
				listeChemins_.add(" --> ");
				List<Integer> tempTab = this.trouverNoeudsSuivants(temp.get(i));
				
				if(tempTab.size()!=0)
				{
					for( j = 0; j < tempTab.size(); j++)
					{
						if(j>=1)
						{
							
							listeChemins_.add(ingredients_[indiceNoeud].getNom());
							listeChemins_.add(" --> ");
						}
						
						
						
						listeChemins_.add(ingredients_[temp.get(i)].getNom());
						listeChemins_.add(" --> ");
						
						printHasse(tempTab.get(j));
					}
				}
				else
				{
		
					listeChemins_.add(ingredients_[temp.get(i)].getNom());
					
					listeChemins_.add("\n");
					nbChemins_++;
	
				}
			}
		}
		
		else
		{
			listeChemins_.add(ingredients_[indiceNoeud].getNom());
			listeChemins_.add("\n");
			nbChemins_++;
		}
		
	}
	/**
	 * Cette m�thode remet les donn�es � z�ro apr�s une utilisation
	 */
	private void reinitialiser()
	{
		nbChemins_=0;
		listeChemins_= new LinkedList<String>();
	}
	
	public Ingredient[] getIngredients()
	{return ingredients_;}

	public int[][] getMatrice()
	{return matrice_;}
	
}
