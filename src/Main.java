import java.util.Scanner;

import classe.ClassGenerator;
import controller.ControllerGenerator;
import repository.RepositoryGenerator;
import view.ListeGenerator;
import view.UpdateGenerator;
import view.FormGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String technologie = "spring";

            System.out.println("Entrez le nom de la classe à générer : ");
            String nomClasse = scanner.nextLine();

            new ClassGenerator(technologie);
//            new ListeGenerator(technologie).generate(nomClasse);
            new FormGenerator(technologie).genereteIonicAngular(nomClasse);
            new UpdateGenerator(technologie).genereteIonicAngular(nomClasse);
            System.out.println("Génération terminée");
            System.out.println("Voulez-vous continuer ? (O/N) [par défaut O]");
            String continuer = scanner.nextLine();
            if (continuer.equalsIgnoreCase("N")) {
                break;
            } else {
                continue;
            }
        }
    }
}