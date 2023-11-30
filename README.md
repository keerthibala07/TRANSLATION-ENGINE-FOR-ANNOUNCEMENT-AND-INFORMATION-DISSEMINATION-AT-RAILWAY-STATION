# IRS-Multilingual-Information-Dissemination-System
Java based Chatbot for Multilingual live info dissemination system for Indian Railway System

# Indian Railways Multilingual Information Dissemination System Chatbot
This is a chatbot designed to provide multilingual information about Indian Railways.
The chatbot gets its information from a Firebase database that is updated by a Python voice translation software running on another system.

Features
Provides information about train schedules and routes.
Can search for trains based on station names or train numbers.
Provides real-time updates about train status and delays.
Supports multiple languages, including English, Hindi, and regional languages.
Technologies Used
Firebase Realtime Database
Python Voice Translation Software
Dialogflow (for chatbot)
Flask (for server)
HTML/CSS/JavaScript (for web interface)
How it Works
The chatbot uses Dialogflow to handle user queries and respond with appropriate information. When the chatbot receives a query, it sends a request to a server running on Flask. The server retrieves the necessary information from the Firebase database, using a Python voice translation software to update the database as needed. The server then sends the information back to the chatbot, which displays it to the user through a web interface.

# Getting Started
To run the chatbot, you will need to set up a Firebase account and create a Realtime Database. You will also need to have the Python voice translation software installed on another system. Once you have these set up, follow these steps:

Clone this repository to your local machine.
Install the necessary dependencies by running pip install -r requirements.txt.
Update the Firebase configuration details in config.py.
Run the Flask server by executing python app.py.
Open the web interface in a browser at http://localhost:5000.
Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

# License
This project is licensed under the MIT License - see the LICENSE file for details.
