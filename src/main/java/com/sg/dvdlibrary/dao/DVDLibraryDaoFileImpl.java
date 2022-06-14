/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author abdulrahman
 */
public class DVDLibraryDaoFileImpl implements DVDLibraryDao{
    
    private Map<String, DVD> DVDList = new HashMap<>();
    
    public static final String LIBRARY_FILE = "library.txt";
    public static final String DELIMETER = "::";
    
    // add new DVD
    @Override
    public DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException {
        // marshall data and write it to file
        this.loadLibrary();
        
        DVD prevDVD = DVDList.put(title, dvd);
        
        this.writeLibrary();
        
        return prevDVD;
        
    }
    
    // get all the DVDs that are currently stored
    @Override
    public List<DVD> getAllDVD() throws DVDLibraryDaoException {
        // unmarshall data and load it from file
        this.loadLibrary();
        
        return new ArrayList<DVD>(DVDList.values());
        
    }
    
    // get information for a specific DVD, if it exists. If not, return null object
    @Override
    public DVD getDVDInfo(String title) throws DVDLibraryDaoException {
        // unmarshall data from loaded file
        this.loadLibrary();
        
        DVD dvd = DVDList.get(title);
        
        if (dvd != null){
            return dvd;
        } else {
            return null;
        }
    }
    
    // edit the information for a particular DVD
    @Override
    public DVD editDVD(String title, String field, String newVal) throws DVDLibraryDaoException {
        
        this.loadLibrary();
        DVD dvd = DVDList.get(title);
        
        if (dvd != null) {
            // get which field the user wants to edit
            switch (field){
                case "title" -> {
                    DVDList.remove(dvd.getTitle(), dvd);
                    dvd.setTitle(newVal);
                    DVDList.put(newVal, dvd);
                }
                case "release date" -> dvd.setReleaseDate(newVal);
                case "mpaaRating" -> dvd.setMpaaRating(newVal);
                case "directorName" -> dvd.setDirectorName(newVal);
                case "studio" -> dvd.setStudio(newVal);
                case "note" -> dvd.setNote(newVal);
            }
            this.writeLibrary();
            return dvd;
            
        } else {
            
            return null;
            
        }
    }
    
    // remove a particular DVD from storage
    @Override
    public DVD removeDVD(String title) throws DVDLibraryDaoException {
        // unmarshall data from loaded file
        this.loadLibrary();
        
        DVD dvd = DVDList.remove(title);
        
        this.writeLibrary();
        
        return dvd;
    }
    
    private String marshallDVD(DVD dvd) {
        
        String dvdAsText = dvd.getTitle() + DELIMETER;
        
        dvdAsText += dvd.getReleaseDate() + DELIMETER
                  + dvd.getMpaaRating() + DELIMETER
                  + dvd.getDirectorName() + DELIMETER
                  + dvd.getStudio() + DELIMETER
                  + dvd.getNote();
        
        return dvdAsText;
        
    }
    
    private DVD unmarshallDVD(String dvdAsText){
        
        String[] dvdTokens = dvdAsText.split(DELIMETER);
        
        String title = dvdTokens[0];
        String releaseDate = dvdTokens[1];
        String mpaaRating = dvdTokens[2];
        String directorName = dvdTokens[3];
        String studio = dvdTokens[4];
        String note = dvdTokens[5];
        
        DVD dvd = new DVD(title);
        dvd.setReleaseDate(releaseDate);
        dvd.setMpaaRating(mpaaRating);
        dvd.setDirectorName(directorName);
        dvd.setStudio(studio);
        dvd.setNote(note);
        
        return dvd;
    }
    
    private void writeLibrary() throws DVDLibraryDaoException {
        
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new DVDLibraryDaoException("Could not save DVD data.", e);
        }
        
        String dvdAsText;
        
        List<DVD> dvdList = this.getAllDVD();
        
        for (DVD dvd: dvdList) {
            dvdAsText = marshallDVD(dvd);
            out.println(dvdAsText);
            out.flush();
        }
        
        out.close();
        
    }
    
    private void loadLibrary() throws DVDLibraryDaoException {
        
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                        new BufferedReader(
                            new FileReader(LIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new DVDLibraryDaoException("Could not load DVD data into memory.", e);
        }
        
        String currentLine;
        
        DVD currentDVD;
        
        while (scanner.hasNextLine()) {
            
            currentLine = scanner.nextLine();
            
            currentDVD = unmarshallDVD(currentLine);
            
            DVDList.put(currentDVD.getTitle(), currentDVD);
            
        }
        
        scanner.close();
        
    }
    
}
