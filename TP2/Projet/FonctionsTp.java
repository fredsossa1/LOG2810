import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FonctionsTp {
	void CreerArbreAdresses() throws IOException
	{
		Arbre arbre = new Arbre();
		
		FileReader fichier = new FileReader("X:/Bureau/TP2_A2017_LOG2810/CodesPostaux.txt");
        BufferedReader br = new BufferedReader(fichier);

        String line;
        //on recupere les informations de recharge des stations
        line = br.readLine();
        while (line != null)
        {
        	arbre.AjouterCodePostal(line);
        	line = br.readLine();
        }
        br.close();
        arbre.afficherH();
	}
}
