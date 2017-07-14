package cr.ac.ucr.ecci.ci1310.wiki.model;

/**
 * Created by Rodrigo on 7/11/2017.
 */
public class WikiEntry {
    private int id;
    private String title;
    private String sourceText;

    public WikiEntry(int id, String title, String sourceText){
        this.id = id;
        this.title = title;
        this.sourceText = sourceText;

    }



    public String snippet(){
        String result = "";
        result += "------------------------------------------------------------------------------------------\n";
        result += "PageTitle: " + title + "\nPageId: " + id + "\n";
        if(sourceText.length()<100){
            result += sourceText+ "\n";
        }else {
            result += sourceText.substring(0, 100) + "...\n";
        }
        result += "------------------------------------------------------------------------------------------\n";
        return result;

    }
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


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }
}
