import com.google.gson.JsonParser;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LibraryDatabase {
   private String dbFileName;
   private File dataFile;
   private String keyword;
   private int numTiers;
   private JsonParser parser;

   private InputStream inputStream;
   private Reader reader;
   private Map<String, Paper> dictionary;


   public LibraryDatabase(){
      dictionary=new HashMap<String,Paper>();
   }

   public void addPaper(Paper p){
      dictionary.put(p.getId(),p);
   }

   public Paper getPaperInDB(String id){
      if(dictionary.containsKey(id))
         return dictionary.get(id);
      else
         return null;
   }

   public boolean isEmptyDataBase(){
      return dictionary.isEmpty();
   }

   public void quickView(){
      dictionary.forEach((id,p)->System.out.println(id+": "+p.getTitle()));
   }

   private void findPapersReferencePaper(String paperId, int maxTiers, int currentTier){
      ArrayList<Paper>tierList=new ArrayList<>();
      
      for (Map.Entry<String, Paper> entry : dictionary.entrySet()) {
         String id = entry.getKey();
         Paper p = entry.getValue();
         if (p.hasReferenceTo(paperId)) {
            tierList.add(p);
         }
      }//end for
      if(tierList.size()>0) {
    	  System.out.println("-----------------------------------------------------------------------------");
    	  System.out.println(dictionary.get(paperId).toString()+"\n");
      }
      for (int i=0; i<tierList.size();i++){
         System.out.println("Tier "+currentTier+":\t");
         System.out.println(tierList.get(i).toString()+"\n\n");

         if(currentTier<=maxTiers) {
            ++currentTier;
            findPapersReferencePaper(tierList.get(i).getId(), maxTiers, currentTier);
         }
      }
   }

   public void findPapersReferencePaper(String paperId,int n){
      findPapersReferencePaper(paperId,n,1);
   }

   public int getNumberOfRecords(){
      return dictionary.size();
   }


}
