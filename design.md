# Design

## Algorithm:
    * Main class: for demonstating program and meausre program performance.
    * Paper class:
        ** Each record will be transformed into a Paper object. 
        ** This object can return a general informations about the paper such as Id, title, author. 
        ** Paper class also includes a function that check if it cited another paper when this paper is given another paper's id. 
        
    * LibraryDatabase class:
        ** This class demonstrate a library database that user will need to search through all records.
        ** This class contains a dictionary field, distionary will containt a key  is ID of the book, and value is the corresponding paper object. 
        ** User can have a quickView about dictionary.
        ** This class will be able to help user find all the paper thas reference to a given paper.
    * SearchTest class:
        ** For tesing purpose only.



