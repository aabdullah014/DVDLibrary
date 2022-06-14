/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.dvdlibrary.controller;

import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.dao.DVDLibraryDaoException;
import com.sg.dvdlibrary.dto.DVD;
import com.sg.dvdlibrary.ui.DVDLibraryView;
import java.util.List;

/**
 *
 * @author abdulrahman
 */
public class DVDLibraryController {
    
    // use dependency injection to eliminate hard-coded interface implementations
    private final DVDLibraryView view;
    private final DVDLibraryDao dao;

    public DVDLibraryController(DVDLibraryView view, DVDLibraryDao dao) {
        this.view = view;
        this.dao = dao;
    }
    
    
    
    public void run(){
        
        boolean keepGoing = true;
        int menuSelection = 0;
        
        try {
            while (keepGoing){

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1 -> this.listDVDs();
                    case 2 -> this.addDVD();
                    case 3 -> this.editDVD();
                    case 4 -> this.viewDVD();
                    case 5 -> this.removeDVD();
                    case 6 -> keepGoing = false;
                    default -> this.unknownCommand();
                    } // end switch
            } // end while
        } catch (DVDLibraryDaoException e) {
            
            view.displayError(e.getMessage());
            
        }
        
        this.exit();
        
    }
    
    // get the user's selection on main menu
    private int getMenuSelection(){
        
        return view.printMenuAndGetSelection();
        
    }
    
    // delegate what to do to add a DVD to view and dao
    private void addDVD() throws DVDLibraryDaoException{
        
        String addAgain;
        
        // continue until user doesn't want to add more DVDs
        do{
            view.displayCreateDVDBanner();

            DVD dvd = view.getNewDVDInfo();
            dao.addDVD(dvd.getTitle(), dvd);

            addAgain = view.displayCreateSuccessBanner();
        } while (addAgain.equals("y") || addAgain.equals("Y"));
        
    }
    
    // list all DVDs currently stored
    private void listDVDs() throws DVDLibraryDaoException {
        
        view.displayAllDVDBanner();
        List<DVD> dvdList = dao.getAllDVD();
        view.displayDVDList(dvdList);
        
    }
    
    // get information for a specific DVD
    private void viewDVD() throws DVDLibraryDaoException {
        
        view.displayDVDBanner();
        
        String title = view.getDVDTitleChoice();
        DVD dvd = dao.getDVDInfo(title);
        
        view.displayDVD(dvd);
        
    }
    
    // remove a DVD
    private void removeDVD() throws DVDLibraryDaoException {
        
        String removeAgain;
        
        // continue until user doesn't want to delete more DVDs
        do{
            view.displayRemoveDVDBanner();

            String title = view.getDVDTitleChoice();
            DVD dvd = dao.removeDVD(title);

            removeAgain = view.displayRemoveDVD(dvd);
        } while (removeAgain.equals("y") || removeAgain.equals("Y")); 
    }
    
    // edit a specific DVD
    private void editDVD() throws DVDLibraryDaoException {
        
        String editAgain;
        DVD dvd = null;
        
        // continue until user doesn't want to edit more DVDs or fields
        do{
            view.displayEditDVDBanner();

            String title = view.getDVDTitleChoice();
            
            if (dao.getDVDInfo(title) != null){
                
                String field = view.getEditField();
                String newVal = view.getEditVal(field);
                dvd = dao.editDVD(title, field, newVal);
                
            } else { // if DVD with that title does not exist
                view.dvdNotFound();
                editAgain = "y";
            }
        
            editAgain = view.displayEditDVD(dvd);
            
        } while (editAgain.equals("y") || editAgain.equals("Y"));
        
    }
    
    // if user inputs an unknown command
    private void unknownCommand(){
        
        view.displayUnknownCommandBanner();
        
    }
    
    // exit program
    private void exit(){
        
        view.displayExitBanner();
        
    }
            
}
