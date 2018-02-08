# davids-weather-app

![alt text](https://github.com/phase681/davids-weather-app/blob/master/screenshots/Screenshot%20at%202018-02-07%2019:46:01.png)

## Introduction
This Adroid app provides a simple way to look up local, and not so local, weather.  Current weather snapshots can be displayed by clicking on the map anywhere in the contenental US.  In addition, a zipcode search feature provides current weather snapshots of places a bit futher away in the US.  

## Development Thought Process
Implementing the functionality of the app occured in three main phases.  The first phase was to use Android's map layout to retreive Lat/Lon on map clicks.  The next phase was getting local weather data from the OpenWeatherMap api utilizing Lat/Lon coordinates.  Finally the UI was put in place to display the data in modals.  Then the extra creadit feature was added.

Development was done on Ubuntu using Android studio with an Android emulator (API 25).

## Required Functionality
This app loads with a map and a marker over the Reston Town Center:

![alt text](https://github.com/phase681/davids-weather-app/blob/master/screenshots/Screenshot%20at%202018-02-07%2019:43:41.png)

From here, a regular click on the map will open a modal dialog showing the current weather at the location clicked.  This click handler does not interfere with regular map dragging or zooming.  

![alt text](https://github.com/phase681/davids-weather-app/blob/master/screenshots/Screenshot%20at%202018-02-07%2019:44:20.png)

The modal is clean and simple, highlighting what many weather apps do: the current temperature.  The next largest text in the modal is a description of the current conditions.  Wind speed and humidity are added for those interested in them.  

Overall the goal in the dsign of this modal is to have important information immediatly recognized with very few distractions.  

At the bottom of the modal is an EXIT button that will return to the map view.  opening and closing modals can be done as many timeas as the user desisers.  Zoom controls were added to facilitate moving quickly to other parts of the US to check current weather anywhere.  


## Extra Credit
An additional feature was included as an ode to other weather interfaces: a zipcode look-up.  

![alt text](https://github.com/phase681/davids-weather-app/blob/master/screenshots/Screenshot%20at%202018-02-07%2019:44:46.png)

This works by entering a 5-digit zipcode in the text field of the upper-right corner and clicking the Done button on the on-screen keyboard or even just hitting the Enter button if running from an emulator.

In addition to opening a modal with the weather at the specified zipcode, the map will move to the general Lat/Lon coordinates of that zipcode anywhere in the contentental US:

![alt text](https://github.com/phase681/davids-weather-app/blob/master/screenshots/Screenshot%20at%202018-02-07%2019:45:09.png)

This shares much of the same code as the clickable lat/Lon, only the OpenWeatherMap URL is different as well as an overriden method to feed the rest of the app what it needs to get the weather for a specific zipcode.  

As before, once the modal is dismissed, the user can manipulate the map, enter in a new zipcode, click and open weather modals...etc. 

## Conclusion
This app provides two main ways to get weather, interacting with a map activity.  It has been designed to be simple to use, quick to get information, and pleasent to look at with a clean interface.  

