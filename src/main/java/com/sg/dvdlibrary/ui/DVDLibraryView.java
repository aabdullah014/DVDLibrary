/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.dvdlibrary.ui;

import com.sg.dvdlibrary.dto.DVD;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author abdulrahman
 */
public class DVDLibraryView {
    // dependency injection
    private UserIO io;

    public DVDLibraryView(UserIO io) {
        this.io = io;
    }
    
    // used to get user selection
    public int printMenuAndGetSelection() {
        
        io.print("Main Menu");
        io.print("1. List all DVDs in Library");
        io.print("2. Add a DVD to the Library");
        io.print("3. Edit a DVD in the Library");
        io.print("4. Search for a DVD's information");
        io.print("5. Remove a DVD from the Library");
        io.print("6. Exit");
        
        return io.readInt("Please select from the above choices.", 1, 6);
    }
    // user interaction when adding new DVD
    public DVD getNewDVDInfo() {
        
        boolean validInput = false;
        String releaseDate;
        
        String title = io.readString("Please enter the title");
        
        
        do{
            releaseDate = io.readString("Please enter the release date in DD-MM-YYYY format.");
            
            // make sure the length is 10
            if (releaseDate.length() != 10) {
                continue;
            }
            
            int day = Integer.parseInt(releaseDate.substring(0,2));
            int month = Integer.parseInt(releaseDate.substring(3,5));
            int year = Integer.parseInt(releaseDate.substring(6));
            // make sure the release date is a possible data  in correct format
            if (releaseDate.substring(2,3).equals("-") && releaseDate.substring(2,3).equals("-")
                    && day < 32 && day > 0 && month < 13 && month > 0 && year > 0 && year < 2022){
                validInput = true;
            }
        } while (!validInput);
        
        String mpaaRating = io.readString("Please enter the MPAA Rating.");
        String directorName = io.readString("Please enter the director's name.");
        String studio = io.readString("Please enter the studio name.");
        String note = io.readString("Please enter any additional notes you may have.");
        
        DVD currentDVD = new DVD(title);
        
        currentDVD.setReleaseDate(releaseDate);
        currentDVD.setMpaaRating(mpaaRating);
        currentDVD.setDirectorName(directorName);
        currentDVD.setStudio(studio);
        currentDVD.setNote(note);
        
        return currentDVD;
        
    }
    
    public String displayCreateSuccessBanner() {
        
        io.print("************************* SUCCESSFULLY CREATED *************************");
        
        boolean validEntry = false;
        
        String addAgain;
        
        do{
            addAgain = io.readString("Would you like to add another DVD? Y/N");
            
            if (addAgain.equals("y") || addAgain.equals("Y") || addAgain.equals("n") || addAgain.equals("N")) {
                validEntry = true;
            }
        } while (!validEntry);
        
        return addAgain;
        
    }
    // interaction to find what field user wants to edit
    public String getEditField() {
        
        String field = "";
        
        io.print("1. Title");
        io.print("2. Release Date");
        io.print("3. MPAA Rating");
        io.print("4. Director Name");
        io.print("5. Studio");
        io.print("6. Additional Notes");
        
        int selection = io.readInt("Which field would you like to edit? Please select from the above choices.", 1, 6);
        
        // get what field user wants to change
        switch (selection){
            case 1 -> field = "title";
            case 2 -> field = "releaseDate";
            case 3 -> field = "mpaaRating";
            case 4 -> field = "directorName";
            case 5 -> field = "studio";
            case 6 -> field = "note";
        }
        
        return field;
        
    }
    // interaction to find what value in the given field user wants to edit
    public String getEditVal(String field) {
        // for ease of use, fields are named in a more readable manner using a Map
        Map<String, String> fieldToName = new HashMap<>();
        
        fieldToName.put("title", "Title");
        fieldToName.put("releaseDate", "Release Date");
        fieldToName.put("mpaaRating", "MPAA Rating");
        fieldToName.put("directorName", "Director's Name");
        fieldToName.put("studio", "Studio");
        fieldToName.put("note", "Note");
        
        String newVal = io.readString("What would you like to change " + fieldToName.get(field)  + " to?");
        
        return newVal;
        
    }
    
    public void displayEditDVDBanner() {
        
        io.print("************************* EDIT DVD *************************");
        
    }
    
    public String displayEditDVD(DVD dvd) {
        
        if (dvd != null) {
            
            io.print("DVD Successfully Edited.");
            this.displayBanner();
            
        } else {
            
            io.print("No such DVD exists in the Library.");
            
        }
        
        boolean validEntry = false;
        
        String editAgain;
        
        do{
            editAgain = io.readString("Would you like to edit another DVD or edit another field on this DVD? Y/N");
            
            if (editAgain.equals("y") || editAgain.equals("Y") || editAgain.equals("n") || editAgain.equals("N")) {
                validEntry = true;
            }
        } while (!validEntry);
        
        return editAgain;
        
    }
    
    public void displayCreateDVDBanner() {
        
        io.print("************************* ADD DVD *************************");
        
    }
    
    public void displayDVDList(List<DVD> dvdList){
        for (DVD dvd: dvdList) {
            String dvdInfo = String.format(
                    "- %s : Released on %s, Rated %s, Directed by %s in Studio %s. Additional notes: %s", 
                    dvd.getTitle(),
                    dvd.getReleaseDate(),
                    dvd.getMpaaRating(),
                    dvd.getDirectorName(),
                    dvd.getStudio(),
                    dvd.getNote());
            
            io.print(dvdInfo);
        }
        
        this.enterToContinue();
    }
    
    public void displayAllDVDBanner() {
        
        io.print("************************* DISPLAY ALL DVDs IN LIBRARY *************************");
        
    }
    
    public void displayDVDBanner() {
        
        io.print("************************* DVD INFO *************************");
        
    }
    
    public String getDVDTitleChoice() {
        
        return io.readString("Please enter the title.");
        
    }
    // information for a particular DVD
    public void displayDVD(DVD dvd){
        
        this.displayBanner();
        
        if (dvd != null){
            io.print("Title: " + dvd.getTitle());
            io.print("Release Date: " + dvd.getReleaseDate());
            io.print("MPAA Rating: " + dvd.getMpaaRating());
            io.print("Director: " + dvd.getDirectorName());
            io.print("Studio: " + dvd.getStudio());
            io.print("Additional Notes: " + dvd.getNote());
        } else {
            io.print("That DVD is not currently in the library.");
        }
        
        this.enterToContinue();
        
    }
    
    public void displayRemoveDVDBanner() {
        
        io.print("************************* DELETE DVD *************************");
        
    }
    // interaction to remove a DVD
    public String displayRemoveDVD(DVD dvd) {
        
        if (dvd != null) {
            
            io.print("DVD Successfully Removed.");
            this.displayBanner();
            
        } else {
            
            io.print("No such DVD exists in the Library.");
            
        }
        
        boolean validEntry = false;
        
        String removeAgain;
        
        do{
            removeAgain = io.readString("Would you like to remove another DVD? Y/N");
            
            if (removeAgain.equals("y") || removeAgain.equals("Y") || removeAgain.equals("n") || removeAgain.equals("N")) {
                validEntry = true;
            }
        } while (!validEntry);
        
        return removeAgain;
        
    }
    
    public void enterToContinue() {
        io.readString("Please hit enter to continue.");
    }
    
    // displayed on exit.
    public void displayExitBanner(){
        
        io.print("Goodbye!");
        
    }
    // if an unknown command is used.
    public void displayUnknownCommandBanner(){
        
        io.print("Unknown command!");
        
    }
    // delimiter between general sections
    public void displayBanner(){
        io.print("====================================================");
    }
    // if a general error that is not caught comes up
    public void displayError(String error){
        
        io.print("**********************ERRROR************************");
        io.print(error);
        
    }
    // if a DVD is not found
    public void dvdNotFound(){
        io.print("Could not find that DVD to edit. Please try again.");
    }
    
}
