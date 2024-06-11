package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import classe.Attribut;
import classe.ClassGenerator;
import connecting.Connexion;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ListeGenerator {
    private static final Vector<String> TEMPLATE_FILE = new Vector<String>();
    private final String CONFIG_FILE;
    private String url;
    private String insertUrl;
    private String deleteUrl;
    private String updateUrl;
    private String methode;
    private String insertMethode;
    private String insertLink;
    private String deleteMethode;
    private String updateMethode;
    private String cssContent;
    private String dossier;
    private String extension;

    private static final Map<String, String> CONFIG_MAP = new HashMap<>();

    static {
        CONFIG_MAP.put("SPRING", "config_file/springliste.xml");
    }

    public ListeGenerator(String techno) throws Exception {
        Path projectRoot = Paths.get(".").toAbsolutePath();
        System.setProperty("user.dir", projectRoot.toString());
        this.CONFIG_FILE = this.getConfigFil(techno);
        TEMPLATE_FILE.add("./ionicTemplate/list.page.txt");
        TEMPLATE_FILE.add("./ionicTemplate/list.page.ts.txt");
        TEMPLATE_FILE.add("./ionicTemplate/list-routing.module.ts.txt");
        TEMPLATE_FILE.add("./ionicTemplate/list.module.ts.txt");
        TEMPLATE_FILE.add("./ionicTemplate/list.page.spec.ts.txt");
        this.setParameter();
    }

    public void setParameter() throws Exception {
        this.url = this.getValue("util", "url");
        this.insertUrl = this.getValue("util", "insertUrl");
        this.updateUrl = this.getValue("util", "updateUrl");
        this.deleteUrl = this.getValue("util", "deleteUrl");
        this.methode = this.getValue("methods", "List");
        this.insertMethode = this.getValue("methods", "Insert");
        this.updateMethode = this.getValue("methods", "Update");
        this.deleteMethode = this.getValue("methods", "Delete");
        this.extension = this.getValue("util", "extension");
        this.insertLink = this.getValue("util", "insertLink");
        this.dossier = this.getValue("util", "dossier");
        this.cssContent = this.getOneValue("cssContent");
    }

    public String getConfigFil(String techno) {
        String csType = CONFIG_MAP.get(techno.toUpperCase());
        return (csType != null) ? csType : "";
    }


    public void generate(String className) throws Exception {
        try {
            String base = "postgres";
            Connection con = Connexion.getConnection(base);

            Vector<String> vtemplate = loadTemplateFromFile();

            String template = vtemplate.get(0);
            Attribut[] attributs = ClassGenerator.getColonnes(className, con);

            template = template.replace("#ATTRIBUT1#", attributs[0].getNom());
            template = template.replace("#CLASS_NAME_2#", capitalizeFirstLetter(className.toLowerCase()));

            String back = vtemplate.get(1);
            String routing = vtemplate.get(2);
            String module = vtemplate.get(3);
            String spec = vtemplate.get(4);

            back = back.replace("#URL#", url);
            back = back.replace("#CLASS_NAME_2#", capitalizeFirstLetter(className.toLowerCase()));

            dossier = dossier.replace("#CLASS_NAME_2#", capitalizeFirstLetter(className.toLowerCase()));
            File folder = new File(dossier);
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier + "/list.page.html"))) {
                        writer.write(template);
                    } catch (Exception e) {
                        throw e;
                    }
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier + "/list.page.ts"))) {
                        writer.write(back);
                    } catch (Exception e) {
                        throw e;
                    }
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier + "/list.module.ts"))) {
                        writer.write(module);
                    } catch (Exception e) {
                        throw e;
                    }
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier + "/list.page.scss"))) {
                        writer.write(cssContent);
                    } catch (Exception e) {
                        throw e;
                    }
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier + "/list.page.spec.ts"))) {
                        writer.write(spec);
                    } catch (Exception e) {
                        throw e;
                    }
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier + "/list-routing.module.ts"))) {
                        writer.write(routing);
                    } catch (Exception e) {
                        throw e;
                    }

                } else {
                    throw new Exception("Error while creating folder");
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Vector<String> loadTemplateFromFile() throws Exception {
        Vector<String> res = new Vector<String>();

        for (int i = 0;i< TEMPLATE_FILE.size();i++){
            try (BufferedReader reader = new BufferedReader(new FileReader(TEMPLATE_FILE.get(i)))) {
                StringBuilder template = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    template.append(line).append("\n");
                }
                res.add(template.toString());
            }
        }


        return res;
    }


    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    private boolean checkClasse(String className) {
        boolean result = false;
        String filename = className + extension;
        Path filePath = Paths.get("./dossier", filename);
        if (Files.exists(filePath)) {
            result = true;
        }
        return result;
    }

    public String getValue(String parent, String element) throws Exception {
        String type = "";
        Path projectRoot = Paths.get(".").toAbsolutePath();
        System.setProperty("user.dir", projectRoot.toString());

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = (Document) builder.parse(CONFIG_FILE);

            NodeList databaseRefList = ((org.w3c.dom.Document) document).getElementsByTagName(parent);
            if (databaseRefList != null) {
                Element databaseRef = (Element) databaseRefList.item(0);
                if (databaseRef != null) {
                    Node elmt = databaseRef.getElementsByTagName(element).item(0);
                    if (elmt != null) {
                        type = elmt.getTextContent();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    public String getOneValue(String parent) throws Exception {
        String type = "";
        Path projectRoot = Paths.get(".").toAbsolutePath();
        System.setProperty("user.dir", projectRoot.toString());

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = (Document) builder.parse(CONFIG_FILE);

            Element element = (Element) document.getElementsByTagName(parent).item(0);
            if (element != null) {
                type = element.getTextContent();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

}
