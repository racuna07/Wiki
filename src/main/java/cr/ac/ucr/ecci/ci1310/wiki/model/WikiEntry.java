package cr.ac.ucr.ecci.ci1310.wiki.model;

/**
 * A class that represents the the result of a query to search for a web page found in Wikipedia´s
 * database.
 */
public class WikiEntry {
    private int id; //Unique identifier for each page.
    private String title; //Each page´s main theme.
    private String sourceText; // The page´s body.

    /**
     * Class constructor
     * @param id:
     * @param title:
     * @param sourceText:
     */
    public WikiEntry(int id, String title, String sourceText){
        this.id = id;
        this.title = title;
        this.sourceText = sourceText;
    }

    /**
     * @return A String abbreviated representation of a Wikipedia´s entry.
     */
    public String snippet(){
        String result = "";
        result += "------------------------------------------------------------------------------------------\n";
        result += "PageTitle: " + title + "\nPageId: " + id + "\n";
        if(sourceText.length() < 100){
            result += sourceText+ "\n";
        }else {
            result += sourceText.substring(0, 100) + "...\n";
        }
        result += "------------------------------------------------------------------------------------------\n";
        return result;

    }

    /**
     * @return A full String representation of the corresponding page.
     */
    @Override
    public String toString(){
        String result = "";
        result += "------------------------------------------------------------------------------------------\n";
        result += "PageTitle: " + title + "\nPageId: " + id + "\n";
        result += "------------------------------------------------------------------------------------------\n";
        result += sourceText +"\n" ;
        result += "------------------------------------------------------------------------------------------\n";
        return result;
    }
}
