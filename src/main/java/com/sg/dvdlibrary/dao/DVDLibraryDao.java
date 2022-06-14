/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.util.List;

/**
 *
 * @author abdulrahman
 */
public interface DVDLibraryDao {
    
    // add a DVD to the Library
    DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException;
    
    // display all DVDs currently in the library
    List<DVD> getAllDVD() throws DVDLibraryDaoException;
    
    // get the information for a particular DVD
    DVD getDVDInfo(String title) throws DVDLibraryDaoException;
    
    // edit the information for a particular DVD
    DVD editDVD(String title, String field, String newVal) throws DVDLibraryDaoException;
    
    // remove a particular DVD from the library
    DVD removeDVD(String title) throws DVDLibraryDaoException;
}
