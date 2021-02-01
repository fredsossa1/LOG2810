import java.util.ArrayList;
import java.util.List;

public class Etat {
    private List<Etat> enfants_ = new ArrayList<Etat>();
    private Etat parent_ = null;
    private String nom_ = "";
    private String sorties_ = "";

    public Etat() {
        nom_ = "";
        sorties_ = "";
    }

    
    public Etat(String nom) {
        nom_ = nom;
    }

    public Etat(String nom, Etat parent_) {
    	nom_ = nom;
        this.parent_ = parent_;
    }

    public List<Etat> getEnfants() {
        return enfants_;
    }

    public void setParent(Etat parent_) {
        this.parent_ = parent_;
    }
    
    public Etat getParent()
    {
    	return parent_;
    }

    public void ajouterEnfant(String nom, char lettre) {
    	Etat enfant = new Etat(nom + lettre);
        enfant.setParent(this);
        this.enfants_.add(enfant);
        sorties_ += lettre;
    }

    public void ajouterEnfant(Etat enfant) {
    	enfant.setParent(this);
        this.enfants_.add(enfant);
    }

    public String getNom() {
        return nom_;
    }
    
    public String getSorties()
    {
    	return sorties_;
    }
    
    public void addSorties(char lettre)
    {
    	sorties_ += lettre; 
    }

    public boolean estRacine() {
        return (this.parent_ == null);
    }

    public boolean estFeuille() {
        if(this.enfants_.size() == 0) 
            return true;
        else 
            return false;
    }

    public void supprimerparent_() {
        this.parent_ = null;
    }
    
    public Etat getEnfant(String nom)
    {
    	for(int i = 0; i < this.getEnfants().size(); i++)
    	{
    		if (enfants_.get(i).getNom().equals(nom))
    		{
    			return enfants_.get(i);
    		}
    	}
    	return null;
    }
}