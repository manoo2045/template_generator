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

public class UpdateGenerator {
//    private static final String TEMPLATE_FILE = "./ReactUpdateTemplate.txt";
    private static final Vector<String>TEMPLATE_FILE = new Vector<String>();
    private final String CONFIG_FILE;
    private String url;
    private String updateUrl;
    private String methode;
    private String updateMethode;
    private String listLink;
    private String cssContent;
    private String dossier;
    private String extension;
    private String forms;
    private String formstate;

    private static final Map<String, String> CONFIG_MAP = new HashMap<>();

    static {
        CONFIG_MAP.put("SPRING", "config_file/springupdate.xml");
    }

    public UpdateGenerator(String techno) throws Exception {
        this.CONFIG_FILE = this.getConfigFil(techno);
        TEMPLATE_FILE.add("./laravelTemplete/Update.txt");
//        TEMPLATE_FILE.add("./ionicTemplate/edit.page.ts.txt");
//        TEMPLATE_FILE.add("./ionicTemplate/edit-routing.module.ts.txt");
//        TEMPLATE_FILE.add("./ionicTemplate/edit.module.ts.txt");
//        TEMPLATE_FILE.add("./ionicTemplate/edit.page.spec.ts.txt");
        this.setParameter();
    }

    public void setParameter() throws Exception {
        this.url = this.getValue("util", "url");
        this.updateUrl = this.getValue("util", "updateUrl");
        this.methode = this.getValue("methods", "List");
        this.updateMethode = this.getValue("methods", "Update");
        this.extension = this.getValue("util", "extension");
        this.dossier = this.getValue("util", "dossier");
        this.listLink = this.getValue("util", "listLink");
        this.cssContent = this.getOneValue("cssContent");
    }

    public String getConfigFil(String techno) {
        String csType = CONFIG_MAP.get(techno.toUpperCase());
        return (csType != null) ? csType : "";
    }
    public String toJson(Attribut[] attributs){
        String json = "item = {\n";
        for (int i = 0; i < attributs.length; i++) {
            json += "\t\t" + attributs[i].getNom() +" : null,\n";
        }
        return json + "\t}";
    }

    public static String toFormData(Attribut[] attributs, Attribut[] attributsFk,Attribut att){
        String formData = "let formData = new FormData(); \n";
        formData +=  "formData.append('"+att.getNom()+"',this."+att.getNom()+" );\n";
        for (int i = 0; i < attributs.length; i++) {
            formData +=  "\tformData.append('"+attributs[i].getNom()+"',this."+attributs[i].getNom()+" );\n";
        }
        for (int i = 0; i < attributsFk.length; i++) {
            if (attributsFk[i].getNom() != null) {
                formData +=  "\tformData.append('"+attributsFk[i].getNom()+"',this."+attributsFk[i].getNom()+" );\n";
            }
        }

        return formData;
    }


    public String getAttributs(Attribut[] attributs, Attribut[] attributsFk, Attribut atr){
        String att ="";
        att += atr.getNom()+":any = null;\n";
        for (int i = 0; i < attributs.length; i++) {
            att += "\t"+attributs[i].getNom()+":any = null;\n";
        }
        for (int i = 0; i < attributsFk.length; i++) {
            if (attributsFk[i].getNom() != null) {
                att += "\t"+attributsFk[i].getNom()+":any = null;\n";
            }
        }
        return att;
    }

    public String getAllValueToFormsIonic(String tableName, Connection connection) throws Exception {
        String allForms = "";
        Path projectRoot = Paths.get(".").toAbsolutePath();
        System.setProperty("user.dir", projectRoot.toString());
        Attribut[] attributs = ClassGenerator.getColonnes(tableName, connection);
        Attribut[] attributsFK = ClassGenerator.getAllFK(tableName, connection);
        Attribut att = ClassGenerator.getPrimaryKeyColumn(tableName, connection);

        try {
            for (int i = 0; i < attributs.length; i++) {
                allForms += "\t\t<div class=\"mb-3\">\n";
                allForms += "\t\t\t<label for=\"exampleInput_"+attributs[i].getNom()+"\" class=\"form-label\">"+attributs[i].getNom()+"</label>\n";
                allForms += "\t\t\t<input name=\""+attributs[i].getNom()+"\" value=\"{{ $#CLASS_NAME#->"+attributs[i].getNom()+" }}\" type=\""+this.getValue("type",attributs[i].getType())+"\" class=\"form-control @error(\""+attributs[i].getNom()+"\") is-invalid @enderror\" id=\"exampleInput_"+attributs[i].getNom()+"\" aria-describedby=\"emailHelp\">\n";
                allForms += "\t\t\t@error(\""+attributs[i].getNom()+"\")\n";
                allForms += "\t\t\t\t<div class=\"invalid-feedback\">\n";
                allForms += "\t\t\t\t\t{{ $message }}\n";
                allForms += "\t\t\t\t</div>\n";
                allForms += "\t\t\t@enderror\n";
                allForms += "\t\t</div>\n\n";
            }
            for (int i = 0; i < attributsFK.length; i++) {
                if (attributsFK[i].getNom() != null) {
                    Attribut attribut = ClassGenerator.getFirstStringColumnAfterId(attributsFK[i].getType(),
                            connection);
                    Attribut attributPK = ClassGenerator.getPrimaryKeyColumn(attributsFK[i].getType(), connection);
                    allForms += "\t\t<div class=\"mb-3\">\n";
                    allForms += "\t\t\t<label for=\"exampleInput_"+attributsFK[i].getNom()+" class=\"form-label\">"+attribut.getNom()+"</label>\n";
                    allForms += "\t\t\t<select name=\""+attributsFK[i].getNom()+"\" class=\"select2 form-control custom-select @error('"+attributsFK[i].getNom()+"') is-invalid @enderror\" style=\"width: 100%; height:36px;\" id=\"exampleInput_"+attributsFK[i].getNom()+"\" aria-describedby=\"#CLASS_NAME#lHelp\">\n";
                    allForms += "\t\t\t@foreach($"+attributPK.getNom()+"s as $"+attributPK.getNom()+")\n";
                    allForms += "\t\t\t\t <option value=\"{{ $"+attribut.getNom()+"->"+attributPK.getNom()+" }}\" @if($"+attributsFK[i].getNom()+"->"+attributPK.getNom()+" === $#CLASS_NAME#->"+attribut.getNom()+") selected @endif >{{ $"+attributPK.getNom()+"->nom }}</option> \n";
                    allForms += "\t\t\t@endforeach\n";
                    allForms += "\t\t\t</select>\n";
                    allForms += "\t\t</div>\n";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allForms;
    }

    public void genereteIonicAngular(String className) throws Exception {
        try {
            String base = "postgres";
            Connection con = Connexion.getConnection(base);

            className = UpdateGenerator.capitalizeFirstLetter(className);
            Vector<String> vtemplate = loadTemplateFromFile();
            Attribut[] attributs = ClassGenerator.getColonnes(className.toLowerCase(), con);
            Attribut[] attributsFK = ClassGenerator.getAllFK(className.toLowerCase(), con);
            Attribut att = ClassGenerator.getPrimaryKeyColumn(className.toLowerCase(), con);
            this.forms = this.getAllValueToFormsIonic(className.toLowerCase(), con);
            this.formstate = this.getAllValueToFormsState(className.toLowerCase(), con);

            String template = vtemplate.get(0);
            template = template.replace("#FORMS#", forms);
            template = template.replace("#CLASS_NAME_2#", capitalizeFirstLetter(className.toLowerCase()));
            template = template.replace("#CLASS_NAME#", className.toLowerCase());
//            String declarFn = FormGenerator.getAllDeclarFonctAttribut(className.toLowerCase(),con);
//            String allFn = FormGenerator.getAllFonctAttribut(className.toLowerCase(),con);

//            String back = vtemplate.get(1);
//
//            back = back.replace("##FORMS_STATE##",formstate);
//            back = back.replace("##FUNCTION##",allFn);
//            back = back.replace("##DECLAR_FUNCTION##",declarFn);
//            back = back.replace("#URL#", url);
//            back = back.replace("#CLASS_NAME_2#", capitalizeFirstLetter(className.toLowerCase()));
//            back = back.replace("##JSON_ITEM##",FormGenerator.toJson(attributs,attributsFK));
//            back = back.replace("##ATTRIBUTS##",getAttributs(attributs,attributsFK,att));
//            back = back.replace("##FORMDATA##",toFormData(attributs,attributsFK,att));
//            back = back.replace("##PRIMARYKEY##",att.getNom());
//
//            String routing = vtemplate.get(2);
//            String module = vtemplate.get(3);
//            String spec = vtemplate.get(4);
            dossier = dossier.replace("#CLASS_NAME_2#",capitalizeFirstLetter(className.toLowerCase()));
            File folder = new File(dossier);

            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(dossier +"/update_"+className.toLowerCase()+".blade.php"))) {
                writer.write(template);
            } catch (Exception e) {
                throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public void generate(String className) throws Exception {
//        try {
//            String base = "postgres";
//            Connection con = Connexion.getConnection(base);
//
//            className = UpdateGenerator.capitalizeFirstLetter(className);
//            String template = loadTemplateFromFile();
//            this.forms = this.getAllValueToForms(className.toLowerCase(), con);
//            this.formstate = this.getAllValueToFormsState(className.toLowerCase(), con);
//
//            template = template.replace("#URL#", url);
//            template = template.replace("#UPDATE_URL#", updateUrl);
//            template = template.replace("#UPDATE_METHOD#", updateMethode);
//            template = template.replace("#LIST_LINK#", listLink);
//            template = template.replace("#METHOD_LIST#", methode);
//            template = template.replace("#CLASS_NAME_2#", className.toLowerCase());
//            template = template.replace("#FORMS#", forms);
//            template = template.replace("#FORMS_STATE#", formstate);
//
//            File folder = new File(dossier);
//            if (!folder.exists()) {
//                if (folder.mkdirs()) {
//                    try (BufferedWriter writer = new BufferedWriter(
//                            new FileWriter(dossier + "/Update" + extension))) {
//                        writer.write(template);
//                    } catch (Exception e) {
//                        throw e;
//                    }
//                    try (BufferedWriter writer = new BufferedWriter(
//                            new FileWriter(dossier + "/Update.css"))) {
//                        writer.write(cssContent);
//                    } catch (Exception e) {
//                        throw e;
//                    }
//                } else {
//                    throw new Exception("Error while creating folder");
//                }
//            } else {
//                try (BufferedWriter writer = new BufferedWriter(
//                        new FileWriter(dossier + "/Update" + extension))) {
//                    writer.write(template);
//                } catch (Exception e) {
//                    throw e;
//                }
//                try (BufferedWriter writer = new BufferedWriter(
//                        new FileWriter(dossier + "/Update.css"))) {
//                    writer.write(cssContent);
//                } catch (Exception e) {
//                    throw e;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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

    public String getAllValueToForms(String tableName, Connection connection) throws Exception {
        String allForms = "";
        Path projectRoot = Paths.get(".").toAbsolutePath();
        System.setProperty("user.dir", projectRoot.toString());
        Attribut[] attributs = ClassGenerator.getColonnes(tableName, connection);
        Attribut[] attributsFK = ClassGenerator.getAllFK(tableName, connection);
        try {
            for (int i = 0; i < attributs.length; i++) {
                allForms += "\t<div className=\"form-group\">\n";
                allForms += "\t\t<label for=\"" + attributs[i].getNom() + "\">" + attributs[i].getNom() + "</label>\n";
                allForms += "\t\t<input type=\"" + this.getValue("type", attributs[i].getType())
                        + "\" className=\"form-control\" id=\"" + attributs[i].getNom() + "\" name=\""
                        + attributs[i].getNom() + "\" placeholder=\"" + attributs[i].getNom() + "\" />\n";
                allForms += "\t</div>\n";
            }
            for (int i = 0; i < attributsFK.length; i++) {
                if (attributsFK[i].getNom() != null) {
                    Attribut[] attributsF = ClassGenerator.getColonnes(attributsFK[i].getType(), connection);
                    Attribut attribut = ClassGenerator.getFirstStringColumnAfterId(attributsFK[i].getType(),
                            connection);
                    Attribut attributPK = ClassGenerator.getPrimaryKeyColumn(attributsFK[i].getType(), connection);
                    allForms += "\t<div className=\"form-group\">\n";
                    allForms += "\t\t<label for=\"" + attributsFK[i].getNom() + "\">" + attributsFK[i].getNom()
                            + "</label>\n";
                    allForms += "\t\t<select className=\"form-control\" id=\"" + attributsFK[i].getNom() + "\" name=\""
                            + attributsFK[i].getNom() + "\">\n";
                    allForms += "\t\t{" + attributsFK[i].getType() + ".map((item) => (\n";
                    allForms += "\t\t\t<option value={item." + attributPK.getNom() + "}>" + "{item." + attribut.getNom()
                            + "}</option>\n";
                    allForms += "\t\t))}\n";
                    allForms += "\t\t</select>\n";
                    allForms += "\t</div>";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allForms;
    }

    public String getAllValueToFormsState(String tableName, Connection connection) throws Exception {
        String allForms = "";
        Path projectRoot = Paths.get(".").toAbsolutePath();
        System.setProperty("user.dir", projectRoot.toString());
        Attribut[] attributsFK = ClassGenerator.getAllFK(tableName, connection);
        for (int i = 0; i < attributsFK.length; i++) {
            if (attributsFK[i].getNom() != null) {
                allForms += attributsFK[i].getType() + "s:any = []";
            }
        }
        return allForms;
    }


}
