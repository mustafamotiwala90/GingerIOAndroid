GingerAndroidTest: 

1. Used the REST API method to access the facebook Graph API. Token expires every 3 or so hours. so the APK will not work everytime , to avoid this go to the strings.xml file and change the access token everytime.
2. Not the most efficient process to change the access token but i wanted to avoid wasting time in integrating the SDK and then authenticating and saving the session token ,etc. I wanted to concentrate most of my time executing the network calls and synchronizing them.

GingerIOApp:
Application class. Future purpose add some bitmap memory checks as well as StrictMode policy. For now just context is getting returned.

MainActivity :
Main Class. Has an actionbar tabs method. Initiates a Loader which is going through all the data and parsing the events . Shows loadingscreen until the loader is finished. The upcoming Events page is tabulated with their id's . Uses a handler to update the view.

EventListLoader :
AysnctaskLoader to load the events. Parses them into a custom object for each event. Implemented to get the first 10 pages of data and not all data because it was taking too long.

upcomingEventsFragment:
As the name suggests. Fragment which contains a listview which shows the upcoming events when they are populated. Starts the next activity which displays the vendors for that particular event.

upcomingEventsListAdapter:
Simple adapter to display the event data from the fragment.

VendorListActivity:
Shows  the list of vendors for that particular event ID. Calls the particular event API and parses the description field and adds them to the universal list of vendorsMap.Starts  the VendorListLoader.

VendorListLoader:
Calls the EVENT Graph API. Downloads and parses the data accordingly.

VendorData:
Custom Vendor Object

EventData:
Custom Event Object.

Utils:
Utility class to hold some parsing and generic static data and methods which will persist through the entire app. Parses JSON, Sorts Map according to date, gets Date/event name,etc.

historyEventsFragment:
Shows the complete list of Vendors . In this case we do not have a complete list of vendors. TO get the complete list of vendors we had to call each EVENT API and parse the description and normalize it and add a vendor. 
So there is a complex AsyncTask call here which goes through each event and checks if that event API was called , if not then it calls them in parallel and downloads/parses vendor details and stores them in a universal static Map.

historyVendorsListAdapter:
Simple adapter to display the data provided by the Fragment

VendorHistoryListActivity:
Shows the vendor details and also the past events each vendor came to.

TabListener:
Simple action tab Listener for the action bar tabs added in the Main activity.




TODO :
- Fixing the "changing access token issue". Add the SDK and authenticate once or find a never-expiring token to access the API.
- sort them more efficiently. 
- OnSaveInstanceState to avoid the changing orientation issue.
- Make network calls in ansynchronous manner the first time to avoid the loading screen .
- Improve UI. Beautify the app. Standardize the ListView the way it looks.
- Save data in the Database to avoid making network calls everytime the app starts.
- Add logic and validation cases.
- Save memory by avoiding doing so many things while parsing the JSON.
- Fix crashers if any .