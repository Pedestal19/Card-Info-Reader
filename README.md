#Card Info Finder


*An Android application (written in Kotlin) which will accepts the input from a user either by:

*Entering a card number using the Soft Input keyboard

*Scanning the debit card number using OCR.


*Once a card is scanned or the user finishes entering the card details, the app displays the following by calling the Binlist API to get the following details:

-Card brand,

-Card type,

-bank,

-Country



*Code structure - Clean Architecture and SOLID principles (Model View ViewModel MVVM + Retrofit)

*Testing included Unit and UI tests (Unit Test & Instrumentation test).

*Using external APIs (BinList).

User Interface and User experience - UI/UX (Material Design)

Optical Character Recognition (OCR) done using Paycard 3rd party library and Google ML Kit (Still in development)

*Note: Setup Project in firebase, download the google.json file for project and add in android studio.

       *This should be done in order to run the App (Because of the Google ML Kit Feature)

