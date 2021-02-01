//package log2810;

public class Ingredient {

//  Constructeur par paramètres
	public Ingredient(String nom, int indice) {
		
		nom_ = nom;
		indice_ = indice;
	}

public String getNom()
{return nom_;}

public int getIndice()
{return indice_;}

	private String nom_;
	private int indice_;
}
