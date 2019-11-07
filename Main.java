import com.google.gson.Gson;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main extends JFrame{
    private static final long MEGABYTE = 1024L*1024L;
    public static LibraryDatabase DB = new LibraryDatabase();
    public static ArrayList<String> idResultList= new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //start
        InputStream inputStream;
        String fileName="";
        String keyword = null;
        int numOfTiers;
        File dbFile;
        ArrayList<Paper> matchedPapers= new ArrayList<>();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle( "Location to Test File in .txt or .json" );
        chooser.setApproveButtonText("Select path");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter=new FileNameExtensionFilter(".txt",".json");
        chooser.setFileFilter(filter);
        if( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ) {
            fileName  = chooser.getSelectedFile().getPath();
        }

        File benchmarkFile=new File("benchMark.txt");
        FileWriter fr=new FileWriter(benchmarkFile,true);
        long startTime = System.currentTimeMillis();
        Runtime runtime=Runtime.getRuntime();


        Scanner sc = new Scanner(System.in);
        //System.out.println("Welcome to my program");
      //  System.out.println("Please enter a file name");
        //fileName = sc.next();
        System.out.println("Enter keyword = ");
       keyword=sc.next();
        System.out.println("Enter tier n=");
        numOfTiers=sc.nextInt();
       /* keyword =
                JOptionPane.showInputDialog ( "Enter first integer" );
        String tiers =
                JOptionPane.showInputDialog ( "Enter number of Tiers" );
        try {
            numOfTiers = Integer.parseInt(tiers);
        }catch (NumberFormatException e){
            System.out.println("number of Tiers has to be an integer");
            e.printStackTrace();
        }*/

        dbFile=new File(fileName);
        System.out.println(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(dbFile));
        String currentLine = reader.readLine();
        while(currentLine!=null) {

            Gson gson = new Gson();
            if(isValidJson(currentLine)) {
                Paper singlePP = gson.fromJson(currentLine, Paper.class);
                DB.addPaper(singlePP);
                idResultList.add(singlePP.getId());
                if(singlePP.keywordIsFound(keyword)) {
                    matchedPapers.add(singlePP);
                    System.out.println("result "+matchedPapers.size()+":");
                   System.out.println(singlePP+"\n");
                }
            }
            currentLine =reader.readLine();
        }




        //DB.quickView();
        idResultList.forEach(resultID->DB.findPapersReferencePaper(resultID,numOfTiers));
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");

        // calculate memory used
        //run garbage collector
        runtime.gc();
        long memory = runtime.totalMemory()-runtime.freeMemory();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        try{
            fr.write("number of Json Object in file = "+DB.getNumberOfRecords());
            fr.write("Keyword ="+keyword+"\n");
            fr.write("n = "+numOfTiers);
            fr.write("\nMemory used: "+memory+ " bytes");
            fr.write("\nUsed memory is megabytes: "
                    + memory/(Math.pow(2,20)));
            fr.write("\nElapse Time: " + elapsedTime);


        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                fr.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }//end main function

    private static boolean isValidJson(String jsonString){
        try{
            return (JsonParser.parseString(jsonString).getAsJsonObject()!=null);
        }catch(Throwable ignored){}
        return false;
    }


}
