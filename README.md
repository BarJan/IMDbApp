# IMDbApp
An Android Studio project done for MSApps.

App uses few fregmants, and only two activities - splash and main.

This app loads a json movies list from the api: https://api.androidhive.info/json/movies.json
and converts it to its movie object (MovObj) - on splash activity
On MainActivity the app
- shows the list of movies ordered by their release year (from new to old)
- on clicking on add button - opens camera to scan for QR barcode to add new movie to the list
- on clicking on a movie - desplay other details about it

Splash's gif from : https://wallpapercave.com/w/wp2757881
Main resources :
General - https://developer.android.com/guide/
Splash - https://dev.to/alonsoatoneyra/android-simple-way-to-implement-a-splashscreen-in-your-app-3k0f
Retrofit\Json - 
https://medium.com/@prakash_pun/retrofit-a-simple-android-tutorial-48437e4e5a23
https://square.github.io/retrofit/
https://www.simplifiedcoding.net/retrofit-android-example/
QR Code - https://www.youtube.com/watch?v=ej51mAYXbKs

