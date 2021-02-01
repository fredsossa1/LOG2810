/*********************************************************************************
 * Code dans le cadre du TP1 du cours LOG2810
 * 
 * @author 1782235 - Alexandre Thimonier
 * @author 1784114 - Freddy Sossa
 * @author 1782257 - Timothée Laborde
 * 
 * @date 2017-10-24
 ***********************************************************************************/
//package log2810;

import java.io.IOException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) throws IOException {	
		
		System.out.println(System.getProperty("user.dir"));
		
		System.out.println("*******************************");
		System.out.print("********** BIENVENUE **********");
		Affichage affichage = new Affichage();
		
		boolean quitter = false;
		Scanner scanner = new Scanner(System.in);
		do
		{

			System.out.println("\n" + "*******************************");
			System.out.println("-------- MENU PRINCIPAL --------");
			System.out.println("A quoi désirez-vous accéder ?");
			System.out.println("  (a) Drones ");
			System.out.println("  (b) Recettes ");
			System.out.println("  (c) Quitter ");
			System.out.print("Votre choix : ");
			Scanner sc = new Scanner(System.in);
			String choix = sc.nextLine();
			
			switch (choix)
			{
			case "a" :
				affichage.afficherInterfaceDrone();
				break;
			
			case "b" :
				affichage.afficherInterfaceRecettes();
				break;
				
			case "c":
				quitter = true;
				
				System.out.println("*******************************");
				System.out.println("****** FIN DU PROGRAMME *******");
				System.out.println("*******************************");
				break;
			
			default :
				System.out.println("Choix non valide! Veuillez chosir une option correcte ");
				quitter = false;
				break;
				
			}
			
		}while(!quitter);
		
		scanner.close();
	}

}
