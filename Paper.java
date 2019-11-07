import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Paper {
    private String id;
    private String title;
    private String year;
    private JsonArray authors;
    private List<String> references;

    public Paper(JsonObject jsPaper) {
        this.id = jsPaper.get("id").getAsString();
        this.title = jsPaper.get("title").getAsString();
        this.year = jsPaper.get("year").getAsString();
        this.authors=jsPaper.get("authors").getAsJsonArray();
        this.references = (List<String>) jsPaper.get("references");
    }

    public String getTitle() {
        return this.title;
    }

    public String getId() {
        return this.id;
    }

    public String getYear() {
        return this.year;
    }

    public String getAuthorNames() {
        List<String> authorNames= new ArrayList<>();
        for(int i=0;i<authors.size();i++){
            authorNames.add(authors.get(i).getAsJsonObject().get("name").getAsString());
        }
        //return authorNames.toString();
        return String.join(", ", authorNames);
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String toString() {
        String s = "title: " + this.title;
        s = s + "\nid: " + this.id;
        s = s + "\nyear: " + this.year;
        s=s+"\nauthors: "+getAuthorNames();
        if(this.references!=null){
            s=s+"\nreferences: "+ String.join(", ",this.references);
        }
        return s;
    }

    public boolean keywordIsFound(String keyword) {
        boolean isFound = this.title.toLowerCase().contains(keyword.toLowerCase());
        return isFound;
    }
    private String arrayListToString(ArrayList<String> arr){
        String listString = arr.stream().map(Object::toString)
                .collect(Collectors.joining(","));
        return listString;
    }
    public Boolean hasReferenceTo(String id){
        if(this.references==null)
            return false;
        String temp = this.references.stream()
                .filter(id::equals)
                .findAny()
                .orElse(null);


        if(temp!=null) {
            System.out.println("* "+temp +" is cited by "+this.id + " *");
            return true;
        }
        else
            return false;
    }
}
