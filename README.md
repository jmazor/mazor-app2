# Mazor's Inventory Application
The following contains instructions on how to this application.
The application should be fairly intuitive, but please visit this file for specific instructions.

## Import List 

**Json** 
  - File -> Import Data -> Select a valid .json file

**HTML**
   - File -> Import Data -> Select a valid .html file

**TSV**
   - File -> Import Data -> Select a valid .txt file


## Export List


**Json**
- File -> Export Data -> Select .json file type

**HTML**
- File -> Export Data -> Select .html file type

**TSV**
- File -> Export Data -> Select .txt file type

## Add Record

Type Information into text fields on the bottom and hit add when done
* Serial Number must be in A-XXX-XXX-XXX format
  * A is a Character
  * X is a Digit or Character
* Serial Number must be unique in list
* Name must be between 2 and 256 characters long
* Value must be greater than or equal to zero
  * $ in front is optional
* Inventory can not contain more than 2000 records

## Delete Record

Select a record by clicking on it and press the Delete button

## Clear Data

File -> Clear Data

## Edit Item

Double-click on field and hit enter when done editing
* Must follow all Add Record requirements

## Search Record

Type search fields in text fields on bottom
Hit search when done
* Search finds all fields that contains the values searched
* Search is case-insensitive