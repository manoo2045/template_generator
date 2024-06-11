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
import java.util.ArrayList;
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

public class FormGenerator {
    private static final Vector<String> TEMPLATE_FILE = new Vector<String>();;
    private final String CONFIG_FILE;
    private String url;
    private String insertUrl;
    private String methode;
    private String insertMethode;
    private String listLink;
    private String cssContent;
    private String dossier;
    private String extension;
    private String forms;
    private String formstate;

    private static final Map<String, String> CONFIG_MAP = new HashMap<>();

    static {
        CONFIG_MAP.put("SPRING", "config_file/springform.xml");
    }

    public FormGenerator(String techno) throws Exception {
        this.CONFIG_FILE = this.getConfigFil(techno);
        TEMPLATE_FILE.add("./laravelTemplete/ClassController.txt");
        TEMPLATE_FILE.add("./laravelTemplete/Class.txt");
        TEMPLATE_FILE.add("./laravelTemplete/List.blade.txt");
        TEMPLATE_FILE.add("./laravelTemplete/Route.txt");
        TEMPLATE_FILE.add("./laravelTemplete/insert.txt");
//        TEMPLATE_FILE.add("./laravelTemplete/ClassController.txt");
//        TEMPLATE_FILE.add("./laravelTemplete/create.page.ts.txt");
//        TEMPLATE_FILE.add("./ionicTemplate/create-routing.module.ts.txt");
//        TEMPLATE_FILE.add("./ionicTemplate/create.module.ts.txt");
//        TEMPLATE_FILE.add("./ionicTemplate/create.page.spec.ts.txt");
//        TEMPLATE_FILE.add("./ionicTemplate/app-routing.module.ts.txt");
        this.setParameter();
    }

    public void setParameter() throws Exception {
        this.url = this.getValue("util", "url");
        this.insertUrl = this.getValue("util", "insertUrl");
        this.methode = this.getValue("methods", "List");
        this.insertMethode = this.getValue("methods", "Insert");
        this.extension = this.getValue("util", "extension");
        this.dossier = this.getValue("util", "dossier");
        this.listLink = this.getValue("util", "listLink");
        this.cssContent = this.getOneValue("cssContent");
    }

    public String getConfigFil(String techno) {
        String csType = CONFIG_MAP.get(techno.toUpperCase());
        return (csType != null) ? csType : "";
    }

    public static String toJson(Attribut[] attributs, Attribut[] attributsFk){
        String json = "item = {\n";
        for (int i = 0; i < attributs.length; i++) {
            json += "\t\t" + attributs[i].getNom() +" : null,\n";
        }
        for (int i = 0; i < attributsFk.length; i++) {
            if (attributsFk[i].getNom() != null) {
                json += "\t\t" + attributsFk[i].getNom() +" : null,\n";
            }
        }

        return json + "\t}";
    }

    public static String validation(Attribut[] attributs, Attribut[] attributsFk){
        String json = "[\n";
        for (int i = 0; i < attributs.length; i++) {
            json += "\t\t'" + attributs[i].getNom() +"'=> ['required'],\n";
        }
        for (int i = 0; i < attributsFk.length; i++) {
            if (attributsFk[i].getNom() != null) {
                json += "\t\t'" + attributsFk[i].getNom() +"'=> ['required'],\n";
            }
        }

        return json + "\t]";
    }
    public static String array(Attribut[] attributs, Attribut[] attributsFk){
        String json = "[\n";
        for (int i = 0; i < attributs.length; i++) {
            json += "\t\t'" + attributs[i].getNom() +"'=> $request->"+ attributs[i].getNom()+",\n";
        }
        for (int i = 0; i < attributsFk.length; i++) {
            if (attributsFk[i].getNom() != null) {
                json += "\t\t'" +  attributsFk[i].getNom() +"'=> $request->"+ attributsFk[i].getNom()+",\n";
            }
        }

        return json + "\t]";
    }

    public static String toFormData(Attribut[] attributs, Attribut[] attributsFk){
        String formData = "let formData = new FormData(); \n";

        for (int i = 0; i < attributs.length; i++) {
            formData +=  "\t\tformData.append('"+attributs[i].getNom()+"',this."+attributs[i].getNom()+" );\n";
        }
        for (int i = 0; i < attributsFk.length; i++) {
            if (attributsFk[i].getNom() != null) {
                formData +=  "\t\tformData.append('"+attributsFk[i].getNom()+"',this."+attributsFk[i].getNom()+" );\n";
            }
        }

        return formData;
    }

    public void genereteIonicAngular(String className) throws Exception {
        try {
            String base = "postgres";
            Connection con = Connexion.getConnection(base);

            className = FormGenerator.capitalizeFirstLetter(className);
            Vector<String> vtemplate = loadTemplateFromFile();
            Attribut[] attributs = ClassGenerator.getColonnes(className.toLowerCase(), con);
            Attribut[] attributsFK = ClassGenerator.getAllFK(className.toLowerCase(), con);

            String declarFn = getAllDeclarFonctAttribut(className.toLowerCase(),con);

            String allFn = "";
            allFn = getAllFonctAttribut(className.toLowerCase(),con);
            this.forms = this.getAllValueToFormsIonic(className.toLowerCase(), con);
            this.formstate = this.getAllValueToFormsState(className.toLowerCase(), con);
            String att = getAttributs(attributs,attributsFK);
//            String template = vtemplate.get(0);
//            template = template.replace("#FORMS#", forms);

            String back = vtemplate.get(0);
//            back = back.replace("#URL#", url);
//            back = back.replace("##FORMDATA##",toFormData(attributs,attributsFK));
            back = back.replace("#CLASS_NAME_2#",capitalizeFirstLetter(className.toLowerCase()));
            back = back.replace("#CLASS_NAME#",className.toLowerCase());
            back = back.replace("#ARRAY#",validation(attributs,attributsFK));

            back = back.replace("#ARRAY_2#",array(attributs,attributsFK));
//            back = back.replace("##FORMS_STATE##",formstate);
//            back = back.replace("##ATTRIBUTS##",att);
//            back = back.replace("##FUNCTION##",allFn);
//            back = back.replace("##DECLAR_FUNCTION##",declarFn);
            String backfileName = capitalizeFirstLetter(className.toLowerCase()).concat("Controller.php");
//            String routing = vtemplate.get(2);
//            String module = vtemplate.get(3);
//            String spec = vtemplate.get(4);
//
//            String allRout = vtemplate.get(5);
//            allRout = allRout.replace("#CLASS_NAME_2#",capitalizeFirstLetter(className.toLowerCase()));
            dossier = dossier.replace("#CLASS_NAME_2#",capitalizeFirstLetter(className.toLowerCase()));

            String classe = vtemplate.get(1);
            classe = classe.replace("#CLASS_NAME_2#",capitalizeFirstLetter(className.toLowerCase()));
            classe = classe.replace("#CLASS_NAME#",className.toLowerCase());

            String list = vtemplate.get(2);
            list = list.replace("#CLASS_NAME_2#",capitalizeFirstLetter(className.toLowerCase()));
            list = list.replace("#CLASS_NAME#",className.toLowerCase());
            list = list.replace("#TABLE_COLUMN#",generateTableColumn(attributs,attributsFK));
            list = list.replace("#TABLE_ROW#",generateTableRow(attributs,attributsFK,className.toLowerCase()));
            list = list.replace("#FORMS#", forms);

            String route = vtemplate.get(3);
            route = route.replace("#CLASS_NAME_2#",capitalizeFirstLetter(className.toLowerCase()));
            route = route.replace("#CLASS_NAME#",className.toLowerCase());

            String insert = vtemplate.get(4);
            insert = insert.replace("#FORMS#", forms);
            insert = insert.replace("#CLASS_NAME_2#",capitalizeFirstLetter(className.toLowerCase()));
            insert = insert.replace("#CLASS_NAME#",className.toLowerCase());

            File folder = new File(dossier);
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier +"/"+capitalizeFirstLetter(className.toLowerCase())+".php"))) {
                        writer.write(classe);
                    } catch (Exception e) {
                        throw e;
                    }
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier+"/"+ backfileName))) {
                        writer.write(back);
                    } catch (Exception e) {
                        throw e;
                    }
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier+"/list_"+className.toLowerCase()+".blade.php" ))) {
                        writer.write(list);
                    } catch (Exception e) {
                        throw e;
                    }
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier+"/insert_"+className.toLowerCase()+".blade.php" ))) {
                        writer.write(insert);
                    } catch (Exception e) {
                        throw e;
                    }
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier+"/route_"+className.toLowerCase()+".php" ))) {
                        writer.write(route);
                    } catch (Exception e) {
                        throw e;
                    }
                } else {
                    throw new Exception("Error while creating folder");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generate(String className) throws Exception {
        try {
            String base = "postgres";
            Connection con = Connexion.getConnection(base);

            className = FormGenerator.capitalizeFirstLetter(className);
            String template = loadTemplateFromFile().get(0);
            this.forms = this.getAllValueToForms(className.toLowerCase(), con);
            this.formstate = this.getAllValueToFormsState(className.toLowerCase(), con);

            template = template.replace("#URL#", url);
            template = template.replace("#INSERT_URL#", insertUrl);
            template = template.replace("#INSERT_METHOD#", insertMethode);
            template = template.replace("#LIST_LINK#", listLink);
            template = template.replace("#METHOD_LIST#", methode);
            template = template.replace("#CLASS_NAME_2#", className.toLowerCase());
            template = template.replace("#FORMS#", forms);
            template = template.replace("#FORMS_STATE#", formstate);

            File folder = new File(dossier);
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier + "/Form" + extension))) {
                        writer.write(template);
                    } catch (Exception e) {
                        throw e;
                    }
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(dossier + "/Form.css"))) {
                        writer.write(cssContent);
                    } catch (Exception e) {
                        throw e;
                    }
                } else {
                    throw new Exception("Error while creating folder");
                }
            } else {
                try (BufferedWriter writer = new BufferedWriter(
                        new FileWriter(dossier + "/Form" + extension))) {
                    writer.write(template);
                } catch (Exception e) {
                    throw e;
                }
                try (BufferedWriter writer = new BufferedWriter(
                        new FileWriter(dossier + "/Form.css"))) {
                    writer.write(cssContent);
                } catch (Exception e) {
                    throw e;
                }
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

    public String getAllValueToFormsIonic(String tableName, Connection connection) throws Exception {
        String allForms = "";
        Path projectRoot = Paths.get(".").toAbsolutePath();
        System.setProperty("user.dir", projectRoot.toString());
        Attribut[] attributs = ClassGenerator.getColonnes(tableName, connection);
        Attribut[] attributsFK = ClassGenerator.getAllFK(tableName, connection);

        try {
            for (int i = 0; i < attributs.length; i++) {
                allForms += "\t\t<div class=\"mb-3\">\n";
                allForms += "\t\t\t<label for=\"exampleInput_"+attributs[i].getNom()+"\" class=\"form-label\">"+attributs[i].getNom()+"</label>\n";
                allForms += "\t\t\t<input name=\""+attributs[i].getNom()+"\" value=\"{{ old('"+attributs[i].getNom()+"') }}\" type=\""+this.getValue("type",attributs[i].getType())+"\" class=\"form-control @error(\""+attributs[i].getNom()+"\") is-invalid @enderror\" id=\"exampleInput_"+attributs[i].getNom()+"\" aria-describedby=\"#CLASS_NAME#lHelp\">\n";
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
                    allForms += "\t\t\t<label for=\"exampleInput_"+attributsFK[i].getNom()+"\" class=\"form-label\">"+attribut.getNom()+"</label>\n";
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

     public static String fonctAttribut(String nomAttribut){
        return  "fectAll"+nomAttribut+"s() {\n" +
                "    axios.get(\"http://localhost:5000/"+nomAttribut.trim()+"s\")\n" +
                "      .then(\n" +
                "        (response)=>{\n" +
                "          this."+nomAttribut.trim()+"s = response.data\n" +
                "          console.log(response.data)\n" +
                "        }\n" +
                "      )\n" +
                "      .catch(\n" +
                "        (error)=>{\n" +
                "          console.log(error)\n" +
                "        }\n" +
                "      )\n" +
                "  }" ;
     }

    public static String getAllDeclarFonctAttribut(String tableName, Connection connection) throws Exception {
        StringBuilder allfn = new StringBuilder();
        Attribut[] attributsFK = ClassGenerator.getAllFK(tableName, connection);
        for (Attribut attribut : attributsFK) {
            if (attribut.getNom() != null) {
                allfn.append("this.fectAll").append(attribut.getType()).append("s()").append("\n");
            }
        }

        return allfn.toString();
    }
     public static String getAllFonctAttribut(String tableName, Connection connection) throws Exception {
         Attribut[] attributsFK = ClassGenerator.getAllFK(tableName, connection);
         StringBuilder allfn = new StringBuilder();
         for (Attribut attribut : attributsFK) {
             if (attribut.getNom() != null) {
                 allfn.append(fonctAttribut(attribut.getType())).append("\n");
             }
         }

        return allfn.toString();
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
        StringBuilder allForms = new StringBuilder();
        Path projectRoot = Paths.get(".").toAbsolutePath();
        System.setProperty("user.dir", projectRoot.toString());
        Attribut[] attributsFK = ClassGenerator.getAllFK(tableName, connection);
        for (Attribut attribut : attributsFK) {
            if (attribut.getNom() != null) {
                allForms.append(attribut.getType()).append("s:any = []");
            }
        }
        return allForms.toString();
    }

    public String getAttributs(Attribut[] attributs, Attribut[] attributsFk){

        String att ="";
        for (int i = 0; i < attributs.length; i++) {
            att += "\t\t"+attributs[i].getNom()+":string = '';\n";
        }
        for (int i = 0; i < attributsFk.length; i++) {
            if (attributsFk[i].getNom() != null) {
                att += "\t\t"+attributsFk[i].getNom()+":string' = '';\n";
            }
        }
        return att;
    }

    public String generateTableColumn(Attribut[] attributs, Attribut[] attributsFk) {
        String att ="\n";
        for (int i = 0; i < attributs.length; i++) {
            att += "\t\t\t\t\t\t\t\t\t<th class=\"footable-sortable\"> ";
            att += attributs[i].getNom();
            att += " <th>\n";
        }
        for (int i = 0; i < attributsFk.length; i++) {
            if (attributsFk[i].getNom() != null) {
                att += "\t\t\t\t\t\t\t\t\t<th class=\"footable-sortable\" > ";
                att += attributsFk[i].getNom();
                att += " <th>\n";
            }
        }
        return att;
    }

    public String generateTableRow(Attribut[] attributs, Attribut[] attributsFk,String className) {
        String att ="\n";
        for (int i = 0; i < attributs.length; i++) {
            att += "\t\t\t\t\t\t\t\t\t<td class=\"footable-sortable\"> {{ $"+className+"->";
            att += attributs[i].getNom();
            att += " }} <td>\n";
        }
        for (int i = 0; i < attributsFk.length; i++) {
            if (attributsFk[i].getNom() != null) {
                att += "\t\t\t\t\t\t\t\t\t<td class=\"footable-sortable\"> {{ $"+className+"->";
                att += attributsFk[i].getNom();
                att += " }} <td>\n";
            }
        }
        return att;
    }


}
