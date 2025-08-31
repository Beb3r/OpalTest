My take on the Opal tech challenge!
Made in about 6hrs. I really had some fun with shaders for the splash screen!
The menu on the top right corner of the screen allows you to
- simulate referred users count
- override gem selection (for theming)
- clear these settings
  I tried to tackle most of the requirements, hope you'll enjoy it.

This is a single acitivity, 100% Compose app with a modular approach. Build logic is handled with Gradle convention plugin and version catalog.

=> kotlin, compose, material3, AAC viewmodels, coroutines, koin annotations, coil, datastore

=> all sources are flows from datastore, so screens are reactive and support offline (well, there's no network call here but you get the idea)

=> main composable observes and collects current gem flow, to automatically update the app theme color (for blurred background, buttons, texts etc) via CompositionLocalProvider

Overall organisation
<img width="1852" height="2904" alt="arch" src="https://github.com/user-attachments/assets/199f7520-8146-4ed6-bc7d-6114a2ab5e28" />

Bonus on v1.1
Found your slider behavior on the iOS app quite rad, so decided to give it a try and implement something similar!
The slider can be found in the "simulate referred users count" bottom sheet


https://github.com/user-attachments/assets/43bf353c-1708-403a-960d-d752aeb6a2bc

