import pyrebase
import speech_recognition as sr
from translate import Translator


firebaseConfig = {
  "apiKey": "AIzaSyCpfKr_tMsM2Yu1LfJ8fSovOpr7QcayycE",
  "authDomain": "train-announcement-261a2.firebaseapp.com",
  "databaseURL": "https://train-announcement-261a2-default-rtdb.firebaseio.com",
  "projectId": "train-announcement-261a2",
  "storageBucket": "train-announcement-261a2.appspot.com",
  "messagingSenderId": "906827760826",
  "appId": "1:906827760826:web:7be014a139c5666805ae83"

};
firebase = pyrebase.initialize_app(firebase_config)
db = firebase.database()

# Initialize the recognizer
recognizer = sr.Recognizer()

def get_train_number():
    # Get the train number as text input from the user
    train_number = input("Enter the train number: ")
    return train_number

def recognize_speech():
    # Capture audio from the microphone
    with sr.Microphone() as source:
        print("Say something:")
        audio = recognizer.listen(source)

    # Recognize speech to get additional information
    try:
        text = recognizer.recognize_google(audio)
        return text
    except sr.UnknownValueError:
        print("Sorry, I could not understand what you said.")
        return None
    except sr.RequestError as e:
        print(f"Sorry, there was an error with the request: {e}")
        return None

def translate_and_store(input_text, language_code, train_number):
    # Translate the input text
    translator = Translator(to_lang=language_code)
    translated_text = translator.translate(input_text)

    # Store the translated text in Firebase Realtime Database under the train number
    db.child("train_data").child(train_number).child("translations").child(language_code).set(translated_text)

    return translated_text

if __name__ == "__main__":
    train_number = get_train_number()

    if train_number:
        # Obtain additional information via speech recognition
        input_text = recognize_speech()

        if input_text:
            # Define the list of language codes for translation
            languages = ['en', 'ta', 'te', 'kn', 'ml', 'hi']

            # Translate and store the text for each language under the train number
            for lang_code in languages:
                translated_text = translate_and_store(input_text, lang_code, train_number)
                print(f"Translated text ({lang_code}): {translated_text}")
